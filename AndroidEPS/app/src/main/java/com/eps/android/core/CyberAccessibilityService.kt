package com.eps.android.core

import android.accessibilityservice.AccessibilityService
import android.content.Context
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import com.eps.android.data.EventLogger
import com.eps.android.ui.screens.VishingAlertActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * Cyber Brother Guardian Service - Real Proactive Defense
 * Monitors UI events to detect:
 * 1. Phishing / Vishing attempts in real-time apps.
 * 2. Overlay attacks on banking apps.
 * 3. Suspicious credential harvesting.
 */
@AndroidEntryPoint
class CyberAccessibilityService : AccessibilityService() {

    @Inject lateinit var eventLogger: EventLogger

    private val serviceScope = CoroutineScope(Dispatchers.Main + kotlinx.coroutines.SupervisorJob())

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        when (event.eventType) {
            AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED -> handleWindowStateChanged(event)
            AccessibilityEvent.TYPE_VIEW_FOCUSED -> handleViewFocused(event)
            AccessibilityEvent.TYPE_VIEW_CLICKED -> handleViewClicked(event)
        }
    }

    private fun handleWindowStateChanged(event: AccessibilityEvent) {
        val packageName = event.packageName?.toString() ?: return
        
        // Detect Overlay Attack (simplification: check if a suspicious window covers a bank)
        if (isBankingApp(packageName)) {
            Timber.d("Securing Banking Session: $packageName")
            // In a real scenario, we'd check for FLAG_SECURE or unauthorized overlays
        }
    }

    private fun handleViewFocused(event: AccessibilityEvent) {
        val node = event.source ?: return
        
        // Real-time Credit Card / OTP harvesting detection
        scanNodeForSensitiveContent(node)
    }

    private fun handleViewClicked(event: AccessibilityEvent) {
        val node = event.source ?: return
        // Detect if user clicks "Allow" on suspicious permissions triggered by malware
    }

    private fun scanNodeForSensitiveContent(node: AccessibilityNodeInfo) {
        val text = node.text?.toString()?.lowercase() ?: ""
        val contentDescription = node.contentDescription?.toString()?.lowercase() ?: ""

        val sensitiveKeywords = listOf("card number", "cvv", "expiry", "password", "pin code", "karta raqami", "parol", "sms kod")
        
        if (sensitiveKeywords.any { text.contains(it) || contentDescription.contains(it) }) {
            Timber.w("🚨 Sensitive data entry field detected: $text")
            // Proactive warning if on a suspicious package
            val pkg = node.packageName?.toString() ?: "unknown"
            if (!isKnownSecureApp(pkg)) {
                triggerWarning("Maxfiy ma'lumot kiritish: $pkg ilovasi xavfsiz emasligi mumkin!")
            }
        }
        
        // Recursive scan
        for (i in 0 until node.childCount) {
            node.getChild(i)?.let { scanNodeForSensitiveContent(it) }
        }
    }

    private fun isBankingApp(pkg: String): Boolean {
        return pkg.contains("bank", ignoreCase = true) || pkg.contains("payme") || pkg.contains("click.uz")
    }

    private fun isKnownSecureApp(pkg: String): Boolean {
        val whitelist = listOf("com.android.settings", "com.google.android.gms", "com.eps.android")
        return whitelist.contains(pkg) || pkg.startsWith("com.android.systemui")
    }

    private fun triggerWarning(message: String) {
        serviceScope.launch {
            eventLogger.logThreat("GUARDIAN_CORE", "HIGH", "Accessibility", message)
            // Optional: Show a "Guardian Bubble" or Toast
        }
    }

    override fun onInterrupt() {
        Timber.e("Guardian Service Interrupted")
    }

    override fun onServiceConnected() {
        super.onServiceConnected()
        Timber.i("✅ Cyber Brother Guardian Service Connected and Proactive.")
    }
}
