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
                    // Filter System Apps and Trusted Apps
                    val isSystemApp = (pkg.applicationInfo.flags and android.content.pm.ApplicationInfo.FLAG_SYSTEM) != 0 ||
                                      (pkg.applicationInfo.flags and android.content.pm.ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0
                    
                    val trustedPackages = setOf(
                        // Telegram Ecosystem
                        "org.telegram.messenger", "org.telegram.messenger.web", "org.telegram.plus", "org.thunderdog.challegram", "org.aka.messenger",
                        // Social & Communication
                        "com.instagram.android", "com.instagram.lite", "com.instagram.barcelona",
                        "com.facebook.katana", "com.facebook.orca", "com.facebook.lite",
                        "com.whatsapp", "com.whatsapp.w4b", "com.viber.voip", "us.zoom.videomeetings", "com.skype.raider",
                        "com.snapchat.android", "com.twitter.android", "com.linkedin.android", "com.pinterest",
                        "org.thoughtcrime.securesms", "com.imo.android.imoim", "ru.oneme.app",
                        "com.zhiliaoapp.musically", "com.ss.android.ugc.trill",
                        // Banking & Finance (Uzbekistan)
                        "uz.tbc.mobile", "uz.tbcbank", "com.tbcgroup", "uz.tbc",
                        "uz.alif.mobi", "uz.alif", "com.alif.mobi", "com.alif",
                        "uz.click.uz", "uz.click", "air.com.ssd.click", "uz.fido.click", "com.click.uzsmart", "uz.click.mobilbank",
                        "uz.dida.payme", "uz.payme", "com.payme.app",
                        "uz.uzcard.mobile", "uz.uzcard", "uz.humo.mobile", "uz.humo",
                        "uz.shaxslar.kapitalbank", "uz.proweb.kapitalbank", "uz.kapitalbank",
                        "uz.ipakyulibank.mobile", "uz.ipakyulibank",
                        "uz.agrobank.agro_mobile", "uz.agrobank",
                        "uz.hamkorbank.mobile", "uz.hamkorbank",
                        "uz.vcard.mobile", "uz.vcard",
                        "uz.ofb.mobile", "uz.ofb",
                        "uz.sqb.mobile", "uz.sqb",
                        "uz.nbu.mobile", "uz.nbu",
                        "uz.vcb.mobile", "uz.vcb",
                        "uz.zoodpay.app", "uz.paynet.android", "uz.xazna.app", "uz.avo.app",
                        "uz.soliq.mobile", "uz.zood.pay", "uz.zood.mall",
                        "uz.uzum.bank", "uz.uzum.market", "uz.uzum.pay", "uz.uzum", "com.uzumbank",
                        // Government & Public Services (Uzbekistan)
                        "uz.uzinfocom.mygov", "uz.egov.oneid", "uz.uzinfocom.dmed", "uz.smartbase.myinspector",
                        "uz.smartbase.license", "uz.iiv.meninginspektorim", "uz.agro.mobile",
                        // E-commerce & Tools
                        "uz.asaxiy.app", "uz.asaxiy", "uz.olx.uz", "uz.olx", "uz.doska.birbir",
                        "com.alibaba.aliexpress", "com.amazon.mShop.android.shopping", "com.ebay.mobile",
                        "uz.allplay.app", "uz.beeline.uz", "uz.mobiuz.mobi",
                        // Education & AI
                        "uz.ibrat.academy", "uz.ibrat", "uz.upscrolled", "com.upscrolled.app",
                        "com.duolingo", "com.google.android.apps.classroom", "ai.saveall.app",
                        "com.openai.chatgpt", "com.google.android.apps.bard", "com.google.android.apps.gemini",
                        "com.pixverseai.pixverse",
                        // Media & Transportation
                        "com.spotify.music", "com.netflix.mediaclient", "com.lemon.lvoverseas", "com.jana.tube.video",
                        "ru.yandex.taxi.uz", "ru.yandex.taxi", "ru.yandex.searchplugin", "com.yandex.browser", 
                        "ru.yandex.yandexmaps", "uz.mytaxi.client", "uz.mytaxi", "uz.express.customer",
                        // Games & System
                        "com.tencent.ig", "com.mobile.legends", "com.ea.gp.fifamobile", "com.roblox.client",
                        "com.kiloo.subwaysurf", "com.PlayMax.playergames", "com.ForgeGames.SpecialForcesGroup2",
                        "com.eps.android", "com.google.android.apps.messaging", "com.google.android.apps.photos", "com.google.android.gm",
                        "uz.gizmo", "com.edaai.globalmove"
                    )
                    
                    val normalizedPkg = pkg.packageName.trim().lowercase()
                    val isTrusted = trustedPackages.contains(normalizedPkg) || 
                                    trustedPackages.any { normalizedPkg.startsWith("$it.") }
                    
                    // Skip if it is a system app or well-known trusted app
                    if (isSystemApp || isTrusted) return@forEach

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
