package com.eps.android.analysis

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRiskAuditor @Inject constructor() {

    enum class RiskLevel { LOW, MEDIUM, HIGH, CRITICAL }

    data class RiskReport(
        val level: RiskLevel,
        val score: Int,
        val flags: List<String>,
        val explanation: String,
        val dangerousPermissions: List<String> = emptyList()
    )

    fun auditPermissions(packageName: String, permissions: List<String>, isSystem: Boolean = false): RiskReport {
        // 1. Whitelist SELF (Cyber Brother)
        if (packageName == "com.eps.android") {
            return RiskReport(RiskLevel.LOW, 0, emptyList(), "Trusted: Cyber Brother Security Core")
        }
        
        // 2. Verified Safe Apps (Whitelist)
        val safeApps = listOf(
            "org.telegram.messenger", "org.telegram.messenger.web", "org.telegram.plus", // Telegram
            "com.instagram.android", // Instagram
            "com.facebook.katana", "com.facebook.orca", // Facebook
            "com.whatsapp", // WhatsApp
            "us.zoom.videomeetings", // Zoom
            "com.yandex.taxi", "ru.yandex.taxi", // Yandex Go & Map
            "uz.dida.payme", "uz.payme.business", // Payme
            "air.com.ssd.click.android", // Click
            "uz.uzcard.mobile", // Uzcard
            "com.google.android", // Google Apps (Play Services, Google App, etc.)
            "com.android", // Android System Apps (Phone, Settings, etc.)
            "com.samsung.android", // Samsung System Apps
            "com.huawei", "com.xiaomi", "com.miui", // Other OEM Systems
            "tj.alif.mobi", // Alif
            "com.transsion", // Tecno/Infinix System Apps (Folax, Tranfac, etc.)
            "com.mediatek" // Mediatek System Apps
        )
        
        if (safeApps.any { packageName.startsWith(it) }) {
             return RiskReport(RiskLevel.LOW, 0, emptyList(), "Tizim ilovasi / Ishonchli (System/Safe)", emptyList())
        }
        
        // Custom Whitelist for specific harmless apps reported by user
        if (packageName == "com.transsion.aivoiceassistant" || // Folax
            packageName == "com.transsion.agingfunction" ||
            packageName == "com.transsion.camera" || 
            packageName == "com.transsion.faceid" || 
            packageName == "com.transsion.letswitch" ||
            packageName == "com.transsion.tranfacmode" ||
            packageName == "com.transsion.mol") { // Folax Translate
            return RiskReport(RiskLevel.LOW, 0, emptyList(), "System Component (Safe)", emptyList())
        }


        var score = 0
        val flags = mutableListOf<String>()
        val dangerousList = mutableListOf<String>()
        
        // Detailed Permission Mapping for UI and Scoring
        if (permissions.contains("android.permission.CAMERA")) {
            dangerousList.add("Camera")
            score += 10
        }
        if (permissions.contains("android.permission.RECORD_AUDIO")) {
            dangerousList.add("Microphone")
            score += 15
        }
        if (permissions.contains("android.permission.ACCESS_FINE_LOCATION") || permissions.contains("android.permission.ACCESS_COARSE_LOCATION")) {
            dangerousList.add("Location")
            score += 10
        }
        if (permissions.contains("android.permission.READ_CONTACTS")) {
            dangerousList.add("Contacts")
            score += 10
        }
        if (permissions.contains("android.permission.READ_SMS") || permissions.contains("android.permission.RECEIVE_SMS")) {
            dangerousList.add("SMS")
            score += 25
            flags.add("SMS_INTERCEPTION")
        }
        if (permissions.contains("android.permission.READ_EXTERNAL_STORAGE") || permissions.contains("android.permission.MANAGE_EXTERNAL_STORAGE")) {
            dangerousList.add("Storage")
            score += 5
        }
        if (permissions.contains("android.permission.READ_CALL_LOG") || permissions.contains("android.permission.WRITE_CALL_LOG")) {
            dangerousList.add("Call Logs")
            score += 10
        }
        
        // Critical system-level permissions
        if (permissions.contains("android.permission.BIND_ACCESSIBILITY_SERVICE")) {
            score += 40
            flags.add("ACCESSIBILITY_SERVICE")
            dangerousList.add("Accessibility Control")
        }
        if (permissions.contains("android.permission.SYSTEM_ALERT_WINDOW")) {
            // REDUCED SCORE: Overlay alone is common. Only dangerous if combined.
            score += 15 
            flags.add("OVERLAY_WINDOW")
            dangerousList.add("Draw Over Apps")
        }
        if (permissions.contains("android.permission.PACKAGE_USAGE_STATS")) {
            score += 20
            flags.add("USAGE_STATISTICS_ACCESS")
            dangerousList.add("Usage Access")
        }
        
        // Malicious Combinations logic
        val hasInternet = permissions.contains("android.permission.INTERNET")
        val hasAccessibility = flags.contains("ACCESSIBILITY_SERVICE")
        val hasOverlay = flags.contains("OVERLAY_WINDOW")
        
        if (hasAccessibility && hasOverlay && hasInternet) {
            score += 50 // Massive boost for banking Trojan signature
            flags.add("CRITICAL_COMBINATION_BANKING_FRAUD")
        } else if (hasAccessibility && hasInternet) {
            score += 30
            flags.add("SUSPICIOUS_REMOTE_CONTROL")
        }

        // Adjust for system apps
        if (isSystem) {
            score = (score * 0.5f).toInt() // Reduce risk for system-signed apps
            flags.add("SYSTEM_APP_TRUSTED")
        }

        val finalScore = score.coerceAtMost(100)
        val level = when {
            finalScore >= 85 -> RiskLevel.CRITICAL
            finalScore >= 60 -> RiskLevel.HIGH
            finalScore >= 30 -> RiskLevel.MEDIUM
            else -> RiskLevel.LOW
        }

        val explanation = buildExplanation(flags, isSystem)

        return RiskReport(level, finalScore, flags, explanation, dangerousList)
    }

    private fun buildExplanation(flags: List<String>, isSystem: Boolean): String {
        if (flags.isEmpty()) return "No suspicious permissions detected."
        
        val builder = StringBuilder(if (isSystem) "Tizim komponenti: " else "Aniqlangan risklar: ")
        flags.filter { it != "SYSTEM_APP_TRUSTED" }.forEachIndexed { index, flag ->
            builder.append(flag.replace("_", " "))
            if (index < flags.size - 2) builder.append(", ")
        }
        
        if (flags.contains("CRITICAL_COMBINATION_BANKING_FRAUD")) {
            builder.append(". WARNING: Bu ilova ekraningizni boshqarishi yoki bank ma'lumotlarini o'g'irlashi mumkin bo'lgan ruxsatnomalarga ega.")
        }
        
        if (isSystem) {
            builder.append(". (Android tizimiga tegishli xavfsiz servis)")
        }
        
        return builder.toString()
    }
}
