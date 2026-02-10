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
import javax.inject.Inject

@AndroidEntryPoint
class SmsReceiver : BroadcastReceiver() {

    @Inject
    lateinit var eventLogger: EventLogger

    private val scope = CoroutineScope(Dispatchers.IO)

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Telephony.Sms.Intents.SMS_RECEIVED_ACTION) {
            val messages = Telephony.Sms.Intents.getMessagesFromIntent(intent)
            for (msg in messages) {
                val body = msg.messageBody
                val sender = msg.originatingAddress ?: "Unknown"
                
                // Check if user is currently on a phone call
                val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as android.telephony.TelephonyManager
                val isOnCall = telephonyManager.callState != android.telephony.TelephonyManager.CALL_STATE_IDLE

                analyzeSms(context, body, sender, isOnCall)
            }
        }
    }

    private fun analyzeSms(context: Context, body: String, sender: String, isOnCall: Boolean) {
        val otpPattern = "\\b\\d{4,8}\\b".toRegex() // Matches 4-8 digit numbers
        val hasOtp = otpPattern.containsMatchIn(body)
        
        // Context keywords (Must be present to consider it a threat while on call)
        val financialKeywords = listOf("karta", "blok", "xavfsizlik", "card", "bank", "security", "plastic", "plastik", "account")
        val urgencyKeywords = listOf("shoshiling", "tezda", "urgent", "blocked", "bloklandi", "hujum", "attack", "suspicious")
        val codeKeywords = listOf("kod", "tasdiqlash", "parol", "code", "verify", "password")

        val lowerBody = body.lowercase()
        val hasFinancial = financialKeywords.any { lowerBody.contains(it) }
        val hasUrgency = urgencyKeywords.any { lowerBody.contains(it) }
        val hasCode = codeKeywords.any { lowerBody.contains(it) }

        // Vishing Logic:
        // 1. User is on a call
        // 2. Message contains a Code/OTP
        // 3. Message ALSO contains Financial OR Urgency keywords (Context)
        // This prevents false positives like "Your Telegram login code is 12345" from triggering Vishing alert just because you are on a call.
        if (isOnCall && hasOtp && (hasFinancial || hasUrgency)) {
            // HIGH PROBABILITY Vishing
            triggerVishingAlarm(context, sender, body)
        } else if (isOnCall && hasCode && hasFinancial) {
            // MEDIUM PROBABILITY (No digits, but asks for code + financial)
             triggerVishingAlarm(context, sender, body)
        } else {
            // Standard phishing check (Links, known bad domains)
            val phishingPatterns = listOf("bit.ly", "tinyurl", "ngrok", "gift", "yutuq", "prize", "login-", "secure-")
            val foundPatterns = phishingPatterns.filter { lowerBody.contains(it) }
            
            if (foundPatterns.isNotEmpty() && lowerBody.contains("http")) {
                logThreat(sender, "Shubhali SMS/Link: Phishing belgilari topildi.")
            }
        }
    }

    private fun triggerVishingAlarm(context: Context, sender: String, body: String) {
        scope.launch {
            eventLogger.logThreat(
                type = "VISHING_GUARD",
                severity = "CRITICAL",
                source = sender,
                details = "DIQQAT: Kimdir sizdan kodni so'ragan bo'lishi mumkin! Xabarni MUTLAQO AYTMANG!"
            )
        }
        
        // Launch the Panic Activity
        val intent = Intent(context, VishingAlertActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            putExtra("sender", sender)
        }
        context.startActivity(intent)
    }

    private fun logThreat(sender: String, details: String) {
        scope.launch {
            eventLogger.logThreat("SMS_GUARD", "HIGH", sender, details)
        }
    }
}
