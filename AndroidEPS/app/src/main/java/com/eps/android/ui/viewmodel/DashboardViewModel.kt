package com.eps.android.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eps.android.data.ThreatEvent
import com.eps.android.data.ThreatEventDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    @dagger.hilt.android.qualifiers.ApplicationContext private val context: android.content.Context,
    private val threatEventDao: ThreatEventDao,
    private val blacklistDao: com.eps.android.data.BlacklistDao
) : ViewModel() {

    // ... Existing event flows ...
    val events: StateFlow<List<ThreatEvent>> = threatEventDao.getAllEvents()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val criticalCount: StateFlow<Int> = threatEventDao.getCriticalThreatCount()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )

    // --- PERMISSION & SERVICE STATUS ---
    data class ProtectionStatus(
        val isFileGuardActive: Boolean = false,
        val isAccessibilityActive: Boolean = false,
        val isNotificationListenerActive: Boolean = false,
        val isNotificationPermissionGranted: Boolean = true,
        val isAutoRevokeDisabled: Boolean = true // Whitelisted from auto-revoke
    )

    private val _protectionStatus = kotlinx.coroutines.flow.MutableStateFlow(ProtectionStatus())
    val protectionStatus: StateFlow<ProtectionStatus> = _protectionStatus

    val recentEvents: StateFlow<List<ThreatEvent>> = threatEventDao.getRecentEvents(5)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val securityScore: StateFlow<Int> = protectionStatus.map { status ->
        var score = 40 // Base score for running Cyber Brother
        if (status.isAccessibilityActive) score += 20
        if (status.isNotificationListenerActive) score += 20
        if (status.isFileGuardActive) score += 15
        if (status.isNotificationPermissionGranted) score += 5
        score.coerceAtMost(100)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = 45
    )

    val dailyReportResource: StateFlow<String> = events.map { allEvents ->
        val last24h = System.currentTimeMillis() - (24 * 60 * 60 * 1000)
        val todayEvents = allEvents.filter { it.timestamp > last24h }
        val threats = todayEvents.filter { it.severity in listOf("CRITICAL", "HIGH") }.size
        val itemsParsed = todayEvents.size
        
        val lang = context.resources.configuration.locales[0].language
        when(lang) {
            "uz" -> "Sizni bugun $itemsParsed ta harakatdan va $threats ta jiddiy xavfdan himoya qildim. Tizim to'liq nazoratda! ✅"
            "ru" -> "Сегодня я защитил вас от $itemsParsed действий и $threats серьезных угроз. Система под полным контролем! ✅"
            else -> "Today I protected you from $itemsParsed activities and $threats serious threats. System is under full control! ✅"
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = "Scanning..."
    )

    // --- LICENSE & TRIAL MANAGEMENT ---
    private val _deviceId = kotlinx.coroutines.flow.MutableStateFlow("")
    val deviceId: StateFlow<String> = _deviceId
    
    private val _isPremium = kotlinx.coroutines.flow.MutableStateFlow(false)
    val isPremium: StateFlow<Boolean> = _isPremium

    private val _trialDaysRemaining = kotlinx.coroutines.flow.MutableStateFlow(14)
    val trialDaysRemaining: StateFlow<Int> = _trialDaysRemaining

    val areFeaturesUnlocked: StateFlow<Boolean> = kotlinx.coroutines.flow.combine(_isPremium, _trialDaysRemaining) { premium, days ->
        premium || days > 0
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), true)

    init {
        loadIdentity()
        checkAllServices()
    }

    fun checkAllServices() {
        // ... (unchanged)
        viewModelScope.launch {
            val fileGuard = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
                android.os.Environment.isExternalStorageManager()
            } else true

            val accessibility = isAccessibilityServiceEnabled()
            val notificationListener = isNotificationListenerEnabled()
            
            val notificationPermission = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                androidx.core.content.ContextCompat.checkSelfPermission(
                    context, android.Manifest.permission.POST_NOTIFICATIONS
                ) == android.content.pm.PackageManager.PERMISSION_GRANTED
            } else true

            val autoRevokeDisabled = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
                try {
                    context.packageManager.isAutoRevokeWhitelisted
                } catch (e: Exception) { true }
            } else true

            _protectionStatus.value = ProtectionStatus(
                isFileGuardActive = fileGuard,
                isAccessibilityActive = accessibility,
                isNotificationListenerActive = notificationListener,
                isNotificationPermissionGranted = notificationPermission,
                isAutoRevokeDisabled = autoRevokeDisabled
            )
        }
    }

    private fun isAccessibilityServiceEnabled(): Boolean {
        val service = "${context.packageName}/${com.eps.android.core.PhishingAccessibilityService::class.java.canonicalName}"
        val enabledServices = android.provider.Settings.Secure.getString(
            context.contentResolver, 
            android.provider.Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES
        ) ?: ""
        return enabledServices.contains(service)
    }

    private fun isNotificationListenerEnabled(): Boolean {
        val packageName = context.packageName
        val listeners = android.provider.Settings.Secure.getString(
            context.contentResolver,
            "enabled_notification_listeners"
        ) ?: ""
        return listeners.contains(packageName)
    }

    private fun loadIdentity() {
        val id = com.eps.android.core.IdentityManager.getDeviceId(context)
        _deviceId.value = id
        
        val prefs = context.getSharedPreferences("cyber_prefs", android.content.Context.MODE_PRIVATE)
        
        // 1. Check Trial
        val installTime = prefs.getLong("install_timestamp", 0L)
        if (installTime == 0L) {
            val now = System.currentTimeMillis()
            prefs.edit().putLong("install_timestamp", now).apply()
            _trialDaysRemaining.value = 14
        } else {
            val diff = System.currentTimeMillis() - installTime
            val daysPassed = (diff / (1000 * 60 * 60 * 24)).toInt()
            _trialDaysRemaining.value = (14 - daysPassed).coerceAtLeast(0)
        }

        // 2. Check License
        val savedKey = prefs.getString("license_key", "") ?: ""
        if (savedKey.isNotEmpty()) {
            val isValid = com.eps.android.core.IdentityManager.validateLicense(context, savedKey)
            _isPremium.value = isValid
        }
    }

    fun activateLicense(key: String): Boolean {
        val isValid = com.eps.android.core.IdentityManager.validateLicense(context, key)
        if (isValid) {
            _isPremium.value = true
            context.getSharedPreferences("cyber_prefs", android.content.Context.MODE_PRIVATE)
                .edit()
                .putString("license_key", key)
                .apply()
        }
        return isValid
    }

    fun testDetection() {
        viewModelScope.launch {
            threatEventDao.insertEvent(
                ThreatEvent(
                    type = "TEST_SULTATION",
                    severity = "HIGH",
                    source = "User Manual Test",
                    details = "This is a simulated threat to verify HACKDEFENDER visibility."
                )
            )
        }
    }

    // --- OFFLINE UPDATES ---
    fun importOfflineDatabase(uri: android.net.Uri) {
        viewModelScope.launch(kotlinx.coroutines.Dispatchers.IO) {
            try {
                val inputStream = context.contentResolver.openInputStream(uri)
                val json = inputStream?.bufferedReader().use { it?.readText() } ?: ""
                
                if (json.isNotBlank()) {
                    val jsonObj = org.json.JSONObject(json)
                    val domainsArray = jsonObj.optJSONArray("domains")
                    
                    val list = mutableListOf<com.eps.android.data.BlacklistedDomain>()
                    if (domainsArray != null) {
                        for (i in 0 until domainsArray.length()) {
                            val item = domainsArray.getJSONObject(i)
                            list.add(
                                com.eps.android.data.BlacklistedDomain(
                                    domain = item.getString("domain"),
                                    reason = item.optString("reason", "Offline Update"),
                                    addedAt = System.currentTimeMillis()
                                )
                            )
                        }
                    }
                    
                    if (list.isNotEmpty()) {
                        blacklistDao.insertAll(list)
                        showToast("Updates Applied: ${list.size} domains")
                    } else {
                        showToast("No valid data found in file")
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                showToast("Update Failed: Corrupted File")
            }
        }
    }

    private suspend fun showToast(msg: String) {
        kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.Main) {
            android.widget.Toast.makeText(context, msg, android.widget.Toast.LENGTH_LONG).show()
        }
    }
}
