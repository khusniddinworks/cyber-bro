package com.eps.android.ui.viewmodel

import android.app.admin.DevicePolicyManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.provider.Settings
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SecurityHubViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {

    data class AppInfo(
        val name: String,
        val packageName: String,
        val icon: android.graphics.drawable.Drawable?
    )

    data class SecurityStats(
        val appsWithCamera: List<AppInfo> = emptyList(),
        val appsWithMic: List<AppInfo> = emptyList(),
        val appsWithLocation: List<AppInfo> = emptyList(),
        val isDevModeEnabled: Boolean = false,
        val isAdbEnabled: Boolean = false,
        val isUnknownSourcesAllowed: Boolean = false,
        val screenLockSecure: Boolean = false
    )

    private val _stats = MutableStateFlow(SecurityStats())
    val stats: StateFlow<SecurityStats> = _stats

    init {
        scanSecurityState()
    }

    fun scanSecurityState() {
        viewModelScope.launch {
            val pm = context.packageManager
            val packages = pm.getInstalledPackages(PackageManager.GET_PERMISSIONS)

            val camList = mutableListOf<AppInfo>()
            val micList = mutableListOf<AppInfo>()
            val locList = mutableListOf<AppInfo>()

            packages.forEach { pkg ->
                val perms = pkg.requestedPermissions
                val flags = pkg.requestedPermissionsFlags
                if (perms != null && flags != null) {
                    // Filter System Apps: Check if FLAG_SYSTEM or FLAG_UPDATED_SYSTEM_APP is set
                    val isSystemApp = (pkg.applicationInfo.flags and android.content.pm.ApplicationInfo.FLAG_SYSTEM) != 0 ||
                                      (pkg.applicationInfo.flags and android.content.pm.ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0
                    
                    // Skip if it is a system app
                    if (isSystemApp) return@forEach

                    var hasCamera = false
                    var hasMic = false
                    var hasLocation = false
                    
                    for (i in perms.indices) {
                        val p = perms[i]
                        val f = flags[i]
                        val isGranted = (f and android.content.pm.PackageInfo.REQUESTED_PERMISSION_GRANTED) != 0
                        
                        if (isGranted) {
                            if (p == android.Manifest.permission.CAMERA) hasCamera = true
                            if (p == android.Manifest.permission.RECORD_AUDIO) hasMic = true
                            if (p == android.Manifest.permission.ACCESS_FINE_LOCATION || 
                                p == android.Manifest.permission.ACCESS_COARSE_LOCATION) hasLocation = true
                        }
                    }

                    if (hasCamera || hasMic || hasLocation) {
                        val appInfo = AppInfo(
                            name = pkg.applicationInfo.loadLabel(pm).toString(),
                            packageName = pkg.packageName,
                            icon = pkg.applicationInfo.loadIcon(pm)
                        )
                        if (hasCamera) camList.add(appInfo)
                        if (hasMic) micList.add(appInfo)
                        if (hasLocation) locList.add(appInfo)
                    }
                }
            }

            // System Settings Checks
            val devMode = Settings.Global.getInt(context.contentResolver, Settings.Global.DEVELOPMENT_SETTINGS_ENABLED, 0) == 1
            val adb = Settings.Global.getInt(context.contentResolver, Settings.Global.ADB_ENABLED, 0) == 1
            val unknownSources = try {
                Settings.Secure.getInt(context.contentResolver, Settings.Secure.INSTALL_NON_MARKET_APPS, 0) == 1
            } catch (e: Exception) { false } // Deprecated in newer Android, handled differently but good for older fallback

             // Screen Lock Check
            val keyguardManager = context.getSystemService(Context.KEYGUARD_SERVICE) as android.app.KeyguardManager
            val isSecure = keyguardManager.isDeviceSecure

            _stats.value = SecurityStats(
                appsWithCamera = camList,
                appsWithMic = micList,
                appsWithLocation = locList,
                isDevModeEnabled = devMode,
                isAdbEnabled = adb,
                isUnknownSourcesAllowed = unknownSources,
                screenLockSecure = isSecure
            )
        }
    }

    fun openDeveloperSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    fun openSecuritySettings() {
        val intent = Intent(Settings.ACTION_SECURITY_SETTINGS)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    fun openNotificationSettings() {
        val intent = Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }
}
