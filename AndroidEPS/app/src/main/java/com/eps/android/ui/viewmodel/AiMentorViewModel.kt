package com.eps.android.ui.viewmodel

import com.eps.android.R

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eps.android.data.ThreatEvent
import com.eps.android.data.ThreatEventDao
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AiMentorViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val threatEventDao: ThreatEventDao,
    private val offlineAi: com.eps.android.analysis.ai.OfflineAiEngine
) : ViewModel(), TextToSpeech.OnInitListener {

    data class ChatMessage(val text: String, val isUser: Boolean)

    private val _isListening = MutableStateFlow(false)
    val isListening: StateFlow<Boolean> = _isListening

    private val _chatHistory = MutableStateFlow(listOf(
        ChatMessage(context.getString(R.string.intro_title) + " " + context.getString(R.string.intro_desc), false)
    ))
    val chatHistory: StateFlow<List<ChatMessage>> = _chatHistory

    private var tts: TextToSpeech? = null
    private var speechRecognizer: SpeechRecognizer? = null
    
    init {
        tts = TextToSpeech(context, this)
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)
        setupSpeechRecognizer()
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val lang = context.resources.configuration.locales[0].language
            tts?.language = Locale(lang)
        }
    }

    private fun setupSpeechRecognizer() {
        speechRecognizer?.setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle?) {}
            override fun onBeginningOfSpeech() { _isListening.value = true }
            override fun onRmsChanged(rmsdB: Float) {}
            override fun onBufferReceived(buffer: ByteArray?) {}
            override fun onEndOfSpeech() { _isListening.value = false }
            override fun onError(error: Int) { _isListening.value = false }
            override fun onResults(results: Bundle?) {
                val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                if (!matches.isNullOrEmpty()) {
                    sendMessage(matches[0])
                }
            }
            override fun onPartialResults(partialResults: Bundle?) {}
            override fun onEvent(eventType: Int, params: Bundle?) {}
        })
    }

    fun startListening() {
        val lang = context.resources.configuration.locales[0].language
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, if (lang == "uz") "uz-UZ" else "en-US")
        }
        speechRecognizer?.startListening(intent)
    }

    fun sendMessage(input: String) {
        val currentHistory = _chatHistory.value.toMutableList()
        currentHistory.add(ChatMessage(input, true))
        _chatHistory.value = currentHistory

        val lang = context.resources.configuration.locales[0].language

        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Fetch recent events for context
                val events = threatEventDao.getRecentEvents(15)
                
                // Gather Device Stats
                val batteryStatus = context.registerReceiver(null, android.content.IntentFilter(android.content.Intent.ACTION_BATTERY_CHANGED))
                val level = batteryStatus?.getIntExtra(android.os.BatteryManager.EXTRA_LEVEL, -1) ?: -1
                val scale = batteryStatus?.getIntExtra(android.os.BatteryManager.EXTRA_SCALE, -1) ?: -1
                val batteryPct = (level * 100 / scale.toFloat()).toInt()
                val isCharging = batteryStatus?.getIntExtra(android.os.BatteryManager.EXTRA_STATUS, -1) == android.os.BatteryManager.BATTERY_STATUS_CHARGING
                
                val stat = android.os.StatFs(android.os.Environment.getDataDirectory().path)
                val bytesAvailable = stat.blockSizeLong * stat.availableBlocksLong
                val freeGb = String.format("%.1f", bytesAvailable / (1024f * 1024f * 1024f))

                // Detect Network Interface (specifically USB Tethering / RNDIS)
                var connectionMode = "UNKNOWN"
                try {
                    val interfaces = java.net.NetworkInterface.getNetworkInterfaces()
                    for (intf in java.util.Collections.list(interfaces)) {
                        if (intf.isUp && (intf.name.contains("rndis") || intf.name.contains("usb0"))) {
                            connectionMode = "USB_TETHERING"
                            break
                        }
                    }
                } catch (e: Exception) {
                    Timber.e(e, "Error sensing network interfaces")
                }

                // Detect VPN Status
                val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as android.net.ConnectivityManager
                val activeNetwork = connectivityManager.activeNetwork
                val caps = connectivityManager.getNetworkCapabilities(activeNetwork)
                val isVpnActive = caps?.hasTransport(android.net.NetworkCapabilities.TRANSPORT_VPN) == true

                val deviceStats = com.eps.android.analysis.ai.OfflineAiEngine.DeviceStatus(batteryPct, freeGb, isCharging, connectionMode, isVpnActive)

                // Process via 100% Offline AI Engine
                val offlineResponse = offlineAi.respond(input, events, deviceStats)
                
                // Add "[Offline]" tag only if we want to emphasize privacy
                addToHistory("🤖 $offlineResponse", false)

            } catch (e: Exception) {
                Timber.e(e, "Offline AI Error")
                addToHistory(if (lang == "uz") "Kechirasiz, ichki xatolik yuz berdi." else "Sorry, an internal error occurred.", false)
            }
        }
    }

    private fun addToHistory(text: String, isUser: Boolean) {
        val updated = _chatHistory.value.toMutableList()
        updated.add(ChatMessage(text, isUser))
        _chatHistory.value = updated
    }

    fun speak(text: String) {
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
    }

    override fun onCleared() {
        super.onCleared()
        tts?.stop()
        tts?.shutdown()
        speechRecognizer?.destroy()
    }
}
