package com.eps.android.ui.viewmodel

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eps.android.R
import com.eps.android.data.ThreatEvent
import com.eps.android.data.ThreatEventDao
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AiMentorViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val threatEventDao: ThreatEventDao,
    private val offlineAi: com.eps.android.analysis.ai.OfflineAiEngine
) : ViewModel() {

    data class ChatMessage(val text: String, val isUser: Boolean)

    private val _chatHistory = MutableStateFlow(listOf(
        ChatMessage(context.getString(R.string.intro_title) + " " + context.getString(R.string.intro_desc), false)
    ))
    val chatHistory: StateFlow<List<ChatMessage>> = _chatHistory

    private val _isTyping = MutableStateFlow(false)
    val isTyping: StateFlow<Boolean> = _isTyping

    fun sendMessage(input: String) {
        val currentHistory = _chatHistory.value.toMutableList()
        currentHistory.add(ChatMessage(input, true))
        _chatHistory.value = currentHistory

        val lang = context.resources.configuration.locales[0].language

        viewModelScope.launch(Dispatchers.IO) {
            _isTyping.value = true
            try {
                // 1. Gather Real System Context
                val events = threatEventDao.getRecentEvents(10).first()
                val batteryStatus = context.registerReceiver(null, android.content.IntentFilter(android.content.Intent.ACTION_BATTERY_CHANGED))
                val batteryPct = batteryStatus?.let {
                    val level = it.getIntExtra(android.os.BatteryManager.EXTRA_LEVEL, -1)
                    val scale = it.getIntExtra(android.os.BatteryManager.EXTRA_SCALE, -1)
                    (level * 100 / scale.toFloat()).toInt()
                } ?: 0
                val isCharging = batteryStatus?.getIntExtra(android.os.BatteryManager.EXTRA_STATUS, -1) == android.os.BatteryManager.BATTERY_STATUS_CHARGING

                val stat = android.os.StatFs(android.os.Environment.getDataDirectory().path)
                val freeGb = String.format("%.1f", (stat.blockSizeLong * stat.availableBlocksLong) / (1024f * 1024f * 1024f))

                // Detect VPN
                val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as android.net.ConnectivityManager
                val caps = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                val isVpnActive = caps?.hasTransport(android.net.NetworkCapabilities.TRANSPORT_VPN) == true

                val deviceStats = com.eps.android.analysis.ai.OfflineAiEngine.DeviceStatus(
                    batteryPct, freeGb, isCharging, "AUTO", isVpnActive
                )

                // 2. Process via 100% Offline AI
                val aiResponse = offlineAi.respond(input, events, deviceStats)
                
                kotlinx.coroutines.delay(800) // Small delay for "thinking" feel
                addToHistory(aiResponse.text, false)
                
                // 3. Execute Actions
                if (aiResponse.action.type != com.eps.android.analysis.ai.OfflineAiEngine.SystemActionType.NONE) {
                    executeSystemAction(aiResponse.action)
                }

            } catch (e: Exception) {
                Timber.e(e, "Offline AI error")
                addToHistory(if (lang == "uz") "Xatolik yuz berdi, birodar." else "Something went wrong, brother.", false)
            } finally {
                _isTyping.value = false
            }
        }
    }

    private fun executeSystemAction(action: com.eps.android.analysis.ai.OfflineAiEngine.SystemAction) {
        viewModelScope.launch(Dispatchers.Main) {
            when (action.type) {
                com.eps.android.analysis.ai.OfflineAiEngine.SystemActionType.OPEN_APP -> {
                    val pkg = action.data ?: return@launch
                    context.packageManager.getLaunchIntentForPackage(pkg)?.let {
                        it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        context.startActivity(it)
                    }
                }
                com.eps.android.analysis.ai.OfflineAiEngine.SystemActionType.START_SCAN -> {
                    context.sendBroadcast(Intent("com.eps.android.ACTION_QUICK_SCAN"))
                }
                else -> {}
            }
        }
    }

    private fun addToHistory(text: String, isUser: Boolean) {
        val updated = _chatHistory.value.toMutableList()
        updated.add(ChatMessage(text, isUser))
        _chatHistory.value = updated
    }
}
