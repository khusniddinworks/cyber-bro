package com.eps.android.analysis.ai

import android.app.ActivityManager
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.TrafficStats
import android.os.BatteryManager
import android.os.Build
import android.os.Environment
import android.os.StatFs
import android.provider.Settings
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import java.io.File
import java.io.RandomAccessFile
import java.net.NetworkInterface
import javax.inject.Inject
import javax.inject.Singleton

/**
 * PRO DEVICE ANALYZER - Qurilmani 100% tahlil qiluvchi AI Core
 * 
 * Bu modul qurilmadagi barcha jarayonlarni tahlil qiladi va 
 * foydalanuvchiga tushunarli tilda ma'lumot beradi.
 */
@Singleton
class DeviceAnalyzer @Inject constructor(
    @ApplicationContext private val context: Context
) {

    /**
     * To'liq qurilma holati
     */
    data class FullDeviceReport(
        // Battery
        val batteryLevel: Int,
        val isCharging: Boolean,
        val batteryTemperature: Float,
        val batteryHealth: String,
        
        // Storage
        val totalStorageGb: Float,
        val freeStorageGb: Float,
        val usedStoragePercent: Int,
        
        // Memory (RAM)
        val totalRamMb: Long,
        val availableRamMb: Long,
        val usedRamPercent: Int,
        
        // CPU
        val cpuUsagePercent: Float,
        
        // Network
        val networkType: String,
        val isVpnActive: Boolean,
        val isUsbTethering: Boolean,
        val isHotspotActive: Boolean,
        val mobileDataUsedMb: Long,
        val wifiDataUsedMb: Long,
        
        // Apps & Processes
        val runningAppsCount: Int,
        val backgroundProcesses: List<ProcessInfo>,
        val recentlyInstalledApps: List<AppInfo>,
        val suspiciousApps: List<AppInfo>,
        
        // Security
        val isDevModeEnabled: Boolean,
        val isAdbEnabled: Boolean,
        val isUnknownSourcesAllowed: Boolean,
        val isScreenLockSet: Boolean,
        val isRooted: Boolean,
        
        // System
        val androidVersion: String,
        val deviceModel: String,
        val uptime: String
    )
    
    data class ProcessInfo(
        val name: String,
        val memoryMb: Int,
        val importance: String,
        val explanation: String // Foydalanuvchiga tushunarli tilda
    )
    
    data class AppInfo(
        val name: String,
        val packageName: String,
        val isSystemApp: Boolean,
        val riskLevel: String
    )

    /**
     * Qurilmani to'liq skanerlash
     */
    fun performFullAnalysis(): FullDeviceReport {
        return FullDeviceReport(
            // Battery
            batteryLevel = getBatteryLevel(),
            isCharging = isCharging(),
            batteryTemperature = getBatteryTemperature(),
            batteryHealth = getBatteryHealth(),
            
            // Storage
            totalStorageGb = getTotalStorage(),
            freeStorageGb = getFreeStorage(),
            usedStoragePercent = getUsedStoragePercent(),
            
            // Memory
            totalRamMb = getTotalRam(),
            availableRamMb = getAvailableRam(),
            usedRamPercent = getUsedRamPercent(),
            
            // CPU
            cpuUsagePercent = getCpuUsage(),
            
            // Network
            networkType = getNetworkType(),
            isVpnActive = isVpnActive(),
            isUsbTethering = isUsbTethering(),
            isHotspotActive = isHotspotActive(),
            mobileDataUsedMb = getMobileDataUsage(),
            wifiDataUsedMb = getWifiDataUsage(),
            
            // Apps
            runningAppsCount = getRunningAppsCount(),
            backgroundProcesses = getBackgroundProcesses(),
            recentlyInstalledApps = getRecentlyInstalledApps(),
            suspiciousApps = getSuspiciousApps(),
            
            // Security
            isDevModeEnabled = isDevModeEnabled(),
            isAdbEnabled = isAdbEnabled(),
            isUnknownSourcesAllowed = isUnknownSourcesAllowed(),
            isScreenLockSet = isScreenLockSet(),
            isRooted = isDeviceRooted(),
            
            // System
            androidVersion = "${Build.VERSION.RELEASE} (API ${Build.VERSION.SDK_INT})",
            deviceModel = "${Build.MANUFACTURER} ${Build.MODEL}",
            uptime = getUptime()
        )
    }

    // ==================== BATTERY ====================
    
    private fun getBatteryLevel(): Int {
        val bm = context.getSystemService(Context.BATTERY_SERVICE) as BatteryManager
        return bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
    }
    
    private fun isCharging(): Boolean {
        val bm = context.getSystemService(Context.BATTERY_SERVICE) as BatteryManager
        return bm.isCharging
    }
    
    private fun getBatteryTemperature(): Float {
        val intent = context.registerReceiver(null, android.content.IntentFilter(android.content.Intent.ACTION_BATTERY_CHANGED))
        val temp = intent?.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0) ?: 0
        return temp / 10f
    }
    
    private fun getBatteryHealth(): String {
        val intent = context.registerReceiver(null, android.content.IntentFilter(android.content.Intent.ACTION_BATTERY_CHANGED))
        return when (intent?.getIntExtra(BatteryManager.EXTRA_HEALTH, 0)) {
            BatteryManager.BATTERY_HEALTH_GOOD -> "Yaxshi ✅"
            BatteryManager.BATTERY_HEALTH_OVERHEAT -> "Qizib ketgan ⚠️"
            BatteryManager.BATTERY_HEALTH_DEAD -> "Buzilgan ❌"
            BatteryManager.BATTERY_HEALTH_COLD -> "Sovuq ❄️"
            else -> "Noma'lum"
        }
    }

    // ==================== STORAGE ====================
    
    private fun getTotalStorage(): Float {
        val stat = StatFs(Environment.getDataDirectory().path)
        return (stat.blockSizeLong * stat.blockCountLong) / (1024f * 1024f * 1024f)
    }
    
    private fun getFreeStorage(): Float {
        val stat = StatFs(Environment.getDataDirectory().path)
        return (stat.blockSizeLong * stat.availableBlocksLong) / (1024f * 1024f * 1024f)
    }
    
    private fun getUsedStoragePercent(): Int {
        val total = getTotalStorage()
        val free = getFreeStorage()
        return ((total - free) / total * 100).toInt()
    }

    // ==================== MEMORY (RAM) ====================
    
    private fun getTotalRam(): Long {
        val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val memInfo = ActivityManager.MemoryInfo()
        am.getMemoryInfo(memInfo)
        return memInfo.totalMem / (1024 * 1024)
    }
    
    private fun getAvailableRam(): Long {
        val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val memInfo = ActivityManager.MemoryInfo()
        am.getMemoryInfo(memInfo)
        return memInfo.availMem / (1024 * 1024)
    }
    
    private fun getUsedRamPercent(): Int {
        val total = getTotalRam()
        val available = getAvailableRam()
        return ((total - available).toFloat() / total * 100).toInt()
    }

    // ==================== CPU ====================
    
    private fun getCpuUsage(): Float {
        return try {
            val reader = RandomAccessFile("/proc/stat", "r")
            val load = reader.readLine()
            reader.close()
            
            val parts = load.split(" ").filter { it.isNotEmpty() }
            if (parts.size >= 5) {
                val user = parts[1].toLongOrNull() ?: 0
                val nice = parts[2].toLongOrNull() ?: 0
                val system = parts[3].toLongOrNull() ?: 0
                val idle = parts[4].toLongOrNull() ?: 0
                val total = user + nice + system + idle
                ((total - idle).toFloat() / total * 100)
            } else 0f
        } catch (e: Exception) {
            Timber.w(e, "CPU usage read failed")
            0f
        }
    }

    // ==================== NETWORK ====================
    
    private fun getNetworkType(): String {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = cm.activeNetwork ?: return "Ulanmagan"
        val caps = cm.getNetworkCapabilities(network) ?: return "Noma'lum"
        
        return when {
            caps.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> "Wi-Fi 📶"
            caps.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> "Mobil Internet 📱"
            caps.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> "Ethernet 🔌"
            caps.hasTransport(NetworkCapabilities.TRANSPORT_VPN) -> "VPN 🔒"
            else -> "Boshqa"
        }
    }
    
    private fun isVpnActive(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = cm.activeNetwork ?: return false
        val caps = cm.getNetworkCapabilities(network) ?: return false
        return caps.hasTransport(NetworkCapabilities.TRANSPORT_VPN)
    }
    
    private fun isUsbTethering(): Boolean {
        return try {
            val interfaces = NetworkInterface.getNetworkInterfaces() ?: return false
            interfaces.toList().any { it.isUp && (it.name.contains("rndis") || it.name.contains("usb0")) }
        } catch (e: Exception) { false }
    }
    
    private fun isHotspotActive(): Boolean {
        return try {
            val interfaces = NetworkInterface.getNetworkInterfaces() ?: return false
            interfaces.toList().any { it.isUp && (it.name.contains("ap0") || it.name.contains("wlan0")) }
        } catch (e: Exception) { false }
    }
    
    private fun getMobileDataUsage(): Long {
        return TrafficStats.getMobileRxBytes() / (1024 * 1024)
    }
    
    private fun getWifiDataUsage(): Long {
        return (TrafficStats.getTotalRxBytes() - TrafficStats.getMobileRxBytes()) / (1024 * 1024)
    }

    // ==================== APPS & PROCESSES ====================
    
    private fun getRunningAppsCount(): Int {
        val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        return am.runningAppProcesses?.size ?: 0
    }
    
    private fun getBackgroundProcesses(): List<ProcessInfo> {
        val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val processes = am.runningAppProcesses ?: return emptyList()
        
        return processes.take(10).map { process ->
            val memInfo = android.os.Debug.MemoryInfo()
            val importance = when (process.importance) {
                ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND -> "Faol"
                ActivityManager.RunningAppProcessInfo.IMPORTANCE_VISIBLE -> "Ko'rinadi"
                ActivityManager.RunningAppProcessInfo.IMPORTANCE_SERVICE -> "Xizmat"
                ActivityManager.RunningAppProcessInfo.IMPORTANCE_BACKGROUND -> "Orqa fonda"
                else -> "Kam muhim"
            }
            
            val explanation = generateProcessExplanation(process.processName, importance)
            
            ProcessInfo(
                name = process.processName.substringAfterLast("."),
                memoryMb = 0, // We can't get accurate memory without debug permissions
                importance = importance,
                explanation = explanation
            )
        }
    }
    
    private fun generateProcessExplanation(processName: String, importance: String): String {
        val name = processName.lowercase()
        return when {
            name.contains("google") -> "Google xizmatlari - Sinxronizatsiya va bildirishnomalar uchun"
            name.contains("system") -> "Tizim jarayoni - Android ishlashi uchun zarur"
            name.contains("keyboard") || name.contains("inputmethod") -> "Klaviatura xizmati - Yozish uchun"
            name.contains("launcher") -> "Bosh ekran - Ilovalarni ko'rsatish"
            name.contains("phone") -> "Telefon xizmati - Qo'ng'iroqlar uchun"
            name.contains("messaging") || name.contains("sms") -> "SMS xizmati - Xabarlar uchun"
            name.contains("location") || name.contains("gps") -> "Joylashuv xizmati - GPS uchun"
            name.contains("camera") -> "Kamera xizmati - Rasm/Video uchun"
            name.contains("music") || name.contains("media") -> "Media pleyer - Musiqa/Video"
            name.contains("download") -> "Yuklab olish menejeri"
            name.contains("bluetooth") -> "Bluetooth xizmati"
            name.contains("wifi") -> "Wi-Fi xizmati"
            name.contains("battery") -> "Batareya optimallashtirish"
            name.contains("security") || name.contains("antivirus") -> "Xavfsizlik xizmati ✅"
            name.contains("telegram") -> "Telegram - Xabar almashish"
            name.contains("whatsapp") -> "WhatsApp - Xabar almashish"
            name.contains("instagram") -> "Instagram - Ijtimoiy tarmoq"
            name.contains("facebook") -> "Facebook - Ijtimoiy tarmoq"
            name.contains("cyber") || name.contains("hackdefender") -> "Cyber Brother PRO - Sizning himoyangiz 🛡️"
            else -> "$importance holatida ishlamoqda"
        }
    }
    
    private fun getRecentlyInstalledApps(): List<AppInfo> {
        val pm = context.packageManager
        val packages = pm.getInstalledPackages(0)
        val now = System.currentTimeMillis()
        val threeDaysAgo = now - (3 * 24 * 60 * 60 * 1000)
        
        return packages
            .filter { it.firstInstallTime > threeDaysAgo }
            .map { pkg ->
                val isSystem = (pkg.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM) != 0
                AppInfo(
                    name = pm.getApplicationLabel(pkg.applicationInfo).toString(),
                    packageName = pkg.packageName,
                    isSystemApp = isSystem,
                    riskLevel = "YANGI"
                )
            }
    }
    
    private fun getSuspiciousApps(): List<AppInfo> {
        val pm = context.packageManager
        val packages = pm.getInstalledPackages(PackageManager.GET_PERMISSIONS)
        val suspicious = mutableListOf<AppInfo>()
        
        // Ishonchli ilovalar - bu ilovalar ko'p ruxsatga ega bo'lsa ham xavfli emas
        val trustedPackages = setOf(
            // Messengerlar
            "org.telegram.messenger",
            "org.telegram.messenger.web",
            "org.thunderdog.challegram",  // Telegram X
            "com.whatsapp",
            "com.whatsapp.w4b",  // WhatsApp Business
            "com.facebook.orca",  // Messenger
            "com.viber.voip",
            "com.skype.raider",
            "com.discord",
            "com.snapchat.android",
            
            // Ijtimoiy tarmoqlar
            "com.instagram.android",
            "com.facebook.katana",  // Facebook
            "com.twitter.android",
            "com.zhiliaoapp.musically",  // TikTok
            "com.linkedin.android",
            "com.pinterest",
            
            // Google ilovalar
            "com.google.android.apps.messaging",
            "com.google.android.apps.photos",
            "com.google.android.gm",  // Gmail
            "com.google.android.apps.maps",
            "com.google.android.youtube",
            "com.google.android.apps.docs",
            "com.google.android.apps.meetings",  // Meet
            
            // Banklar va to'lov tizimlari
            "com.click.uzsmart",
            "uz.click.mobilbank",
            "uz.paynet.android",
            "com.payme.app",
            "com.uzumbank",
            "com.apelsin",
            "com.kapitalbank",
            
            // Boshqa mashhur ilovalar
            "com.spotify.music",
            "com.yandex.browser",
            "com.yandex.taxi",
            "com.yandex.music",
            "ru.yandex.yandexnavi",
            "com.ubercab",
            "com.uber.driver",
            "com.amazon.mShop.android.shopping",
            "com.ebay.mobile",
            "com.aliexpress.app",
            
            // Xavfsizlik ilovalari
            "com.eps.android",  // Cyber Brother
            "com.avast.android.mobilesecurity",
            "com.bitdefender.security",
            "com.kaspersky.security.cloud",
            "com.antivirus",
            "org.malwarebytes.antimalware"
        )
        
        val dangerousPermissions = setOf(
            "android.permission.READ_SMS",
            "android.permission.SEND_SMS",
            "android.permission.READ_CALL_LOG",
            "android.permission.RECORD_AUDIO",
            "android.permission.READ_CONTACTS",
            "android.permission.ACCESS_FINE_LOCATION"
        )
        
        packages.forEach { pkg ->
            val isSystem = (pkg.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM) != 0
            val isTrusted = pkg.packageName in trustedPackages
            
            // System va trusted ilovalarni o'tkazib yuborish
            if (!isSystem && !isTrusted) {
                val perms = pkg.requestedPermissions ?: emptyArray()
                val dangerCount = perms.count { it in dangerousPermissions }
                
                // Faqat 5+ xavfli ruxsatga ega va ishonchsiz ilovalar
                if (dangerCount >= 5) {
                    suspicious.add(AppInfo(
                        name = pm.getApplicationLabel(pkg.applicationInfo).toString(),
                        packageName = pkg.packageName,
                        isSystemApp = false,
                        riskLevel = if (dangerCount >= 6) "YUQORI" else "O'RTA"
                    ))
                }
            }
        }
        
        return suspicious
    }

    // ==================== SECURITY ====================
    
    private fun isDevModeEnabled(): Boolean {
        return Settings.Global.getInt(context.contentResolver, Settings.Global.DEVELOPMENT_SETTINGS_ENABLED, 0) == 1
    }
    
    private fun isAdbEnabled(): Boolean {
        return Settings.Global.getInt(context.contentResolver, Settings.Global.ADB_ENABLED, 0) == 1
    }
    
    private fun isUnknownSourcesAllowed(): Boolean {
        return try {
            Settings.Secure.getInt(context.contentResolver, Settings.Secure.INSTALL_NON_MARKET_APPS, 0) == 1
        } catch (e: Exception) { false }
    }
    
    private fun isScreenLockSet(): Boolean {
        val km = context.getSystemService(Context.KEYGUARD_SERVICE) as android.app.KeyguardManager
        return km.isDeviceSecure
    }
    
    private fun isDeviceRooted(): Boolean {
        val paths = arrayOf(
            "/system/bin/su", "/system/xbin/su", "/sbin/su",
            "/system/sd/xbin/su", "/system/bin/failsafe/su",
            "/data/local/su", "/data/local/bin/su", "/data/local/xbin/su"
        )
        return paths.any { File(it).exists() } ||
               Build.TAGS?.contains("test-keys") == true
    }

    // ==================== SYSTEM ====================
    
    private fun getUptime(): String {
        val uptimeMs = android.os.SystemClock.elapsedRealtime()
        val hours = uptimeMs / (1000 * 60 * 60)
        val minutes = (uptimeMs % (1000 * 60 * 60)) / (1000 * 60)
        return "${hours} soat ${minutes} daqiqa"
    }

    /**
     * Foydalanuvchiga tushunarli tilda to'liq hisobot
     */
    fun generateHumanReadableReport(lang: String = "uz"): String {
        val report = performFullAnalysis()
        
        val sb = StringBuilder()
        
        when (lang) {
            "uz" -> {
                sb.appendLine("📱 QURILMA TO'LIQ TAHLILI")
                sb.appendLine("═══════════════════════════")
                sb.appendLine()
                sb.appendLine("🔋 BATAREYA")
                sb.appendLine("   Quvvat: ${report.batteryLevel}%")
                sb.appendLine("   Holat: ${if (report.isCharging) "Quvvatlanmoqda ⚡" else "Quvvatlanmayapti"}")
                sb.appendLine("   Harorat: ${report.batteryTemperature}°C")
                sb.appendLine("   Salomatligi: ${report.batteryHealth}")
                sb.appendLine()
                sb.appendLine("💾 XOTIRA")
                sb.appendLine("   Jami: ${"%.1f".format(report.totalStorageGb)} GB")
                sb.appendLine("   Bo'sh: ${"%.1f".format(report.freeStorageGb)} GB")
                sb.appendLine("   Ishlatilgan: ${report.usedStoragePercent}%")
                sb.appendLine()
                sb.appendLine("🧠 OPERATIV XOTIRA (RAM)")
                sb.appendLine("   Jami: ${report.totalRamMb} MB")
                sb.appendLine("   Bo'sh: ${report.availableRamMb} MB")
                sb.appendLine("   Ishlatilgan: ${report.usedRamPercent}%")
                sb.appendLine()
                sb.appendLine("🌐 TARMOQ")
                sb.appendLine("   Turi: ${report.networkType}")
                sb.appendLine("   VPN: ${if (report.isVpnActive) "Faol ✅" else "O'chirilgan"}")
                sb.appendLine("   USB Tethering: ${if (report.isUsbTethering) "Faol 🔌" else "Yo'q"}")
                sb.appendLine()
                sb.appendLine("📲 ILOVALAR")
                sb.appendLine("   Ishlaydigan: ${report.runningAppsCount} ta")
                sb.appendLine("   Yangi o'rnatilgan: ${report.recentlyInstalledApps.size} ta")
                sb.appendLine("   Shubhali: ${report.suspiciousApps.size} ta")
                sb.appendLine()
                sb.appendLine("🔒 XAVFSIZLIK")
                sb.appendLine("   Ekran qulfi: ${if (report.isScreenLockSet) "O'rnatilgan ✅" else "YO'Q ⚠️"}")
                sb.appendLine("   Dasturchi rejimi: ${if (report.isDevModeEnabled) "Yoqilgan ⚠️" else "O'chirilgan ✅"}")
                sb.appendLine("   ADB: ${if (report.isAdbEnabled) "Faol ⚠️" else "O'chirilgan ✅"}")
                sb.appendLine("   Noma'lum manbalar: ${if (report.isUnknownSourcesAllowed) "Ruxsat ⚠️" else "Bloklangan ✅"}")
                sb.appendLine("   Root: ${if (report.isRooted) "HA ⚠️" else "YO'Q ✅"}")
                sb.appendLine()
                sb.appendLine("📋 TIZIM")
                sb.appendLine("   Android: ${report.androidVersion}")
                sb.appendLine("   Qurilma: ${report.deviceModel}")
                sb.appendLine("   Yoniq vaqt: ${report.uptime}")
            }
            else -> {
                sb.appendLine("📱 FULL DEVICE ANALYSIS")
                sb.appendLine("═══════════════════════════")
                sb.appendLine()
                sb.appendLine("🔋 BATTERY: ${report.batteryLevel}% ${if (report.isCharging) "⚡" else ""}")
                sb.appendLine("💾 STORAGE: ${"%.1f".format(report.freeStorageGb)} GB free of ${"%.1f".format(report.totalStorageGb)} GB")
                sb.appendLine("🧠 RAM: ${report.availableRamMb} MB free (${report.usedRamPercent}% used)")
                sb.appendLine("🌐 NETWORK: ${report.networkType}")
                sb.appendLine("📲 RUNNING APPS: ${report.runningAppsCount}")
                sb.appendLine("🔒 SECURITY ISSUES: ${listOf(report.isDevModeEnabled, report.isAdbEnabled, report.isUnknownSourcesAllowed, !report.isScreenLockSet).count { it }}")
            }
        }
        
        return sb.toString()
    }
}
