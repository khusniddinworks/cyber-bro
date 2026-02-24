package com.eps.android.core

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.telephony.TelephonyManager
import androidx.core.app.NotificationCompat
import timber.log.Timber
import kotlinx.coroutines.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class EpsNotificationListener : NotificationListenerService() {

    @Inject
    lateinit var urlScanRepository: com.eps.android.analysis.network.UrlScanRepository

    @Inject
    lateinit var eventLogger: com.eps.android.data.EventLogger

    @Inject
    lateinit var threatNotifier: ThreatNotifier

    private val serviceScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    // OTP detection: Contextual keywords + 4-8 digit number
    // Improved: Detects keyword and number in any order
    private val keywordPattern = Regex("(?i)(kod|tasdiqlash|code|verify|password|parol|код|подтверждение|пароль)")
    private val numberPattern = Regex("\\b\\d{4,8}\\b")
    
    private val vibrationReceiver = object : android.content.BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == ThreatNotifier.ACTION_STOP_VISH) {
                threatNotifier.cancelVishingVibration()
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        val filter = android.content.IntentFilter(ThreatNotifier.ACTION_STOP_VISH)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(vibrationReceiver, filter, Context.RECEIVER_NOT_EXPORTED)
        } else {
            registerReceiver(vibrationReceiver, filter)
        }
    }

    override fun onDestroy() {
        threatNotifier.cancelVishingVibration()
        unregisterReceiver(vibrationReceiver)
        super.onDestroy()
    }
    
    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        val sbnPackage = sbn?.packageName ?: return
        
        // Skip our own notifications
        if (sbnPackage == "com.eps.android") return
        
        val extras = sbn.notification.extras
        val title = extras.getString("android.title") ?: ""
        val text = extras.getCharSequence("android.text")?.toString() ?: ""
        val fullContent = "$title $text".lowercase()
        
        // ===== CORE PROTECTION: OTP + CALL DETECTION =====
        val telephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        val isOnCall = telephonyManager.callState != TelephonyManager.CALL_STATE_IDLE
        
        // Check if on call AND content has both a keyword and an OTP-like number
        val hasKeyword = keywordPattern.containsMatchIn(fullContent)
        val hasNumber = numberPattern.containsMatchIn(fullContent)
        
        if (isOnCall && hasKeyword && hasNumber) {
            Timber.w("🚨 VISHING: Confirmed OTP during call from [$sbnPackage]. Content: $fullContent")
            triggerVishingPush(sbnPackage)
            return
        }
        
        if (isOnCall && (hasKeyword || hasNumber)) {
            Timber.d("Skip vishing check: hasKeyword=$hasKeyword, hasNumber=$hasNumber, isOnCall=true")
        }

        // ===== NEW: LINK DETECTION IN NOTIFICATIONS =====
        extractAndScanLinks(fullContent, sbnPackage)
    }

    private fun extractAndScanLinks(content: String, sourceApp: String) {
        val urlPattern = android.util.Patterns.WEB_URL
        val matcher = urlPattern.matcher(content)
        
        while (matcher.find()) {
            val url = matcher.group()
            serviceScope.launch {
                Timber.d("Scanning URL from notification ($sourceApp): $url")
                val result = urlScanRepository.checkUrl(url)
                if (!result.isSecure && !result.isUnknown) {
                    val detail = if (result.isHeuristicResult) "Xabarnomada shubhali havola: $url"
                                 else "Xabarnomada zararli havola: $url (UrlScan tahlili)"
                    
                    eventLogger.logThreat(
                        type = "URL_NOTIFICATION_GUARD",
                        severity = "HIGH",
                        source = sourceApp,
                        details = detail
                    )
                }
            }
        }
    }

    private fun triggerVishingPush(sourceApp: String) {
        val appName = when {
            sourceApp.contains("telegram", true) -> "Telegram"
            sourceApp.contains("messaging", true) || sourceApp.contains("sms", true) -> "SMS"
            sourceApp.contains("click", true) -> "Click"
            sourceApp.contains("payme", true) -> "Payme"
            sourceApp.contains("google", true) -> "Google"
            sourceApp.contains("whatsapp", true) -> "WhatsApp"
            sourceApp.contains("instagram", true) -> "Instagram"
            sourceApp.contains("humo", true) -> "Humo"
            sourceApp.contains("uzcard", true) -> "Uzcard"
            sourceApp.contains("kapital", true) -> "KapitalBank"
            else -> sourceApp.substringAfterLast(".")
        }
        
        threatNotifier.showVishingAlert(appName, isOtp = true)
    }
}
