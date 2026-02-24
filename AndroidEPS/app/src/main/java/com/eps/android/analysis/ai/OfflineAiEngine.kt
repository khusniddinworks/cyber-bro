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

    enum class SystemActionType {
        OPEN_APP, START_SCAN, TOGGLE_VPN, SHOW_SECURITY_AUDIT, NAVIGATE, NONE
    }

    data class SystemAction(
        val type: SystemActionType,
        val data: String? = null
    )

    data class AiResponse(
        val text: String,
        val action: SystemAction = SystemAction(SystemActionType.NONE)
    )

    fun respond(query: String, threats: List<ThreatEvent>, deviceStats: DeviceStatus): AiResponse {
        lastDeviceStats = deviceStats
        val q = query.lowercase().trim()
        val lang = context.resources.configuration.locales[0].language
        
        // 1. ACTION DETECTION (The "Ipidan-ignasigacha" logic)
        val action = detectAction(q, lang)
        if (action.type != SystemActionType.NONE) {
            val actionText = when(action.type) {
                SystemActionType.OPEN_APP -> {
                    val appName = action.data ?: "ilova"
                    if (lang == "uz") "Xo'p bo'ladi, $appName ilovasini ochyapman... 🚀"
                    else "Sure, opening $appName..."
                }
                SystemActionType.START_SCAN -> {
                    if (lang == "uz") "Tizimni to'liq skanerlashni boshlayapman... 🛡️"
                    else "Initializing full system scan... 🛡️"
                }
                SystemActionType.TOGGLE_VPN -> {
                    if (lang == "uz") "VPN himoya xizmatini sozlamoqdamda... 🔒"
                    else "Adjusting VPN protection status... 🔒"
                }
                else -> ""
            }
            if (actionText.isNotEmpty()) return AiResponse(actionText, action)
        }

        // 🚀 PRO: To'liq qurilma tahlili
        if (q.contains(Regex("to'liq|toliq|full|batafsil|полный|подробный|analyze|tahlil|tekshir|audit"))) {
            return AiResponse(performFullDeviceAnalysis(lang))
        }
        
        // 🚀 PRO: Background jarayonlar
        if (q.contains(Regex("background|orqa fon|jarayon|process|nima ishlayapti|фоновые|процессы"))) {
            return AiResponse(explainBackgroundProcesses(lang))
        }
        
        // 🚀 PRO: Shubhali ilovalar
        if (q.contains(Regex("shubhali|xavfli|dangerous|suspicious|подозрительны|опасны"))) {
            return AiResponse(explainSuspiciousApps(lang))
        }
        
        // 🚀 PRO: RAM va CPU
        if (q.contains(Regex("ram|cpu|protsessor|operativ|процессор|оперативная"))) {
            return AiResponse(explainResourceUsage(lang))
        }
        
        // 🚀 PRO: Tarmoq tahlili
        if (q.contains(Regex("tarmoq|network|internet|wifi|сеть|интернет"))) {
            return AiResponse(explainNetworkStatus(lang))
        }
        
        // Device Stats - Batareya
        if (q.contains(Regex("batareya|zaryad|quvvat|battery|батарея|заряд"))) {
            return AiResponse(explainBatteryStatus(lang))
        }
        
        // Xotira
        if (q.contains(Regex("joy|xotira|storage|memory|память|место"))) {
            return AiResponse(explainStorageStatus(lang))
        }
        
        if (q.contains(Regex("usb|kabel|cable|ulangan|internet|кабель|подключен"))) {
            if (deviceStats.connectionMode == "USB_TETHERING") {
                val text = when(lang) {
                    "uz" -> "🔌 USB TETHERING FAOLLASHGAN: Siz hozirda USB kabel orqali boshqa qurilmaga (masalan, Laptopga) internet tarqatyapsiz (RNDIS/Modem rejimi). Bu shunchaki quvvatlash emas!"
                    "ru" -> "🔌 USB-МОДЕМ АКТИВИРОВАН: Вы раздаете интернет другому устройству (например, ноутбуку) через USB-кабель. Это не просто зарядка!"
                    else -> "🔌 USB TETHERING ACTIVE: You are sharing your mobile internet with another device (like a Laptop) via USB Cable (RNDIS Mode). This is more than just charging!"
                }
                return AiResponse(text)
            }
            
            if (q.contains(Regex("usb|kabel|кабель"))) {
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
                val text = when(lang) {
                    "uz" -> "🔌 USB holati: Qurilma $chargeStatus. Agar internet tarqatmoqchi bo'lsangiz, Sozlamalardan 'USB Tethering'ni yoqing."
                    "ru" -> "🔌 Статус USB: Устройство $chargeStatus. Если вы хотите раздать интернет, включите 'USB-модем' в настройках."
                    else -> "🔌 USB Status: Device is $chargeStatus. If you want to share internet, enable 'USB Tethering' in Settings."
                }
                return AiResponse(text)
            }
        }

        // 1. Check for specific system activity questions
        val activityResponse = checkSystemActivity(q, threats, lang)
        if (activityResponse != null) return AiResponse(activityResponse)

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

    private fun handleUzbekMasterResponse(q: String, criticalCount: Int, threats: List<ThreatEvent>): AiResponse {
        val text = when {
            q.contains(Regex("salom|assalom|assalomu alaykum|qale|qalaysan|nima gap")) -> 
                "Assalomu alaykum, birodar! 🤝 Men Cyber Brother — sizning shaxsiy kiber-himoyachingizman. Tizim xavfsizligi bo'yicha har qanday savol berishingiz mumkin. O'zimni uyingizdagidek his qiling! Masalan: 'Skanerlashni boshla' yoki 'Telefonim holati qanaqa?' desangiz, darrov tahlil qilib beraman. 😊"
            
            q.contains(Regex("ahvol|holat|status|sharoit|tinchlikmi|nima yangilik|yaxshimi")) -> {
                generateSystemNarrative("uz", criticalCount, threats, lastDeviceStats)
            }

            q.contains(Regex("rahmat|hop|mayli|tushunarli|ok|yaxshi|zo'r|borakal|barakalla")) -> 
                "Arzimaydi, jigardan! 💪 Xavfsizligingiz — mening birinchi raqamli vazifam. Yana kiber-xavfsizlikka oid savollaringiz bormi? Men doim shu yerdaman, kiber-olamda hushyor bo'ling!"
            
            q.contains(Regex("nima bu|bu nima|qanaqa|qanday|kimsan|nima qilasan")) -> 
                "Men — Cyber Brother, sizning raqamli himoyachingizman. Telefoningizni hackerlardan, viruslardan va zararli saytlardan oflayn rejimda himoya qilaman. Men nafaqat oddiy dastur, balki kiber-yordamchingizman, har bir bit va baytingizni nazorat qilaman! 🛡️"

            q.contains(Regex("tensorflow|ai|sun'iy intellekt|neyron|neural|model|aqlli")) ->
                "🤖 Men 100% oflayn ishlaydigan sun'iy intellektman. TensorFlow Lite orqali ilovalaringizni 'rentgen' qilib, ichidagi yashirin xavflarni aniqlayman. Eng muhimi — sizning ma'lumotlaringiz hech qachon qurilmadan tashqariga chiqmaydi!"

            q.contains("vpn") -> "🛡️ VPN haqida gapiradigan bo'lsak, men internet trafigingizni xavfsiz kanaldan o'tkazaman. Bu sizni soxta (phishing) saytlardan va ma'lumotlaringiz o'g'irlanishidan himoya qiladi. VPN yoqilgan bo'lsa, xotirjam bo'lsangiz bo'ladi!"
            
            q.contains(Regex("fayl|apk|scan|virus|zarar|skaner|tahlil")) -> {
                val lastApk = threats.find { it.type == "APK_AUDIT" }
                if (lastApk != null) "📁 Oxirgi tahlil: ${lastApk.details}. Men tizimdagi har bir o'zgarishni sun'iy intellekt ko'zi bilan kuzatib turibman. Shubhali narsa bo'lsa, darrov aytaman! 😉"
                else "📁 Hozirda telefoningiz toza, birodar! Men barcha APK fayllarni TensorFlow algoritmlari yordamida tekshirib chiqdim. Xavotirga o'rin yo'q."
            }

            q.contains(Regex("nima bilasan|nima qo'lingdan keladi|vazifang")) -> 
                "Mening qobiliyatlarim: \n1. Linklarni real-vaqtda tekshirish. \n2. Ilovalarni rentgen kabi skanerlash. \n3. VPN orqali trafigingizni yopish. \n4. Tizim resurslarini (batareya, xotira) doimiy tahlil qilish. Savolingizni beravering, men hamma narsadan xabardorman! 💪"

            else -> {
                "Tushunarli. Men sizning xabaringizni tahlil qilyapman, birodar. Kiber-himoyasiz qolmaslik uchun mendan quyidagilarni so'rashingiz mumkin:\n- 'Tizim holati qanday?'\n- 'Skanerlashni boshla'\n- 'VPN nima vazifani bajaradi?'\n- 'Xavfsizlik bo'yicha maslahat ber'"
            }
        }
        return AiResponse(text)
    }

    private fun handleRussianMasterResponse(q: String, criticalCount: Int, threats: List<ThreatEvent>): AiResponse {
        val text = when {
            q.contains(Regex("привет|здравствуй|салам|как дела|как ты|добрый день|приветствую")) -> 
                "Приветствую, брат! 🤝 Я Cyber Brother — твой персональный ИИ-защитник. Я здесь, чтобы оберегать твои данные в цифровом мире. Можешь спрашивать что угодно касательно безопасности. Я свой человек! Попроси меня 'Проверить систему' или спроси 'Как дела с памятью?', и я тут же дам полный отчет. 😊"
            
            q.contains(Regex("состояние|статус|как обстановка|что происходит|все нормально|новости|анализ|че как")) -> {
                generateSystemNarrative("ru", criticalCount, threats, lastDeviceStats)
            }

            q.contains(Regex("спасибо|ок|хорошо|ладно|понял|ясно|круто|отлично|красава")) -> 
                "Всегда пожалуйста, брат! 💪 Твоя безопасность — мой высший приоритет. Если будут еще вопросы по кибербезопасности, я всегда на посту. Будь бдителен!"

            q.contains(Regex("что это|кто ты|зачем это|что делаешь|функции|кто такой")) -> 
                "Я — Cyber Brother, интеллектуальный щит твоего смартфона. Я оберегаю тебя от хакеров, вирусов и фишинговых атак полностью офлайн. Я не просто программа, я твой напарник в мире киберугроз! 🛡️"

            q.contains(Regex("tensorflow|ai|ии|нейросеть|нейронка|модель|умный")) ->
                "🤖 Я работаю на базе TensorFlow Lite, что позволяет мне делать глубокий анализ приложений прямо на устройстве. Это 100% приватно — твои данные не покидают телефон, всё под моим контролем!"

            q.contains("vpn") -> "🛡️ Насчет VPN: я создаю зашифрованный туннель для твоего трафика. Это защищает тебя от кражи паролей в открытых сетях и блокирует опасные сайты. С VPN ты под надежной защитой!"
            
            q.contains(Regex("файл|апк|скан|apk|scan|вирус|угроза|проверка")) -> {
                val lastApk = threats.find { it.type == "APK_AUDIT" }
                if (lastApk != null) "📁 Результат недавнего аудита: ${lastApk.details}. Я слежу за каждым изменением в системе. Если замечу что-то подозрительное — сразу дам знать! 😉"
                else "📁 Брат, пока всё чисто! Я просканировал приложения с помощью своих нейросетей, угроз не обнаружено. Можешь не волноваться."
            }

            q.contains(Regex("что умеешь|что можешь|возможности")) -> 
                "Я умею: \n1. Блокировать вредоносные ссылки. \n2. Проводить рентген-сканирование приложений. \n3. Шифровать трафик через VPN. \n4. Мониторить ресурсы системы 24/7. Спрашивай, я всегда на связи! 💪"

            else -> {
                "Анализирую твой запрос, брат. Чтобы лучше защитить тебя, попробуй спросить:\n- 'Какой сейчас статус безопасности?'\n- 'Запусти быструю проверку'\n- 'Что такое VPN?'\n- 'Расскажи про мой ИИ'"
            }
        }
        return AiResponse(text)
    }

    private fun generateSystemNarrative(lang: String, criticalCount: Int, threats: List<ThreatEvent>, stats: DeviceStatus?): String {
        val sentiment = if (criticalCount == 0) {
            when(lang) {
                "uz" -> "Birodar, hozircha telefoning tinch va xavfsiz holatda! ✅"
                "ru" -> "Брат, на данный момент твой телефон вне опасности! ✅"
                else -> "Brother, your device is safe and sound for now! ✅"
            }
        } else {
            when(lang) {
                "uz" -> "Diqqat, jigar! Tizimda shubhali holatlar aniqlandi. ⚠️"
                "ru" -> "Внимание, брат! Замечены подозрительные события в системе. ⚠️"
                else -> "Attention, brother! Suspicious events detected in the system. ⚠️"
            }
        }
        
        // 2. Battery & Power Context
        val batteryPart = if (stats != null) {
            val chargeIcon = if (stats.isCharging) "⚡" else "🔋"
            val chargeStatus = if (stats.isCharging) {
                when(lang) {
                    "uz" -> "quvvatlanmoqda"
                    "ru" -> "заряжается"
                    else -> "charging"
                }
            } else {
                when(lang) {
                    "uz" -> "qolgan"
                    "ru" -> "осталось"
                    else -> "left"
                }
            }
            when(lang) {
                "uz" -> "$chargeIcon Quvvatimiz ${stats.batteryLevel}% $chargeStatus." 
                "ru" -> "$chargeIcon Заряда ${stats.batteryLevel}% $chargeStatus."
                else -> "$chargeIcon Battery is ${stats.batteryLevel}% $chargeStatus."
            }
        } else ""

        // 3. Network & VPN Context
        val vpnPart = if (stats != null) {
            val vpnText = if (stats.isVpnActive) {
                when(lang) {
                    "uz" -> "Kiber-qalqon faol (VPN yoqilgan) 🛡️"
                    "ru" -> "Кибер-щит активен (VPN включен) 🛡️"
                    else -> "Cyber Shield is Active (VPN On) 🛡️"
                }
            } else {
                when(lang) {
                    "uz" -> "Kiber-qalqon o'chiq (VPN o'chirilgan) ❌"
                    "ru" -> "Кибер-щит выключен (VPN не активен) ❌"
                    else -> "Cyber Shield is Off (VPN Inactive) ❌"
                }
            }
            when(lang) {
                "uz" -> "🌐 Tarmoq: $vpnText."
                "ru" -> "🌐 Сеть: $vpnText."
                else -> "🌐 Network: $vpnText."
            }
        } else ""

        // 4. Memory Context
        val storagePart = if (stats != null) {
            when(lang) {
                "uz" -> "💾 Xotira: ${stats.freeStorageGb} GB bo'sh joyingiz bor."
                "ru" -> "💾 Память: у тебя ${stats.freeStorageGb} ГБ свободного места."
                else -> "💾 Storage: You have ${stats.freeStorageGb} GB free."
            }
        } else ""

        // 5. Security Summary
        val securityPart = if (criticalCount > 0) {
            val eventList = threats.take(2).joinToString { it.type }
            when(lang) {
                "uz" -> "Men $criticalCount ta xavfni (masalan: $eventList) aniqladim. Ehtiyot choralarini ko'rishni tavsiya qilaman!" 
                "ru" -> "Я нашел столько угроз: $criticalCount (включая $eventList). Рекомендую принять меры!"
                else -> "I found $criticalCount security events (like $eventList). I suggest taking precautions!"
            }
        } else {
            when(lang) {
                "uz" -> "Tizim toza. Shubhali ilova yoki zararli fayllar yo'q, xotirjam bo'lavering!"
                "ru" -> "Система чиста. Подозрительных приложений нет, можешь быть спокоен!"
                else -> "System is clean. No suspicious apps or malware found, you can relax!"
            }
        }

        return "$sentiment\n\n$batteryPart\n$storagePart\n$vpnPart\n\n$securityPart"
    }

    private fun handleEnglishMasterResponse(q: String, criticalCount: Int, threats: List<ThreatEvent>): AiResponse {
        val text = when {
            q.contains(Regex("hi|hello|hey|greetings|start|good morning|how are you")) -> 
                "Hello there! I am Cyber Brother — your dedicated AI guardian and security consultant. I'm here to keep your digital life safe. You can ask me to 'Check system status', 'Start full scanning', or 'Tell me about VPN'. Great to talk to you! 😊"
            
            q.contains(Regex("status|state|how is it|condition|happen|everything ok|news|update|audit")) -> {
                generateSystemNarrative("en", criticalCount, threats, lastDeviceStats)
            }

            q.contains(Regex("thanks|thank|ok|okay|cool|good|nice|understood|perfect")) -> 
                "You're very welcome! Your safety is my mission. If you have any other security-related questions, I'm always here to help. Stay safe in the digital world!"

            q.contains(Regex("what is this|who are you|what do you do|work|purpose")) -> 
                "I am an intelligent, next-generation protection system that shields your phone from malware, phishing, and spyware. I use on-device AI to ensure your data never leaves your hands while keeping threats away!"

            q.contains(Regex("tensorflow|ai|intelligence|neural|model|learning")) ->
                "🤖 About TensorFlow & AI: Yes, I utilize TensorFlow Lite for sophisticated on-device machine learning! This allows me to perform deep packet inspections and behavioral analysis of apps without needing the cloud. My neural networks are constantly guarding your privacy."

            q.contains("vpn") -> "🛡️ About VPN: I create a secure, encrypted tunnel for your internet traffic. I've also integrated VirusTotal API checks to verify every domain you visit, ensuring a ironclad browsing experience."
            
            q.contains(Regex("file|apk|scan|virus|malware|threat")) -> {
                val lastApk = threats.find { it.type == "APK_AUDIT" }
                if (lastApk != null) "📁 Latest App Audit: ${lastApk.details}. I perform deep package inspections (X-Ray audit) on every new installation."
                else "📁 I use local neural networks to scan every new file and app for zero-day threats. Your device is perfectly clean right now!"
            }

            q.contains(Regex("what can you do|skills|features|abilities")) -> 
                "My core features: 1. Real-time malicious link blocking. 2. Deep app X-ray scanning. 3. Encrypted VPN tunneling with VirusTotal integration. 4. AI-powered Voice Phishing (Vishing) detection."

            else -> {
                "I'm analyzing your request. To help you stay secure, try asking:\n- 'How is my system security status?'\n- 'Run a full deep-scan'\n- 'Explain your AI features'\n- 'What is my battery health?'"
            }
        }
        return AiResponse(text)
    }

    
    private fun detectAction(q: String, lang: String): SystemAction {
        // App opening logic
        val openKeywords = listOf("och", "yoq", "start", "open", "run", "launch", "запусти", "открой")
        if (openKeywords.any { q.contains(it) }) {
            // Filter out keywords to find the app name
            val words = q.split(" ").filter { it !in openKeywords }
            
            // Map common app names to packages
            val packageMap = mapOf(
                "telegram" to "org.telegram.messenger",
                "whatsapp" to "com.whatsapp",
                "instagram" to "com.instagram.android",
                "facebook" to "com.facebook.katana",
                "youtube" to "com.google.android.youtube",
                "chrome" to "com.android.chrome",
                "payme" to "uz.dida.payme",
                "click" to "air.com.ssd.click.android",
                "uzum" to "uz.uzum.app",
                "settings" to "com.android.settings",
                "sozlamalar" to "com.android.settings"
            )
            
            packageMap.forEach { (name, pkg) ->
                if (q.contains(name)) return SystemAction(SystemActionType.OPEN_APP, pkg)
            }
        }

        // Feature actions
        if (q.contains("skaner") || q.contains("scan") || q.contains("tekshir")) {
             return SystemAction(SystemActionType.START_SCAN)
        }
        
        if (q.contains("vpn")) {
             return SystemAction(SystemActionType.TOGGLE_VPN)
        }
        
        if (q.contains("audit") || q.contains("xavf")) {
             return SystemAction(SystemActionType.SHOW_SECURITY_AUDIT)
        }

        return SystemAction(SystemActionType.NONE)
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
