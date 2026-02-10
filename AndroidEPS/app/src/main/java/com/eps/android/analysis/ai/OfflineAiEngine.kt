package com.eps.android.analysis.ai

import android.content.Context
import com.eps.android.data.ThreatEvent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import timber.log.Timber

/**
 * Offline AI Engine PRO - Rule-Based Context-Aware System
 * 100% Offline, no external dependencies
 * Qurilmani 100% tahlil qiladi va foydalanuvchiga tushunarli tilda ma'lumot beradi
 */
@Singleton
class OfflineAiEngine @Inject constructor(
    @ApplicationContext private val context: Context,
    private val deviceAnalyzer: DeviceAnalyzer
) {

    private var lastDeviceStats: DeviceStatus? = null
    private var cachedFullReport: DeviceAnalyzer.FullDeviceReport? = null

    init {
        Timber.i("✅ Offline PRO AI Engine Initialized")
    }

    /**
     * Generate response based on user query and system threats
     */
    data class DeviceStatus(
        val batteryLevel: Int,
        val freeStorageGb: String,
        val isCharging: Boolean,
        val connectionMode: String,
        val isVpnActive: Boolean
    )

    fun respond(query: String, threats: List<ThreatEvent>, deviceStats: DeviceStatus): String {
        lastDeviceStats = deviceStats
        val q = query.lowercase()
        val lang = context.resources.configuration.locales[0].language
        
        // 🚀 PRO: To'liq qurilma tahlili
        if (q.contains("to'liq") || q.contains("toliq") || q.contains("full") || q.contains("batafsil") || q.contains("полный") || q.contains("подробный") || q.contains("analyze") || q.contains("tahlil") || q.contains("tekshir") || q.contains("audit")) {
            return performFullDeviceAnalysis(lang)
        }
        
        // 🚀 PRO: Background jarayonlar
        if (q.contains("background") || q.contains("orqa fon") || q.contains("jarayon") || q.contains("process") || q.contains("nima ishlayapti") || q.contains("фоновые") || q.contains("процессы")) {
            return explainBackgroundProcesses(lang)
        }
        
        // 🚀 PRO: Shubhali ilovalar
        if (q.contains("shubhali") || q.contains("xavfli") || q.contains("dangerous") || q.contains("suspicious") || q.contains("подозрительны") || q.contains("опасны")) {
            return explainSuspiciousApps(lang)
        }
        
        // 🚀 PRO: RAM va CPU
        if (q.contains("ram") || q.contains("cpu") || q.contains("protsessor") || q.contains("operativ") || q.contains("процессор") || q.contains("оперативная")) {
            return explainResourceUsage(lang)
        }
        
        // 🚀 PRO: Tarmoq tahlili
        if (q.contains("tarmoq") || q.contains("network") || q.contains("internet") || q.contains("wifi") || q.contains("сеть") || q.contains("интернет")) {
            return explainNetworkStatus(lang)
        }
        
        // Device Stats - Batareya
        if (q.contains("batareya") || q.contains("zaryad") || q.contains("quvvat") || q.contains("battery") || q.contains("батарея") || q.contains("заряд")) {
            return explainBatteryStatus(lang)
        }
        
        // Xotira
        if (q.contains("joy") || q.contains("xotira") || q.contains("storage") || q.contains("memory") || q.contains("память") || q.contains("место")) {
            return explainStorageStatus(lang)
        }
        
        if (q.contains("usb") || q.contains("kabel") || q.contains("cable") || q.contains("ulangan") || q.contains("internet") || q.contains("кабель") || q.contains("подключен")) {
            if (deviceStats.connectionMode == "USB_TETHERING") {
                return when(lang) {
                    "uz" -> "🔌 USB TETHERING FAOLLASHGAN: Siz hozirda USB kabel orqali boshqa qurilmaga (masalan, Laptopga) internet tarqatyapsiz (RNDIS/Modem rejimi). Bu shunchaki quvvatlash emas!"
                    "ru" -> "🔌 USB-МОДЕМ АКТИВИРОВАН: Вы раздаете интернет другому устройству (например, ноутбуку) через USB-кабель. Это не просто зарядка!"
                    else -> "🔌 USB TETHERING ACTIVE: You are sharing your mobile internet with another device (like a Laptop) via USB Cable (RNDIS Mode). This is more than just charging!"
                }
            }
            
            if (q.contains("usb") || q.contains("kabel") || q.contains("кабель")) {
                val chargeStatus = if (deviceStats.isCharging) {
                    when(lang) {
                        "uz" -> "quvvatlanmoqda ⚡"
                        "ru" -> "заряжается ⚡"
                        else -> "charging ⚡"
                    }
                } else {
                    when(lang) {
                        "uz" -> "quvvatlanmayapti"
                        "ru" -> "не заряжается"
                        else -> "not charging"
                    }
                }
                return when(lang) {
                    "uz" -> "🔌 USB holati: Qurilma $chargeStatus. Agar internet tarqatmoqchi bo'lsangiz, Sozlamalardan 'USB Tethering'ni yoqing."
                    "ru" -> "🔌 Статус USB: Устройство $chargeStatus. Если вы хотите раздать интернет, включите 'USB-модем' в настройках."
                    else -> "🔌 USB Status: Device is $chargeStatus. If you want to share internet, enable 'USB Tethering' in Settings."
                }
            }
        }

        // 1. Check for specific system activity questions
        val activityResponse = checkSystemActivity(q, threats, lang)
        if (activityResponse != null) return activityResponse

        // 2. Rule-Based / Master Template Engine (Context Aware)
        val criticalCount = threats.count { it.severity in listOf("CRITICAL", "HIGH") }
        
        return when(lang) {
            "uz" -> handleUzbekMasterResponse(q, criticalCount, threats)
            "ru" -> handleRussianMasterResponse(q, criticalCount, threats)
            else -> handleEnglishMasterResponse(q, criticalCount, threats)
        }
    }

    private fun checkSystemActivity(q: String, threats: List<ThreatEvent>, lang: String): String? {
        val isAskingAboutChange = q.contains(Regex("nima o'zgardi|nima bo'ldi|nima yangilik|what changed|what happened|что изменилось|что случилось|что нового"))
        if (isAskingAboutChange) {
            if (threats.isEmpty()) {
                return when(lang) {
                    "uz" -> "🛡️ Tizimda hozircha hech qanday shubhali o'zgarish yo'q. Hamma narsa nazorat ostida."
                    "ru" -> "🛡️ В системе пока нет никаких подозрительных изменений. Все под контролем."
                    else -> "🛡️ No suspicious changes detected in the system. Everything is under control."
                }
            }
            return buildMasterNarrative(threats, lang)
        }
        return null
    }

    private fun buildMasterNarrative(threats: List<ThreatEvent>, lang: String): String {
        val latest = threats.first()
        return when(lang) {
            "uz" -> "Oxirgi o'zgarish: ${explainEvent(latest, "uz")}\n\nBundan tashqari, men tizimda yana ${threats.size - 1} ta holatni kuzatib turibman."
            "ru" -> "Последнее изменение: ${explainEvent(latest, "ru")}\n\nКроме того, я отслеживаю еще ${threats.size - 1} событий в системе."
            else -> "Latest activity: ${explainEvent(latest, "en")}\n\nAdditionally, I am monitoring ${threats.size - 1} other system events."
        }
    }

    private fun explainEvent(event: ThreatEvent, lang: String): String {
        return when (event.type) {
            "APK_AUDIT" -> when(lang) {
                "uz" -> "Yangi ilova o'rnatildi. Men uni 'X-Ray' kabi ichini tekshirib chiqdim. Natija: ${event.details}"
                "ru" -> "Установлено новое приложение. Я проверил его внутренний код. Результат: ${event.details}"
                else -> "A new app was installed. I performed an X-Ray audit on its internal code. Result: ${event.details}"
            }
            "VPN_TUNNEL" -> when(lang) {
                "uz" -> "Internet trafigingizni xavfsiz tunneldan o'tkazyapman. Bu sizni soxta (phishing) saytlardan himoya qiladi."
                "ru" -> "Я пропускаю ваш интернет-трафик через защищенный туннель. Это защищает вас от фишинговых сайтов."
                else -> "I am tunneling your traffic securely. This shields you from phishing attempts."
            }
            "FILE_ENTROPY" -> when(lang) {
                "uz" -> "Tizimdagi shubhali fayl tahlili: Fayl ichidagi ma'lumotlar juda chalkash (yuqori entropiya). Bu ko'pincha virus yoki yashirin kod belgisi bo'ladi."
                "ru" -> "Анализ подозрительного файла: данные внутри файла сильно запутаны (высокая энтропия). Это часто является признаком вируса или скрытого кода."
                else -> "Analyzing suspicious file entropy: The data is highly randomized, which is a common indicator of encrypted malware."
            }
            "BOOT_EVENT" -> when(lang) {
                "uz" -> "Telefon yoqildi. Men tizimni birinchi bo'lib himoya ostiga oldim."
                "ru" -> "Телефон включен. Я первым делом взял систему под защиту."
                else -> "Device reboot detected. I have prioritized system protection protocols."
            }
            "SMS_RECEPTION" -> when(lang) {
                "uz" -> "Kelgan SMS tekshirildi. Undagi linklar tahlil ostida."
                "ru" -> "Полученное SMS проверено. Ссылки в нем анализируются."
                else -> "Incoming SMS verified. Links are being scanned for safety."
            }
            else -> event.details
        }
    }

    private fun handleUzbekMasterResponse(q: String, criticalCount: Int, threats: List<ThreatEvent>): String {
        return when {
            q.contains(Regex("salom|assalom|assalomu alaykum|qale|qalaysan|nima gap")) -> 
                "Assalomu alaykum! Men Cyber Brother — sizning shaxsiy kiber-himoyachingizman. Tizim xavfsizligi bo'yicha har qanday savol berishingiz mumkin."
            
            q.contains(Regex("ahvol|holat|status|sharoit|tinchlikmi|nima yangilik")) -> {
                generateSystemNarrative("uz", criticalCount, threats, lastDeviceStats)
            }

            q.contains(Regex("rahmat|hop|mayli|tushunarli|ok|yaxshi|zo'r")) -> 
                "Arzimaydi! Xavfsizligingiz — mening ustuvor vazifam. Yana savollaringiz bormi?"
            
            q.contains(Regex("nima bu|bu nima|qanaqa|qanday")) -> 
                "Men sizning telefoningizni viruslar, fishing hujumlari va josuslik dasturlaridan himoya qiluvchi aqlli tizimman. Hozirda 'Guardian Core' himoyasi faol."

            q.contains("vpn") -> "🛡️ VPN haqida: Men telefoningiz atrofida virtual 'devor' quryapman. Bu funksiya sizning internet trafigingizni shifrlaydi va zararli saytlarni bloklaydi."
            
            q.contains(Regex("fayl|apk|scan|virus")) -> {
                val lastApk = threats.find { it.type == "APK_AUDIT" }
                if (lastApk != null) "📁 Oxirgi ilova tahlili: ${lastApk.details}. Men doimiy ravishda yangi o'rnatilgan ilovalarni kuzatib boraman."
                else "📁 Men yuklanayotgan har bir fayl va ilovani sun'iy intellekt yordamida tekshiraman."
            }

            q.contains(Regex("aqillimisan|nimalarni bilasan|kimsan")) -> 
                "Men Cyber Brother — sun'iy intellekt asosida ishlovchi himoyachiman. Internet bo'lmasa ham, men har bir xavfni tahlil qila olaman."

            else -> {
                "Kechirasiz, savolingizni to'liq tushunmadim. Menga 'Tizim holati', 'VPN' yoki 'Xavfsizlik' haqida savol berishingiz mumkin."
            }
        }
    }

    private fun handleRussianMasterResponse(q: String, criticalCount: Int, threats: List<ThreatEvent>): String {
        return when {
            q.contains(Regex("привет|здравствуй|салам|как дела|как ты")) -> 
                "Здравствуйте! Я Cyber Brother — ваш личный кибер-хранитель. Готов ответить на вопросы о безопасности вашего устройства."
            
            q.contains(Regex("состояние|статус|как обстановка|что происходит|все нормально|новости")) -> {
                generateSystemNarrative("ru", criticalCount, threats, lastDeviceStats)
            }

            q.contains(Regex("спасибо|ок|хорошо|ладно|понял|ясно|круто")) -> 
                "Всегда пожалуйста! Ваша безопасность — мой приоритет. Есть еще вопросы?"

            q.contains(Regex("что это|кто ты|зачем это")) -> 
                "Я интеллектуальная система защиты, охраняющая ваш телефон от вирусов, фишинга и шпионажу. Сейчас активна защита 'Guardian Core'."
            
            q.contains("vpn") -> "🛡️ О VPN: Я создаю зашифрованный туннель для вашего интернета. Это блокирует рекламу и не дает хакерам перехватить ваши данные."
            
            q.contains(Regex("файл|апк|скан|apk|scan|вирус")) -> {
                val lastApk = threats.find { it.type == "APK_AUDIT" }
                if (lastApk != null) "📁 Последний анализ: ${lastApk.details}. Я постоянно слежу за новыми установками."
                else "📁 Я сканирую каждый новый файл и приложение с помощью нейросетей."
            }

            else -> {
                "Извините, я не совсем понял. Вы можете спросить меня о 'Состоянии системы', 'VPN' или 'Безопасности'."
            }
        }
    }

    private fun generateSystemNarrative(lang: String, criticalCount: Int, threats: List<ThreatEvent>, stats: DeviceStatus?): String {
        val sentiment = if (criticalCount == 0) {
            when(lang) {
                "uz" -> "Birodar, hozircha hammasi tinch! ✅"
                "ru" -> "Брат, пока все спокойно! ✅"
                else -> "Brother, everything is calm right now! ✅"
            }
        } else {
            when(lang) {
                "uz" -> "Diqqat! Tizimda xavf bor. ⚠️"
                "ru" -> "Внимание! В системе есть угроза. ⚠️"
                else -> "Attention! System is at risk. ⚠️"
            }
        }
        
        // 2. Battery & Power Context
        val batteryPart = if (stats != null) {
            val chargingText = if (stats.isCharging) {
                when(lang) {
                    "uz" -> "quvvatlanmoqda ⚡"
                    "ru" -> "заряжается ⚡"
                    else -> "charging ⚡"
                }
            } else ""
            when(lang) {
                "uz" -> "🔋 Quvvatimiz ${stats.batteryLevel}% $chargingText." 
                "ru" -> "🔋 Заряд ${stats.batteryLevel}% $chargingText."
                else -> "🔋 Battery is at ${stats.batteryLevel}% $chargingText."
            }
        } else ""

        // 3. Network & VPN Context
        val vpnPart = if (stats != null) {
            val vpnText = if (stats.isVpnActive) {
                when(lang) {
                    "uz" -> "Himoyalangan (VPN Faol) 🛡️"
                    "ru" -> "Защищено (VPN активен) 🛡️"
                    else -> "Secured (VPN Active) 🛡️"
                }
            } else {
                when(lang) {
                    "uz" -> "Himoyasiz (VPN O'chiq) ❌"
                    "ru" -> "Не защищено (VPN выключен) ❌"
                    else -> "Unsecured (VPN Off) ❌"
                }
            }
            
            val tetherText = if (stats.connectionMode == "USB_TETHERING") {
                when(lang) {
                    "uz" -> "Vaholanki, siz hozir USB orqali internet tarqatyapsiz (Modem). 🔌"
                    "ru" -> "Кстати, вы сейчас раздаете интернет через USB (модем). 🔌"
                    else -> "Note regarding connectivity: You are sharing internet via USB (Tethering). 🔌"
                }
            } else ""
            
            when(lang) {
                "uz" -> "🌐 Tarmoq: $vpnText. $tetherText"
                "ru" -> "🌐 Сеть: $vpnText. $tetherText"
                else -> "🌐 Network: $vpnText. $tetherText"
            }
        } else ""

        // 4. Security Summary
        val securityPart = if (criticalCount > 0) {
            when(lang) {
                "uz" -> "Men ${threats.take(3).joinToString { it.type }} kabi $criticalCount ta xavfni aniqladim." 
                "ru" -> "Я обнаружил $criticalCount угроз, включая ${threats.take(3).joinToString { it.type }}."
                else -> "I have detected $criticalCount threats including ${threats.take(3).joinToString { it.type }}."
            }
        } else {
            when(lang) {
                "uz" -> "Tizim toza. Virus yoki xavfli ilovalar yo'q."
                "ru" -> "Система чиста. Вирусов или опасных приложений не обнаружено."
                else -> "System is clean. No malware or dangerous apps found."
            }
        }

        return "$sentiment\n\n$batteryPart\n$vpnPart\n$securityPart"
    }

    private fun handleEnglishMasterResponse(q: String, criticalCount: Int, threats: List<ThreatEvent>): String {
        return when {
            q.contains(Regex("hi|hello|hey|greetings|start")) -> 
                "Hello! I am Cyber Brother — your dedicated AI guardian. Use me to check your system status or ask about digital security."
            
            q.contains(Regex("status|state|how is it|condition|happen|everything ok|news|update")) -> {
                generateSystemNarrative("en", criticalCount, threats, lastDeviceStats)
            }

            q.contains(Regex("thanks|thank|ok|okay|cool|good|nice|understood")) -> 
                "You're welcome! Your safety is my mission. Let me know if you need anything else."

            q.contains(Regex("what is this|who are you|what do you do")) -> 
                "I am an intelligent protection system that shields your phone from malware, phishing, and spyware. 'Guardian Core' protection is currently active."
            
            q.contains("vpn") -> "🛡️ About VPN: I create a secure, encrypted tunnel for your internet traffic. This blocks ads and prevents hackers from intercepting your data."
            
            q.contains(Regex("file|apk|scan|virus|malware")) -> {
                val lastApk = threats.find { it.type == "APK_AUDIT" }
                if (lastApk != null) "📁 Last Audit: ${lastApk.details}. I am constantly monitoring for new installations."
                else "📁 I use neural networks to scan every new file and application for hidden threats."
            }
            
            else -> {
                "I'm afraid I didn't quite catch that. You can ask me about 'System Status', 'VPN', or general 'Security'."
            }
        }
    }

    
    private fun buildExtendedContext(threats: List<ThreatEvent>): String {
        return threats.joinToString(". ") { 
            "Type ${it.type} with ${it.severity} severity detected in ${it.source}. Details: ${it.details}"
        }
    }

    private fun buildThreatReport(threats: List<ThreatEvent>, lang: String): String {
        val critical = threats.filter { it.severity in listOf("CRITICAL", "HIGH") }
        
        return buildString {
            when(lang) {
                "uz" -> {
                    appendLine("🔍 Xavfsizlik bo'yicha qisqacha hisobot:")
                    if (critical.isNotEmpty()) {
                        appendLine("🛑 Jiddiy xavflar aniqlangan (${critical.size} ta).")
                        critical.take(3).forEach { appendLine("- ${it.type}: ${it.details}") }
                    } else appendLine("✅ Hech qanday jiddiy xavf yo'q.")
                }
                "ru" -> {
                    appendLine("🔍 Краткий отчет о безопасности:")
                    if (critical.isNotEmpty()) {
                        appendLine("🛑 Обнаружены серьезные угрозы (${critical.size}).")
                        critical.take(3).forEach { appendLine("- ${it.type}: ${it.details}") }
                    } else appendLine("✅ Никаких серьезных угроз нет.")
                }
                else -> {
                    appendLine("🔍 Security Summary Report:")
                    if (critical.isNotEmpty()) {
                        appendLine("🛑 Critical threats detected (${critical.size}).")
                        critical.take(3).forEach { appendLine("- ${it.type}: ${it.details}") }
                    } else appendLine("✅ No serious threats found.")
                }
            }
        }
    }

    /**
     * Vishing (Voice Phishing) Analysis
     */
    fun analyzeVishing(text: String): VishingAnalysisResult {
        val score = com.eps.android.analysis.VishingPatternMatcher.calculateVishingScore(text)
        val isDangerous = score > 0.4
        
        val lang = context.resources.configuration.locales[0].language
        
        val warningMessage = when(lang) {
            "uz" -> "🛑 DIQQAT: XAVF! Suhbatda firibgarlik belgilari aniqlandi. Hech qachon SMS kod yoki shaxsiy ma'lumotlarni bermang!"
            "ru" -> "🛑 ВНИМАНИЕ: ОПАСНОСТЬ! Обнаружены признаки мошенничества. Никогда не передавайте СМС-коды или личные данные!"
            else -> "🛑 WARNING: DANGER! Phishing indicators detected in conversation. Never share SMS codes or personal data!"
        }
        
        return VishingAnalysisResult(isDangerous, score, warningMessage)
    }

    data class VishingAnalysisResult(
        val isDangerous: Boolean,
        val score: Double,
        val message: String
    )

    // ==================== PRO FUNCTIONS ====================
    
    /**
     * To'liq qurilma tahlili - foydalanuvchiga tushunarli tilda
     */
    private fun performFullDeviceAnalysis(lang: String): String {
        val report = deviceAnalyzer.performFullAnalysis()
        cachedFullReport = report
        
        return when (lang) {
            "uz" -> buildString {
                appendLine("📱 TO'LIQ QURILMA TAHLILI")
                appendLine("═══════════════════════════")
                appendLine()
                appendLine("🔋 BATAREYA: ${report.batteryLevel}% ${if (report.isCharging) "⚡ Quvvatlanmoqda" else ""}")
                appendLine("   Harorat: ${report.batteryTemperature}°C | Salomatligi: ${report.batteryHealth}")
                appendLine()
                appendLine("💾 XOTIRA: ${"%.1f".format(report.freeStorageGb)} GB bo'sh / ${"%.1f".format(report.totalStorageGb)} GB jami")
                appendLine("   Ishlatilgan: ${report.usedStoragePercent}%")
                appendLine()
                appendLine("🧠 RAM: ${report.availableRamMb} MB bo'sh / ${report.totalRamMb} MB jami")
                appendLine("   Ishlatilgan: ${report.usedRamPercent}%")
                appendLine()
                appendLine("🌐 TARMOQ: ${report.networkType}")
                appendLine("   VPN: ${if (report.isVpnActive) "✅ Faol" else "❌ O'chiq"}")
                appendLine("   USB Tethering: ${if (report.isUsbTethering) "🔌 Faol" else "Yo'q"}")
                appendLine()
                appendLine("📲 ILOVALAR: ${report.runningAppsCount} ta ishlamoqda")
                appendLine("   Yangi: ${report.recentlyInstalledApps.size} ta | Shubhali: ${report.suspiciousApps.size} ta")
                appendLine()
                appendLine("🔒 XAVFSIZLIK:")
                appendLine("   Ekran qulfi: ${if (report.isScreenLockSet) "✅" else "⚠️ YO'Q"}")
                appendLine("   Dasturchi rejimi: ${if (report.isDevModeEnabled) "⚠️ YOQILGAN" else "✅"}")
                appendLine("   ADB: ${if (report.isAdbEnabled) "⚠️ FAOL" else "✅"}")
                appendLine("   Root: ${if (report.isRooted) "⚠️ HA" else "✅ YO'Q"}")
                appendLine()
                appendLine("📋 ${report.deviceModel} | Android ${report.androidVersion}")
                appendLine("⏱️ Yoniq: ${report.uptime}")
            }
            "ru" -> buildString {
                appendLine("📱 ПОЛНЫЙ АНАЛИЗ УСТРОЙСТВА")
                appendLine("═══════════════════════════")
                appendLine()
                appendLine("🔋 БАТАРЕЯ: ${report.batteryLevel}% ${if (report.isCharging) "⚡ Заряжается" else ""}")
                appendLine("   Температура: ${report.batteryTemperature}°C")
                appendLine()
                appendLine("💾 ПАМЯТЬ: ${"%.1f".format(report.freeStorageGb)} ГБ свободно")
                appendLine("🧠 ОЗУ: ${report.availableRamMb} МБ свободно (${report.usedRamPercent}% занято)")
                appendLine()
                appendLine("🌐 СЕТЬ: ${report.networkType}")
                appendLine("   VPN: ${if (report.isVpnActive) "✅" else "❌"}")
                appendLine()
                appendLine("📲 ПРИЛОЖЕНИЯ: ${report.runningAppsCount} работает")
                appendLine("   Подозрительных: ${report.suspiciousApps.size}")
                appendLine()
                appendLine("🔒 БЕЗОПАСНОСТЬ:")
                appendLine("   Блокировка: ${if (report.isScreenLockSet) "✅" else "⚠️"}")
                appendLine("   Режим разработчика: ${if (report.isDevModeEnabled) "⚠️" else "✅"}")
            }
            else -> buildString {
                appendLine("📱 FULL DEVICE ANALYSIS")
                appendLine("═══════════════════════════")
                appendLine()
                appendLine("🔋 BATTERY: ${report.batteryLevel}% ${if (report.isCharging) "⚡" else ""}")
                appendLine("💾 STORAGE: ${"%.1f".format(report.freeStorageGb)} GB free")
                appendLine("🧠 RAM: ${report.usedRamPercent}% used")
                appendLine("🌐 NETWORK: ${report.networkType} | VPN: ${if (report.isVpnActive) "ON" else "OFF"}")
                appendLine("📲 APPS: ${report.runningAppsCount} running | ${report.suspiciousApps.size} suspicious")
                appendLine("🔒 SECURITY ISSUES: ${listOf(report.isDevModeEnabled, report.isAdbEnabled, !report.isScreenLockSet, report.isRooted).count { it }}")
            }
        }
    }

    /**
     * Background jarayonlarni tushuntirish
     */
    private fun explainBackgroundProcesses(lang: String): String {
        val report = cachedFullReport ?: deviceAnalyzer.performFullAnalysis()
        val processes = report.backgroundProcesses.take(8)
        
        return when (lang) {
            "uz" -> buildString {
                appendLine("⚙️ ORQA FONDA ISHLAYDIGAN JARAYONLAR")
                appendLine("═══════════════════════════════════")
                appendLine()
                appendLine("Hozirda ${report.runningAppsCount} ta jarayon ishlamoqda:")
                appendLine()
                processes.forEach { process ->
                    appendLine("• ${process.name} (${process.importance})")
                    appendLine("  └─ ${process.explanation}")
                }
                appendLine()
                appendLine("💡 Bu jarayonlar telefoningiz to'g'ri ishlashi uchun zarur.")
                appendLine("   Keraksizlarini 'Sozlamalar > Ilovalar' dan to'xtatishingiz mumkin.")
            }
            "ru" -> buildString {
                appendLine("⚙️ ФОНОВЫЕ ПРОЦЕССЫ")
                appendLine("═══════════════════════════════════")
                appendLine()
                appendLine("Сейчас работает ${report.runningAppsCount} процессов:")
                appendLine()
                processes.forEach { process ->
                    appendLine("• ${process.name} (${process.importance})")
                }
            }
            else -> buildString {
                appendLine("⚙️ BACKGROUND PROCESSES")
                appendLine("═══════════════════════════════════")
                appendLine()
                appendLine("${report.runningAppsCount} processes running:")
                appendLine()
                processes.forEach { process ->
                    appendLine("• ${process.name}: ${process.explanation}")
                }
            }
        }
    }

    /**
     * Shubhali ilovalarni tushuntirish
     */
    private fun explainSuspiciousApps(lang: String): String {
        val report = cachedFullReport ?: deviceAnalyzer.performFullAnalysis()
        val suspicious = report.suspiciousApps
        
        return when (lang) {
            "uz" -> buildString {
                if (suspicious.isEmpty()) {
                    appendLine("✅ SHUBHALI ILOVA TOPILMADI")
                    appendLine()
                    appendLine("Telefoningizda hozircha xavfli ko'rinadigan ilova yo'q.")
                    appendLine("Men barcha ilovalarning ruxsatlarini tekshirdim.")
                } else {
                    appendLine("⚠️ SHUBHALI ILOVALAR ANIQLANDI")
                    appendLine("═══════════════════════════════════")
                    appendLine()
                    appendLine("Quyidagi ilovalar ko'p shaxsiy ruxsatlarga ega:")
                    appendLine()
                    suspicious.forEach { app ->
                        appendLine("🔴 ${app.name}")
                        appendLine("   Paket: ${app.packageName}")
                        appendLine("   Xavf darajasi: ${app.riskLevel}")
                        appendLine()
                    }
                    appendLine("💡 Bu ilovalarni o'chirishni yoki ruxsatlarini cheklashni tavsiya qilaman.")
                }
            }
            "ru" -> buildString {
                if (suspicious.isEmpty()) {
                    appendLine("✅ Подозрительных приложений не найдено")
                } else {
                    appendLine("⚠️ ОБНАРУЖЕНЫ ПОДОЗРИТЕЛЬНЫЕ ПРИЛОЖЕНИЯ: ${suspicious.size}")
                    suspicious.forEach { 
                        appendLine("• ${it.name} - ${it.riskLevel}")
                    }
                }
            }
            else -> buildString {
                if (suspicious.isEmpty()) {
                    appendLine("✅ No suspicious apps found")
                } else {
                    appendLine("⚠️ SUSPICIOUS APPS DETECTED: ${suspicious.size}")
                    suspicious.forEach { 
                        appendLine("• ${it.name} (${it.packageName}) - Risk: ${it.riskLevel}")
                    }
                }
            }
        }
    }

    /**
     * Resurs ishlatilishini tushuntirish
     */
    private fun explainResourceUsage(lang: String): String {
        val report = cachedFullReport ?: deviceAnalyzer.performFullAnalysis()
        
        return when (lang) {
            "uz" -> buildString {
                appendLine("🧠 TELEFON RESURSLARI")
                appendLine("═══════════════════════════════════")
                appendLine()
                appendLine("📊 CPU (Protsessor):")
                appendLine("   Hozirgi yuklanish: ~${report.cpuUsagePercent.toInt()}%")
                appendLine()
                appendLine("🧠 RAM (Operativ xotira):")
                appendLine("   Jami: ${report.totalRamMb} MB")
                appendLine("   Bo'sh: ${report.availableRamMb} MB")
                appendLine("   Ishlatilgan: ${report.usedRamPercent}%")
                appendLine()
                if (report.usedRamPercent > 80) {
                    appendLine("⚠️ RAM ko'p ishlatilmoqda! Bo'sh joy kam.")
                    appendLine("   Orqa fondagi ilovalarni yopishni tavsiya qilaman.")
                } else {
                    appendLine("✅ Resurslar yetarli. Telefon silliq ishlashi kerak.")
                }
            }
            "ru" -> buildString {
                appendLine("🧠 РЕСУРСЫ УСТРОЙСТВА")
                appendLine()
                appendLine("ОЗУ: ${report.availableRamMb} МБ свободно (${report.usedRamPercent}% занято)")
                appendLine("CPU: ~${report.cpuUsagePercent.toInt()}%")
            }
            else -> buildString {
                appendLine("🧠 DEVICE RESOURCES")
                appendLine()
                appendLine("RAM: ${report.availableRamMb} MB free (${report.usedRamPercent}% used)")
                appendLine("CPU: ~${report.cpuUsagePercent.toInt()}%")
            }
        }
    }

    /**
     * Tarmoq holatini tushuntirish
     */
    private fun explainNetworkStatus(lang: String): String {
        val report = cachedFullReport ?: deviceAnalyzer.performFullAnalysis()
        
        return when (lang) {
            "uz" -> buildString {
                appendLine("🌐 TARMOQ HOLATI")
                appendLine("═══════════════════════════════════")
                appendLine()
                appendLine("📶 Ulanish turi: ${report.networkType}")
                appendLine()
                appendLine("🛡️ VPN himoyasi: ${if (report.isVpnActive) "FAOL ✅" else "O'CHIQ ❌"}")
                if (!report.isVpnActive) {
                    appendLine("   ⚠️ VPN yoqilmagan. Internet trafigingiz himoyasiz.")
                }
                appendLine()
                if (report.isUsbTethering) {
                    appendLine("🔌 USB TETHERING FAOL!")
                    appendLine("   Siz boshqa qurilmaga internet tarqatyapsiz.")
                    appendLine("   Bu quvvat va trafikni ko'p ishlatadi.")
                }
                if (report.isHotspotActive) {
                    appendLine("📡 HOTSPOT FAOL!")
                    appendLine("   Wi-Fi orqali internet tarqatilmoqda.")
                }
                appendLine()
                appendLine("📊 Trafik statistikasi:")
                appendLine("   Mobil: ${report.mobileDataUsedMb} MB")
                appendLine("   Wi-Fi: ${report.wifiDataUsedMb} MB")
            }
            "ru" -> buildString {
                appendLine("🌐 СОСТОЯНИЕ СЕТИ")
                appendLine()
                appendLine("Тип: ${report.networkType}")
                appendLine("VPN: ${if (report.isVpnActive) "✅ Активен" else "❌ Выключен"}")
                appendLine("USB-модем: ${if (report.isUsbTethering) "Активен" else "Нет"}")
            }
            else -> buildString {
                appendLine("🌐 NETWORK STATUS")
                appendLine()
                appendLine("Type: ${report.networkType}")
                appendLine("VPN: ${if (report.isVpnActive) "Active" else "Off"}")
                appendLine("USB Tethering: ${if (report.isUsbTethering) "Active" else "No"}")
                appendLine("Mobile Data: ${report.mobileDataUsedMb} MB")
            }
        }
    }

    /**
     * Batareya holatini tushuntirish
     */
    private fun explainBatteryStatus(lang: String): String {
        val report = cachedFullReport ?: deviceAnalyzer.performFullAnalysis()
        
        return when (lang) {
            "uz" -> buildString {
                appendLine("🔋 BATAREYA HOLATI")
                appendLine("═══════════════════════════════════")
                appendLine()
                appendLine("⚡ Quvvat darajasi: ${report.batteryLevel}%")
                appendLine("🔌 Holat: ${if (report.isCharging) "Quvvatlanmoqda ⚡" else "Quvvatlanmayapti"}")
                appendLine("🌡️ Harorat: ${report.batteryTemperature}°C")
                appendLine("❤️ Salomatligi: ${report.batteryHealth}")
                appendLine()
                when {
                    report.batteryLevel < 20 -> {
                        appendLine("⚠️ Quvvat kam! Tez orada zaryadga qo'ying.")
                    }
                    report.batteryTemperature > 40 -> {
                        appendLine("🔥 Batareya qizib ketgan! Telefonni dam oldiring.")
                    }
                    else -> {
                        appendLine("✅ Batareya holati yaxshi.")
                    }
                }
            }
            "ru" -> buildString {
                appendLine("🔋 СОСТОЯНИЕ БАТАРЕИ")
                appendLine()
                appendLine("Заряд: ${report.batteryLevel}%")
                appendLine("Статус: ${if (report.isCharging) "Заряжается ⚡" else "Не заряжается"}")
                appendLine("Температура: ${report.batteryTemperature}°C")
            }
            else -> buildString {
                appendLine("🔋 BATTERY STATUS")
                appendLine()
                appendLine("Level: ${report.batteryLevel}%")
                appendLine("Status: ${if (report.isCharging) "Charging ⚡" else "Not charging"}")
                appendLine("Temperature: ${report.batteryTemperature}°C")
                appendLine("Health: ${report.batteryHealth}")
            }
        }
    }

    /**
     * Xotira holatini tushuntirish
     */
    private fun explainStorageStatus(lang: String): String {
        val report = cachedFullReport ?: deviceAnalyzer.performFullAnalysis()
        
        return when (lang) {
            "uz" -> buildString {
                appendLine("💾 XOTIRA HOLATI")
                appendLine("═══════════════════════════════════")
                appendLine()
                appendLine("📦 Jami xotira: ${"%.1f".format(report.totalStorageGb)} GB")
                appendLine("✅ Bo'sh joy: ${"%.1f".format(report.freeStorageGb)} GB")
                appendLine("📊 Ishlatilgan: ${report.usedStoragePercent}%")
                appendLine()
                
                // Progress bar
                val filled = (report.usedStoragePercent / 10)
                val empty = 10 - filled
                append("   [")
                repeat(filled) { append("█") }
                repeat(empty) { append("░") }
                appendLine("]")
                appendLine()
                
                when {
                    report.usedStoragePercent > 90 -> {
                        appendLine("🛑 XOTIRA KRITIK DARAJADA TO'LA!")
                        appendLine("   Keraksiz fayllarni o'chirishni tavsiya qilaman.")
                    }
                    report.usedStoragePercent > 75 -> {
                        appendLine("⚠️ Xotira ko'p ishlatilgan.")
                        appendLine("   Bo'sh joyni tozalash yaxshi bo'lardi.")
                    }
                    else -> {
                        appendLine("✅ Xotira etarli. Hammasi yaxshi!")
                    }
                }
            }
            "ru" -> buildString {
                appendLine("💾 СОСТОЯНИЕ ПАМЯТИ")
                appendLine()
                appendLine("Всего: ${"%.1f".format(report.totalStorageGb)} ГБ")
                appendLine("Свободно: ${"%.1f".format(report.freeStorageGb)} ГБ")
                appendLine("Занято: ${report.usedStoragePercent}%")
            }
            else -> buildString {
                appendLine("💾 STORAGE STATUS")
                appendLine()
                appendLine("Total: ${"%.1f".format(report.totalStorageGb)} GB")
                appendLine("Free: ${"%.1f".format(report.freeStorageGb)} GB")
                appendLine("Used: ${report.usedStoragePercent}%")
            }
        }
    }
}
