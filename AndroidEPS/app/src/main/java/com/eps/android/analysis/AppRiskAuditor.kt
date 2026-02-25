package com.eps.android.analysis

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRiskAuditor @Inject constructor(
    private val trustedAppDao: com.eps.android.data.TrustedAppDao
) {

    enum class RiskLevel { LOW, MEDIUM, HIGH, CRITICAL }

    data class RiskReport(
        val level: RiskLevel,
        val score: Int,
        val flags: List<String>,
        val explanation: String,
        val dangerousPermissions: List<String> = emptyList()
    )

    suspend fun auditPermissions(packageName: String, permissions: List<String>, isSystem: Boolean = false, isPlayStore: Boolean = false): RiskReport {
        // 1. Whitelist SELF, Play Store, and USER-TRUSTED Apps
        if (packageName == "com.eps.android") {
            return RiskReport(RiskLevel.LOW, 0, emptyList(), "Trusted: Cyber Brother Security Core")
        }
        
        if (isPlayStore) {
            return RiskReport(RiskLevel.LOW, 0, emptyList(), "Ishonchli Manba (Google Play Market)")
        }

        if (trustedAppDao.isTrusted(packageName)) {
            return RiskReport(RiskLevel.LOW, 0, emptyList(), "Foydalanuvchi Tomonidan Tasdiqlangan", emptyList())
        }
        
        // 2. Verified Safe Apps (Whitelist) - Common Apps in Uzbekistan & Global
        val safeApps = setOf(
            // Telegram Ecosystem
            "org.telegram.messenger", "org.telegram.messenger.web", "org.telegram.plus", "org.thunderdog.challegram", "org.aka.messenger",
            // Social & Communication
            "com.instagram.android", "com.instagram.lite", "com.instagram.barcelona", // Instagram, Threads
            "com.facebook.katana", "com.facebook.orca", "com.facebook.lite", // Facebook, Messenger
            "com.whatsapp", "com.whatsapp.w4b", "com.viber.voip", "us.zoom.videomeetings", "com.skype.raider",
            "com.snapchat.android", "com.twitter.android", "com.linkedin.android", "com.pinterest",
            "org.thoughtcrime.securesms", "com.imo.android.imoim", "ru.oneme.app", // Signal, imo, MAX
            "com.zhiliaoapp.musically", "com.ss.android.ugc.trill", // TikTok
            // Banking & Finance (Uzbekistan)
            "uz.tbc.mobile", "uz.tbcbank", "com.tbcgroup", "uz.tbc", // TBC
            "uz.alif.mobi", "uz.alif", "com.alif.mobi", "com.alif", // Alif
            "uz.click.uz", "uz.click", "air.com.ssd.click", "uz.fido.click", "com.click.uzsmart", "uz.click.mobilbank", // Click
            "uz.dida.payme", "uz.payme", "com.payme.app", // Payme
            "uz.uzcard.mobile", "uz.uzcard", "uz.humo.mobile", "uz.humo", // Card Apps
            "uz.shaxslar.kapitalbank", "uz.proweb.kapitalbank", "uz.kapitalbank", // Kapitalbank
            "uz.ipakyulibank.mobile", "uz.ipakyulibank", // Ipak Yuli
            "uz.agrobank.agro_mobile", "uz.agrobank", // Agrobank
            "uz.hamkorbank.mobile", "uz.hamkorbank", // Hamkorbank
            "uz.vcard.mobile", "uz.vcard", // Anorbank / Vcard
            "uz.ofb.mobile", "uz.ofb", // Orient Finans
            "uz.sqb.mobile", "uz.sqb", // SQB
            "uz.nbu.mobile", "uz.nbu", // NBU
            "uz.vcb.mobile", "uz.vcb", // Universal Bank
            "uz.zoodpay.app", "uz.paynet.android", "uz.xazna.app", "uz.avo.app", // ZoodPay, Paynet, Xazna, AVO
            "uz.soliq.mobile", "uz.zood.pay", "uz.zood.mall", // Soliq, Zood
            // Uzum Ecosystem
            "uz.uzum.bank", "uz.uzum.market", "uz.uzum.pay", "uz.uzum", "com.uzumbank",
            // Government & Public Services (Uzbekistan)
            "uz.uzinfocom.mygov", "uz.egov.oneid", "uz.uzinfocom.dmed", "uz.smartbase.myinspector",
            "uz.smartbase.license", "uz.iiv.meninginspektorim", "uz.agro.mobile",
            // E-commerce & Tools
            "uz.asaxiy.app", "uz.asaxiy", "uz.olx.uz", "uz.olx", "uz.doska.birbir",
            "com.alibaba.aliexpress", "com.amazon.mShop.android.shopping", "com.ebay.mobile",
            "com.edi.mob", "uz.allplay.app", "uz.beeline.uz", "uz.mobiuz.mobi", // Allplay, Beeline, Mobiuz
            // Education & AI
            "uz.ibrat.academy", "uz.ibrat", "uz.upscrolled", "com.upscrolled.app",
            "com.duolingo", "com.google.android.apps.classroom", "ai.saveall.app", // Duolingo, Classroom, Gizmo
            "com.openai.chatgpt", "com.google.android.apps.bard", "com.google.android.apps.gemini",
            "com.pixverseai.pixverse", // PixVerse
            // Media & Entertainment
            "com.spotify.music", "com.netflix.mediaclient", "com.shazam.android",
            "com.lemon.lvoverseas", "com.cyberlink.powerdirector", // CapCut, PowerDirector
            "com.jana.tube.video", "com.google.android.youtube", // JanaTube, YouTube
            // Transportation & Navigation
            "ru.yandex.taxi.uz", "ru.yandex.taxi", "ru.yandex.searchplugin", "com.yandex.browser", 
            "ru.yandex.yandexmaps", "ru.yandex.yandexnavi", "uz.mytaxi.client", "uz.mytaxi", "uz.express.customer",
            "com.google.android.apps.maps", "com.ubercab",
            // Games (Popular in Uzbekistan & Global)
            "com.tencent.ig", "com.tencent.iglite", "com.tencent.mobileqq", // PUBG
            "com.mobile.legends", "com.ea.gp.fifamobile", "com.roblox.client", // MLBB, FC Mobile, Roblox
            "com.kiloo.subwaysurf", "com.fgol.HungrySharkEvolution", "com.dts.freefireth", // Subway Surfers, Hungry Shark, Free Fire
            "com.PlayMax.playergames", "com.ForgeGames.SpecialForcesGroup2", // Stickman Party, SFG2
            "com.hungry.block.blast", "com.supercent.pizzaready", "com.candyroom.schoolpartycraft", // Block Blast, Pizza Ready
            "com.crazy.block.robo.monster.cliffs.craft", "com.launcher.brgame", "com.kiloo.subwaysurf",
            "com.mojang.minecraftpe", "com.supercell.clashofclans", "com.supercell.brawlstars",
            "com.king.candycrushsaga", "com.brussia.launcher", "com.ikame.carrace",
            // System & Utilities
            "com.android.chrome", "org.mozilla.firefox", "com.opera.browser", "com.microsoft.emmx",
            "com.google.android.apps.messaging", "com.google.android.apps.photos", "com.google.android.gm",
            "ru.hh.android", "uz.gizmo", "com.gizmo", "com.edaai.globalmove",
            "ge.space.app.uzbekistan"
        )
        
        val normalizedPackage = packageName.trim().lowercase()
        val isTbcOrAlif = normalizedPackage.contains("tbc") || normalizedPackage.contains("alif") || 
                          normalizedPackage.contains("click") || normalizedPackage.contains("payme") ||
                          normalizedPackage.contains("uzum") || normalizedPackage.contains("ge.space")

        if (safeApps.contains(normalizedPackage) || safeApps.any { normalizedPackage.startsWith("$it.") } || isTbcOrAlif) {
             val explanation = if (isTbcOrAlif) "Tasdiqlangan Milliy Ilova (Verified App)" else "Ishonchli Manba (Trusted Source)"
             return RiskReport(RiskLevel.LOW, 0, emptyList(), explanation, emptyList())
        }
        
        // Detailed Transsion (Tecno/Infinix) System Component Whitelist
        if (packageName.contains("transsion") || packageName.contains("tecno") || packageName.contains("infinix")) {
            if (isSystem) return RiskReport(RiskLevel.LOW, 0, emptyList(), "Tizim Komponenti (System)", emptyList())
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
        val hasSms = flags.contains("SMS_INTERCEPTION")
        val hasMic = dangerousList.contains("Microphone")
        val hasCamera = dangerousList.contains("Camera")
        val hasLocation = dangerousList.contains("Location")
        val hasContacts = dangerousList.contains("Contacts")
        
        if (hasAccessibility && hasOverlay && hasInternet) {
            score += 50 // Banking Trojan signature
            flags.add("PATTERN_BANKING_TROJAN")
        } 
        
        if (hasSms && hasInternet) {
            score += 40 // OTP Stealer / SMS Spy
            flags.add("PATTERN_SMS_SPYWARE")
        }

        if (hasMic && hasCamera && hasInternet) {
            score += 35 // Remote Access Trojan (RAT)
            flags.add("PATTERN_REMOTE_SPYING")
        }

        if (hasLocation && hasContacts && hasInternet) {
            score += 20 // Data Harvesting
            flags.add("PATTERN_DATA_MINING")
        }

        if (hasAccessibility && hasInternet) {
            score += 30 // Remote Control / Screen Scrapper
            flags.add("SUSPICIOUS_REMOTE_CONTROL")
        }

        // Adjust for system apps
        if (isSystem) {
            score = (score * 0.5f).toInt() // Reduce risk for system-signed apps
            flags.add("SYSTEM_APP_TRUSTED")
        }

        val finalScore = score.coerceAtMost(100)
        var level = when {
            finalScore >= 85 -> RiskLevel.CRITICAL
            finalScore >= 60 -> RiskLevel.HIGH
            finalScore >= 30 -> RiskLevel.MEDIUM
            else -> RiskLevel.LOW
        }

        // System apps should never be CRITICAL/HIGH unless they are extremely suspicious
        if (isSystem && level != RiskLevel.LOW) {
            level = RiskLevel.MEDIUM 
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
        
        if (flags.contains("PATTERN_BANKING_TROJAN")) {
            builder.append(". 🛑 BANKING ATTACK: Bu ilova banking ilovalaringiz ustidan soxta oyna qo'yishi va parolingizni o'g'irlashi mumkin.")
        }
        if (flags.contains("PATTERN_SMS_SPYWARE")) {
            builder.append(". 🛑 SMS SPY: OTP kodlarni o'g'irlash va internetga yuborish xavfi mavjud.")
        }
        if (flags.contains("PATTERN_REMOTE_SPYING")) {
            builder.append(". 🛑 RAT SPY: Mikrofon va kamera orqali sizni yashirin kuzatish xavfi.")
        }
        
        if (isSystem) {
            builder.append(". (Android tizimiga tegishli xavfsiz servis)")
        }
        
        return builder.toString()
    }
}
