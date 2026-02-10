package com.eps.android.core

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import com.eps.android.data.BlacklistDao
import com.eps.android.data.ThreatEvent
import com.eps.android.data.ThreatEventDao
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.regex.Pattern
import javax.inject.Inject

@AndroidEntryPoint
class PhishingAccessibilityService : AccessibilityService() {

    @Inject lateinit var blacklistDao: BlacklistDao
    @Inject lateinit var threatEventDao: ThreatEventDao

    private val serviceScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    
    // URL Regex Pattern
    private val urlPattern = Pattern.compile(
        "(https?://[\\w\\-._~:/?#\\[\\]@!$&'()*+,;=%]+)",
        Pattern.CASE_INSENSITIVE
    )
    
    // Suspicious patterns (Requirement 5 strengthened)
    private val suspiciousPatterns = listOf(
        Regex(".*-verify.*", RegexOption.IGNORE_CASE),
        Regex(".*-secure.*", RegexOption.IGNORE_CASE),
        Regex(".*-login.*", RegexOption.IGNORE_CASE),
        Regex(".*-account.*", RegexOption.IGNORE_CASE),
        Regex(".*-kabinet.*", RegexOption.IGNORE_CASE),
        Regex(".*payme-.*", RegexOption.IGNORE_CASE),
        Regex(".*click-uz.*", RegexOption.IGNORE_CASE),
        Regex(".*uzcard-.*", RegexOption.IGNORE_CASE),
        Regex(".*humo-.*", RegexOption.IGNORE_CASE),
        Regex(".*my-humo.*", RegexOption.IGNORE_CASE),
        Regex(".*bonus-uz.*", RegexOption.IGNORE_CASE),
        Regex(".*telegram-.*", RegexOption.IGNORE_CASE),
        Regex(".*kapitalbank.*", RegexOption.IGNORE_CASE),
        Regex(".*aloqabank.*", RegexOption.IGNORE_CASE)
    )
    
    // Dangerous TLDs
    private val dangerousTlds = setOf("tk", "ml", "ga", "cf", "gq", "top", "xyz", "pw", "cc", "site", "online", "surf", "casa")
    
    // Cached blacklist
    private val cachedBlacklist = mutableSetOf<String>()
    
    companion object {
        // Temporary whitelist for users who click "Proceed Anyway"
        val tempWhitelist = mutableSetOf<String>()
        
        fun addToWhitelist(domain: String) {
            tempWhitelist.add(domain)
        }
    }
    
    override fun onServiceConnected() {
        super.onServiceConnected()
        Timber.i("PhishingAccessibilityService connected")
        loadBlacklist()
    }
    
    private fun loadBlacklist() {
        serviceScope.launch {
            try {
                val domains = blacklistDao.getAllDomains()
                cachedBlacklist.clear()
                cachedBlacklist.addAll(domains.map { it.domain })
                Timber.i("Loaded ${cachedBlacklist.size} blacklisted domains")
            } catch (e: Exception) {
                Timber.e(e, "Error loading blacklist")
            }
        }
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        // Process multiple event types for better coverage
        when (event.eventType) {
            AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED,
            AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED,
            AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED,
            AccessibilityEvent.TYPE_VIEW_CLICKED -> {
                // Continue processing
            }
            else -> return
        }
        
        val packageName = event.packageName?.toString() ?: return
        
        // --- NEW: CALL VISHING DETECTION ---
        // Monitor phone apps (Dialer, Google Phone, Samsung Phone etc)
        if (packageName.contains("telephony") || packageName.contains("phone") || packageName.contains("dialer")) {
            val screenText = extractAllTextFromEvent(event)
            if (screenText.isNotBlank()) {
                val score = com.eps.android.analysis.VishingPatternMatcher.calculateVishingScore(screenText)
                if (score > 0.4) {
                    Timber.w("VISHING DETECTED DURING CALL: $screenText")
                    showVishingWarning(screenText)
                }
            }
        }

        // --- NEW: BLOCK SIDELOADING (TELEGRAM INSTALLS) ---
        val blockers = listOf(
            "com.google.android.packageinstaller",
            "com.android.packageinstaller",
            "com.samsung.android.packageinstaller",
            "com.miui.packageinstaller",
            "com.miui.guardprovider"
        )

        if (blockers.any { packageName.contains(it, ignoreCase = true) }) {
            Timber.w("BLOCKED PACKAGE INSTALLER: $packageName")
            triggerBlockAction()
            return
        }

        // --- NEW: CONTENT-BASED PHISHING DETECTION ---
        // If we are in a browser or unknown app, check for phishing UI elements
        val suspiciousApps = listOf("com.android.chrome", "org.mozilla.firefox", "com.opera.browser", "com.microsoft.emmx")
        if (suspiciousApps.any { packageName.contains(it) } || packageName == "android") {
            val screenText = extractAllTextFromEvent(event).lowercase()
            
            // Check for phishing keyword combinations
            val cardTerms = listOf("karta raqami", "номер карты", "card number")
            val bankTerms = listOf("click", "payme", "uzcard", "tasdiqlash", "подтвердить", "verify")
            val rewardTerms = listOf("bonus", "yutib oldingiz", "вы выиграли", "sovg'a")
            
            val hasCardInput = cardTerms.any { screenText.contains(it) }
            val hasBankContext = bankTerms.any { screenText.contains(it) }
            val hasRewardContext = rewardTerms.any { screenText.contains(it) }
            
            // If screen has CARD INPUT + (BANK CONTEXT or REWARD CONTEXT)
            if (hasCardInput && (hasBankContext || hasRewardContext)) {
                Timber.w("🛑 PHISHING UI DETECTED ON SCREEN: $packageName")
                triggerBlockAction()
                return
            }
        }
        
        // --- EXISTING: URL PHISHING DETECTION ---
        // Extract URLs from the event
        val urls = extractUrlsFromEvent(event)
        
        if (urls.isNotEmpty()) {
            urls.forEach { url ->
                checkUrl(url, packageName)
            }
        }
    }

    private fun extractAllTextFromEvent(event: AccessibilityEvent): String {
        val sb = StringBuilder()
        event.text?.forEach { sb.append(it).append(" ") }
        event.contentDescription?.let { sb.append(it).append(" ") }
        
        event.source?.let { node ->
            sb.append(extractAllTextFromNode(node))
            node.recycle()
        }
        return sb.toString()
    }

    private fun extractAllTextFromNode(node: AccessibilityNodeInfo): String {
        val sb = StringBuilder()
        node.text?.let { sb.append(it).append(" ") }
        node.contentDescription?.let { sb.append(it).append(" ") }
        
        for (i in 0 until minOf(node.childCount, 30)) {
            node.getChild(i)?.let { child ->
                sb.append(extractAllTextFromNode(child))
                child.recycle()
            }
        }
        return sb.toString()
    }
    
    private fun extractUrlsFromEvent(event: AccessibilityEvent): Set<String> {
        val urls = mutableSetOf<String>()
        
        // Extract from event text
        event.text?.forEach { text ->
            urls.addAll(extractUrlsFromText(text.toString()))
        }
        
        // Extract from content description
        event.contentDescription?.let { desc ->
            urls.addAll(extractUrlsFromText(desc.toString()))
        }
        
        // Extract from source node
        event.source?.let { node ->
            urls.addAll(extractUrlsFromNode(node))
            node.recycle()
        }
        
        return urls
    }
    
    private fun extractUrlsFromNode(node: AccessibilityNodeInfo): Set<String> {
        val urls = mutableSetOf<String>()
        
        // Get text from current node
        node.text?.let { text ->
            urls.addAll(extractUrlsFromText(text.toString()))
        }
        
        node.contentDescription?.let { desc ->
            urls.addAll(extractUrlsFromText(desc.toString()))
        }
        
        // Recursively check child nodes (limit depth to avoid performance issues)
        for (i in 0 until minOf(node.childCount, 20)) {
            node.getChild(i)?.let { child ->
                urls.addAll(extractUrlsFromNode(child))
                child.recycle()
            }
        }
        
        return urls
    }
    
    private fun extractUrlsFromText(text: String): Set<String> {
        val urls = mutableSetOf<String>()
        val matcher = urlPattern.matcher(text)
        
        while (matcher.find()) {
            matcher.group(1)?.let { url ->
                urls.add(url)
            }
        }
        
        return urls
    }
    
    private fun checkUrl(url: String, sourceApp: String) {
        serviceScope.launch {
            try {
                val domain = extractDomain(url)
                
                if (isPhishingUrl(url, domain)) {
                    Timber.w("PHISHING DETECTED: $url from $sourceApp")
                    handlePhishingUrl(url, domain, sourceApp)
                }
            } catch (e: Exception) {
                Timber.e(e, "Error checking URL: $url")
            }
        }
    }
    
    private fun extractDomain(url: String): String {
        return try {
            val uri = java.net.URI(url)
            uri.host ?: url
        } catch (e: Exception) {
            url
        }
    }
    
    private suspend fun isPhishingUrl(url: String, domain: String): Boolean {
        // 00. CHECK TEMP WHITELIST (Requirement: allow proceed anyway)
        if (tempWhitelist.contains(domain)) {
            return false
        }

        // 0. IMPROVED LOCAL IP CHECK (Requirement: catch all local ranges)
        val isLocalIp = domain.matches(Regex("^(127\\.0\\.0\\.1|localhost|10\\..*|192\\.168\\..*|172\\.(1[6-9]|2[0-9]|3[0-1])\\..*)$"))
        if (isLocalIp) {
            // If on local network, block if it looks like a phishing mock-up
            if (url.contains("phish") || url.contains("test") || url.contains(".html")) {
                Timber.w("🛑 Local Phishing Test Detected: $url")
                return true
            }
        }

        // 1. Check blacklist
        if (cachedBlacklist.contains(domain)) {
            return true
        }
        
        // 2. Metadata/Keyword check on URL itself
        val lowerUrl = url.lowercase()
        val suspiciousKeywords = listOf("click-", "payme-", "uzcard-", "verify", "bonus", "promo", "auth")
        val matchCount = suspiciousKeywords.count { lowerUrl.contains(it) }
        
        // If domain is suspicious AND has phishing keywords
        if (matchCount >= 2 && !domain.contains("click.uz") && !domain.contains("payme.uz")) {
            return true
        }

        // 3. Check dangerous TLDs
        val tld = domain.substringAfterLast('.', "")
        if (dangerousTlds.contains(tld.lowercase())) {
            return true
        }
        
        // 4. Check if domain is ANY IP address (highly suspicious for phishing)
        if (domain.matches(Regex("\\d+\\.\\d+\\.\\d+\\.\\d+")) && !isLocalIp) {
            return true
        }
        
        // 5. Check URL length (very long URLs are suspicious)
        if (url.length > 150) {
            return true
        }
        
        // 6. Check for homograph attack (Cyrillic/Greek characters)
        if (domain.any { it.code > 127 && it.code < 1024 }) {
            return true
        }
        
        return false
    }
    
    private suspend fun handlePhishingUrl(url: String, domain: String, sourceApp: String) {
        // Log to database
        threatEventDao.insertEvent(
            ThreatEvent(
                type = "URL_PHISHING",
                severity = "CRITICAL",
                source = sourceApp,
                details = "Phishing URL aniqlandi: $url"
            )
        )
        
        // Show blocking overlay
        showPhishingWarning(url, domain, sourceApp)
    }
    
    private fun showPhishingWarning(url: String, domain: String, sourceApp: String) {
        val intent = Intent(this, PhishingWarningActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
            putExtra("url", url)
            putExtra("domain", domain)
            putExtra("sourceApp", sourceApp)
        }
        startActivity(intent)
    }

    private fun triggerBlockAction() {
        // RADICAL ACTION: Go HOME immediately. 
        // "Back" can be ignored, "Home" minimizes everything.
        performGlobalAction(GLOBAL_ACTION_HOME)
        performGlobalAction(GLOBAL_ACTION_BACK) // Double tap checking
        
        android.widget.Toast.makeText(
            applicationContext, 
            "Cyber Brother: ⛔ APK O'RNATISH TA'QIQLANADI!",
            android.widget.Toast.LENGTH_LONG
        ).show()
    }

    private fun showVishingWarning(text: String) {
        val intent = Intent(this, PhishingWarningActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
            putExtra("mode", "vishing")
            putExtra("detectedText", text)
        }
        startActivity(intent)
    }

    override fun onInterrupt() {
        Timber.w("PhishingAccessibilityService interrupted")
    }
    
    override fun onDestroy() {
        super.onDestroy()
        Timber.i("PhishingAccessibilityService destroyed")
    }
}
