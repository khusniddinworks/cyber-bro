package com.eps.android.core

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import com.eps.android.data.EventLogger
import com.eps.android.ui.screens.VishingAlertActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class SmsReceiver : BroadcastReceiver() {

    @Inject
    lateinit var nlpEngine: com.eps.android.analysis.SmartPhishingNLP

    @Inject
    lateinit var eventLogger: EventLogger

    @Inject
    lateinit var urlScanRepository: com.eps.android.analysis.network.UrlScanRepository
 
    @Inject
    lateinit var threatNotifier: ThreatNotifier
 
    private val scope = CoroutineScope(Dispatchers.IO)

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Telephony.Sms.Intents.SMS_RECEIVED_ACTION) {
            val messages = Telephony.Sms.Intents.getMessagesFromIntent(intent)
            for (msg in messages) {
                val body = msg.messageBody
                val sender = msg.originatingAddress ?: "Unknown"
                
                // Get TelephonyManager
                val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as android.telephony.TelephonyManager
                val isOnCall = telephonyManager.callState != android.telephony.TelephonyManager.CALL_STATE_IDLE

                analyzeSms(context, body, sender, isOnCall)
                extractAndCheckUrls(body, sender)
            }
        }
    }

    private fun analyzeSms(context: Context, body: String, sender: String, isOnCall: Boolean) {
        val result = nlpEngine.analyzeText(body, isOnCall)
        val otpPattern = Regex("\\b\\d{4,8}\\b")
        val hasOtp = otpPattern.containsMatchIn(body)
        
        val lastKeywordTime = com.eps.android.analysis.VishingPatternMatcher.lastDetectionTimestamp
        val timeSinceKeyword = System.currentTimeMillis() - lastKeywordTime
        val isKeywordRecent = timeSinceKeyword < 120000 // 2 minutes

        if (isOnCall && hasOtp && isKeywordRecent) {
            Timber.w("🔥 CRITICAL COMBO: Call + Recent Keywords + SMS Code! Triggering Alarm.")
            threatNotifier.showVishingAlert(sender, isOtp = true)
        } else if (result.isCritical && !isOnCall) {
            // Standard phishing SMS push
            logThreat(sender, "Zararli SMS: Ma'lumotlarni o'g'rilashga urinish aniqlandi.")
            threatNotifier.showThreatAlert("SHUBHALI SMS", "Yuboruvchi: $sender\n$body", null, null)
        } else if (result.score > 35) {
            logThreat(sender, "Shubhali SMS (Score: ${result.score})")
        }
    }

    private fun logThreat(sender: String, details: String) {
        scope.launch {
            eventLogger.logThreat("SMS_GUARD", "HIGH", sender, details)
        }
    }

    private fun extractAndCheckUrls(text: String, sender: String) {
        val urlPattern = android.util.Patterns.WEB_URL
        val matcher = urlPattern.matcher(text)
        
        while (matcher.find()) {
            val url = matcher.group()
            scope.launch {
                Timber.d("Checking URL from SMS: $url")
                val result = urlScanRepository.checkUrl(url)
                if (!result.isSecure && !result.isUnknown) {
                    val detail = if (result.isHeuristicResult) "SMS orqali kelgan shubhali havola: $url"
                                 else "SMS orqali kelgan zararli havola: $url (UrlScan tahlili)"
                    
                    eventLogger.logThreat(
                        type = "URL_SMS_GUARD",
                        severity = "CRITICAL",
                        source = sender,
                        details = detail
                    )
                }
            }
        }
    }
}
