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

    val securityScore: StateFlow<Int> = threatEventDao.getCriticalThreatCount().map { count ->
        (100 - (count * 5)).coerceAtLeast(30)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = 100
    )

    // --- LICENSE MANAGEMENT ---
    private val _deviceId = kotlinx.coroutines.flow.MutableStateFlow("")
    val deviceId: StateFlow<String> = _deviceId
    
    // Check SharedPreferences for saved key validity
    private val _isPremium = kotlinx.coroutines.flow.MutableStateFlow(false)
    val isPremium: StateFlow<Boolean> = _isPremium

    // --- PERMISSION & SERVICE STATUS ---
    data class ProtectionStatus(
        val isFileGuardActive: Boolean = false,
        val isAccessibilityActive: Boolean = false,
        val isNotificationListenerActive: Boolean = false,
        val isVpnActive: Boolean = false,
        val isNotificationPermissionGranted: Boolean = true
    )

    private val _protectionStatus = kotlinx.coroutines.flow.MutableStateFlow(ProtectionStatus())
    val protectionStatus: StateFlow<ProtectionStatus> = _protectionStatus

    init {
        loadIdentity()
        checkAllServices()
    }

    fun checkAllServices() {
        viewModelScope.launch {
            val fileGuard = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
                android.os.Environment.isExternalStorageManager()
            } else true

            val accessibility = isAccessibilityServiceEnabled()
            val notificationListener = isNotificationListenerEnabled()
            val vpn = com.eps.android.core.AntiPhishingVpnService.isServiceRunning.get()
            
            val notificationPermission = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                androidx.core.content.ContextCompat.checkSelfPermission(
                    context, android.Manifest.permission.POST_NOTIFICATIONS
                ) == android.content.pm.PackageManager.PERMISSION_GRANTED
            } else true

            _protectionStatus.value = ProtectionStatus(
                isFileGuardActive = fileGuard,
                isAccessibilityActive = accessibility,
                isNotificationListenerActive = notificationListener,
                isVpnActive = vpn,
                isNotificationPermissionGranted = notificationPermission
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
        
        // Auto-check saved license
        val prefs = context.getSharedPreferences("cyber_prefs", android.content.Context.MODE_PRIVATE)
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
            // Save locally
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
