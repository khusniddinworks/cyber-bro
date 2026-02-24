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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.Collections
import java.util.concurrent.ConcurrentHashMap
import java.util.regex.Pattern
import javax.inject.Inject

@AndroidEntryPoint
class PhishingAccessibilityService : AccessibilityService() {

    @Inject lateinit var blacklistDao: BlacklistDao
    @Inject lateinit var threatEventDao: ThreatEventDao
    @Inject lateinit var urlScanRepository: com.eps.android.analysis.network.UrlScanRepository
    @Inject lateinit var threatNotifier: ThreatNotifier

    private val serviceScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    
    // Strict URL Pattern: requires proper TLD (2+ letters). Rejects bare numbers.
    // Improved URL Pattern: catches domains with or without http/https, ensures TLD
    private val urlPattern = Pattern.compile(
        "((?:https?://)?(?:[a-zA-Z0-9][\\w\\-]*(?:\\.[a-zA-Z0-9][\\w\\-]*)*\\.[a-zA-Z]{2,})(?:/[\\w\\-._~:/?#\\[\\]@!$&'()*+,;=%]*)?)",
        Pattern.CASE_INSENSITIVE
    )
    
    // Fallback for bare domains (e.g. "bit.ly", "fastpay.uz")
    private val domainPattern = Pattern.compile(
        "\\b([a-zA-Z0-9][a-zA-Z0-9\\-]{0,61}[a-zA-Z0-9]\\.[a-zA-Z]{2,}(?:/[\\w\\-._~:/?#\\[\\]@!$&'()*+,;=%]*)?)\\b",
        Pattern.CASE_INSENSITIVE
    )

    // File extensions to ignore (Avoid false positives like file.apk or image.jpg)
    private val ignoredExtensions = setOf("apk", "jpg", "jpeg", "png", "gif", "pdf", "mp3", "mp4", "zip", "rar", "7z", "txt", "doc", "docx")
    
    // Battery optimization: Debounce scanning for content changes
    private var lastScanTime: Long = 0
    private val SCAN_COOLDOWN_MS = 1500L // Only scan screen content every 1.5 seconds maximum
    
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
    
    // Dangerous TLDs (Expanded)
    private val dangerousTlds = setOf("tk", "ml", "ga", "cf", "gq", "top", "xyz", "pw", "cc", "site", "online", "surf", "casa", "link", "click", "shop", "work")
    
    // Ignored Domains (Whitelist to avoid false positives on common safe sites)
    private val ignoredDomains = setOf(
        // Search & Tech Giants
        "google.com", "google.uz", "google.ru", "yandex.ru", "yandex.uz",
        "apple.com", "microsoft.com", "play.google.com", "bing.com",
        // Messengers & Social Media
        "t.me", "telegram.org", "telegram.me", "web.telegram.org",
        "instagram.com", "facebook.com", "fb.com", "messenger.com",
        "twitter.com", "x.com", "linkedin.com", "tiktok.com",
        "whatsapp.com", "web.whatsapp.com", "signal.org",
        // Media & Entertainment
        "youtube.com", "youtu.be", "spotify.com", "netflix.com",
        // Knowledge & Dev
        "wikipedia.org", "github.com", "stackoverflow.com", "reddit.com",
        // Uzbekistan trusted services
        "uz", "gov.uz", "edu.uz", "ziyonet.uz", "kun.uz", "daryo.uz",
        "gazeta.uz", "spot.uz", "regnum.uz", "norma.uz",
        "click.uz", "payme.uz", "uzum.uz", "olx.uz",
        // Pivot.uz and other news/business
        "pivot.uz"
    )

    // Trusted domain suffixes — for matching subdomains like m.youtube.com or cdn.telegram.org
    private val trustedDomainSuffixes = listOf(
        ".t.me", ".telegram.org", ".telegram.me",
        ".google.com", ".google.uz",
        ".instagram.com", ".facebook.com", ".youtube.com",
        ".twitter.com", ".x.com", ".linkedin.com",
        ".tiktok.com", ".whatsapp.com", ".spotify.com",
        ".wikipedia.org", ".github.com",
        ".gov.uz", ".edu.uz"
    )

    // Forbidden Domains in Uzbekistan (Government Blocklist / Harmful Content)
    private val forbiddenDomains = setOf(
        "1xbet.com", "pin-up.com", "mostbet.com", "melbet.com", // Gambling (Forbidden)
        "islom-uz.com", // Example extremist mirror (placeholder - real lists are dynamic)
        "pornhub.com", "xvideos.com" // Adult content (Harmful)
    )
    
    private var lastForegroundPackage: String? = null
    // Cached blacklist
    private val cachedBlacklist: MutableSet<String> = Collections.newSetFromMap(ConcurrentHashMap())
    
    companion object {
        // Temporary whitelist for users who click "Proceed Anyway"
        val tempWhitelist: MutableSet<String> = Collections.newSetFromMap(ConcurrentHashMap())
        // Track recently scanned window IDs to avoid loops
        private val scannedWindowIds: MutableSet<Int> = Collections.newSetFromMap(ConcurrentHashMap())
        
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
        val eventPackageName = event.packageName?.toString() ?: ""
        
        // Process multiple event types for better coverage
        when (event.eventType) {
            AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED,
            AccessibilityEvent.TYPE_VIEW_CLICKED -> {
                // Continue processing
            }
            else -> return
        }
        
        // --- STRICT CLICK INTERCEPTION ---
        // We ONLY trigger when user effectively clicks something
        val isClick = event.eventType == AccessibilityEvent.TYPE_VIEW_CLICKED
        val isWindowChange = event.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED
        
        // Fast exit for noise
        if (!isClick && !isWindowChange) return
        if (eventPackageName == "com.eps.android") return

        val urls = mutableSetOf<String>()
        
        // 1. Try from clicked node ONLY (Targeted — never scan full screen on click)
        if (isClick) {
            event.source?.let { node ->
                urls.addAll(extractUrlsAndSpans(node))
                // If Telegram click didn't yield URLs from clicked node, check extras
                if (urls.isEmpty() && eventPackageName.contains("telegram")) {
                    // Only check the clicked node's URLSpans — NOT the full screen
                    val extras = node.extras
                    if (extras.containsKey("android.text")) {
                        val spannedText = extras.getCharSequence("android.text")
                        if (spannedText is android.text.Spanned) {
                            val spans = spannedText.getSpans(0, spannedText.length, android.text.style.URLSpan::class.java)
                            spans.forEach { urls.add(it.url) }
                        }
                    }
                }
            }
        }
        
        // 2. Confirm Dialog Fallback (ONLY for genuine "Open link?" dialogs)
        // This should NOT trigger for normal Telegram channel views/navigation
        if (urls.isEmpty() && isWindowChange) {
            val windowText = event.text?.joinToString(" ")?.lowercase() ?: ""
            // Only scan if the dialog explicitly asks about opening a link
            val isConfirmDialog = windowText.contains("open") && (windowText.contains("link") || windowText.contains("http")) ||
                                  windowText.contains("kirish") && windowText.contains("havola") ||
                                  windowText.contains("havolani ochish") ||
                                  windowText.contains("open in browser") ||
                                  windowText.contains("brauzerda ochish")
            
            if (isConfirmDialog) {
                event.text?.forEach { urls.addAll(extractUrlsFromText(it.toString())) }
                if (urls.isEmpty()) {
                    rootInActiveWindow?.let { root ->
                        urls.addAll(extractUrlsAndSpans(root))
                        root.recycle()
                    }
                }
            }
        }
        
        if (urls.isNotEmpty()) {
            val clickedUrl = urls.first()
            val domain = extractDomain(clickedUrl)
                
            // 🛡️ NO-SCAN LOGIC:
            // 1. Skip if domain is in ignored list (exact match OR subdomain match)
            val isIgnored = ignoredDomains.contains(domain) || 
                ignoredDomains.any { domain.endsWith(".$it") } ||
                trustedDomainSuffixes.any { domain.endsWith(it) }
            
            // 2. Trigger Gatekeeper UI for any link that is not ignored
            // We exclude very short strings and files to avoid false triggers
            val isLikelyUrl = clickedUrl.contains(".") && (clickedUrl.startsWith("http") || domain.length >= 4)
            
            val isHighlySuspicious = suspiciousPatterns.any { it.containsMatchIn(clickedUrl) } || 
                                    dangerousTlds.contains(domain.substringAfterLast(".", "")) ||
                                    forbiddenDomains.contains(domain)
            
            if (!isIgnored && (isLikelyUrl || isHighlySuspicious) && domain.length > 3) {
                Timber.d("Authenticated Dangerous Link Click: $clickedUrl from $eventPackageName")
                
                val standardizedUrl = if (!clickedUrl.startsWith("http")) "https://$clickedUrl" else clickedUrl
                
                val intent = Intent(this, com.eps.android.ui.activities.UrlGuardActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_NO_ANIMATION
                    data = android.net.Uri.parse(standardizedUrl)
                    putExtra("is_auto_scan", true)
                    if (forbiddenDomains.contains(domain)) {
                        putExtra("is_forbidden", true)
                    }
                }
                startActivity(intent)
            } else {
                Timber.d("Silent background scan for: $clickedUrl (isIgnored: $isIgnored)")
            }
        }
        
        // Note: event.source is already recycled inside extractUrlsAndSpans if it was used
        // Only recycle if we didn't enter the click block (where it was already consumed)
        if (!isClick) {
            event.source?.recycle()
        }
        
        // --- NEW: CALL VISHING DETECTION ---
        // Monitor phone apps (Dialer, Google Phone, Samsung Phone etc)
        if (eventPackageName.contains("telephony") || eventPackageName.contains("phone") || eventPackageName.contains("dialer")) {
            val screenText = extractAllTextFromEvent(event)
            if (screenText.isNotBlank()) {
                val score = com.eps.android.analysis.VishingPatternMatcher.calculateVishingScore(screenText)
                if (score > 0.4) {
                    com.eps.android.analysis.VishingPatternMatcher.lastDetectionTimestamp = System.currentTimeMillis()
                    Timber.w("VISHING KEYWORD RECORDED DURING CALL (score: $score)")
                    threatNotifier.showVishingAlert("Telefon qo'ng'irog'i", isOtp = false)
                }
            }
        }

        // --- NEW: BLOCK SIDELOADING (CHROME, GOOGLE, TELEGRAM INSTALLS) ---
        val installerApps = listOf(
            "com.android.chrome",
            "com.google.android.apps.docs", // Google Drive
            "com.google.android.gm", // Gmail
            "com.android.browser",
            "com.sec.android.app.sbrowser", // Samsung Browser
            "org.mozilla.firefox",
            "com.android.vending" // Google Play (to be careful, but good for testing)
        )

        val packageInstallers = listOf(
            "com.google.android.packageinstaller",
            "com.android.packageinstaller",
            "com.samsung.android.packageinstaller",
            "com.miui.packageinstaller",
            "com.miui.guardprovider",
            "com.coloros.safecenter",
            "com.huawei.appmarket"
        )

        if (packageInstallers.any { eventPackageName.contains(it, ignoreCase = true) }) {
            val windowId = event.windowId
            
            // If this specific window (installation session) was already scanned, skip
            if (scannedWindowIds.contains(windowId)) {
                return
            }

            Timber.w("DETECTED PACKAGE INSTALLER WINDOW: $windowId (Source: $lastForegroundPackage) - Starting AI Analysis")
            triggerAiScan(windowId, lastForegroundPackage ?: eventPackageName)
            return
        }

        // --- NEW: Update last foreground package if it's not an installer or our app ---
        if (!packageInstallers.any { eventPackageName.contains(it, ignoreCase = true) } && eventPackageName != "com.eps.android" && eventPackageName.isNotEmpty()) {
            lastForegroundPackage = eventPackageName
        }
        
        // Clear old window IDs if they are no longer in focus (optional cleanup)
       // If the current app is not an installer, we can assume the installation window is gone
        if (!packageInstallers.any { eventPackageName.contains(it, ignoreCase = true) }) {
            // Keep the last few, but clear old ones
            if (scannedWindowIds.size > 10) scannedWindowIds.clear()
        }

        // --- NEW: CONTENT-BASED PHISHING DETECTION ---
        // If we are in a browser or unknown app, check for phishing UI elements
        val suspiciousApps = listOf("com.android.chrome", "org.mozilla.firefox", "com.opera.browser", "com.microsoft.emmx")
        if (suspiciousApps.any { eventPackageName.contains(it) } || eventPackageName == "android") {
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
                Timber.w("🛑 PHISHING UI DETECTED ON SCREEN: $eventPackageName")
                // For browsers, we don't cache windowId because different sites can have different phishing
                triggerAiScan(event.windowId, eventPackageName, shouldCache = false)
                return
            }
        }
        
        // --- BACKGROUND URL SCANNING (SILENT) ---
        // For battery saving, we don't scan content too often
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastScanTime < SCAN_COOLDOWN_MS) return
        lastScanTime = currentTime

        val foundUrls = extractUrlsFromEvent(event)
        if (foundUrls.isNotEmpty()) {
            foundUrls.forEach { url ->
                val domain = extractDomain(url)
                val lowerUrl = url.lowercase()
                
                // 🛡️ BACKGROUND FILTER: Only scan if it's a real link or contains suspicious keywords
                val isRealLink = url.startsWith("http") || url.contains("/")
                val hasSuspiciousKeyword = suspiciousPatterns.any { it.containsMatchIn(lowerUrl) } || 
                                         listOf("click", "payme", "uzcard", "tg=", "bonus", "premium").any { lowerUrl.contains(it) }
                val isDangerousTld = dangerousTlds.contains(domain.substringAfterLast(".", ""))

                if (!isRealLink && !hasSuspiciousKeyword && !isDangerousTld) {
                    // Skip garbage like "uzingiz.br" or random dots in text
                    return@forEach
                }

                serviceScope.launch {
                    // 1. FAST CHECK (Local heuristics + Blacklist)
                    if (isPhishingUrl(url, domain)) {
                        Timber.w("Background HEURISTIC Detection: $url")
                        recordThreat(url, domain, eventPackageName, "Heuristik tahlil orqali zararli deb topildi")
                        return@launch
                    }
                    
                    // 2. DEEP CHECK (VirusTotal) - Only for suspicious but unknown
                    if (!tempWhitelist.contains(domain) && !ignoredDomains.contains(domain)) {
                        val standardizedUrl = if (!url.startsWith("http")) "https://$url" else url
                        val uuid = urlScanRepository.submitUrl(standardizedUrl) ?: return@launch
                        // B. Polling (Reduced to 10 seconds for speed)
                        var scanResult: com.eps.android.analysis.network.UrlScanRepository.AnalysisResult? = null
                        repeat(5) {
                            delay(2000) // 2s per poll
                            scanResult = urlScanRepository.getResult(uuid)
                            if (scanResult != null) return@repeat
                        }

                        if (scanResult != null && !scanResult!!.isSecure && !scanResult!!.isUnknown) {
                            Timber.w("Background URLSCAN Detection: $url (Malicious: ${scanResult!!.maliciousCount})")
                            val reason = if (scanResult!!.isHeuristicResult) "AI tahlil orqali zararli deb topildi" 
                                        else "UrlScan tahlili: havola xavfli bo'lishi mumkin"
                            
                            recordThreat(url, domain, eventPackageName, reason)
                        }
                    }
                }
            }
        }
    }

    private suspend fun recordThreat(url: String, domain: String, sourceApp: String, reason: String) {
        threatEventDao.insertEvent(
            ThreatEvent(
                type = "URL_SILENT_GUARD",
                severity = "HIGH",
                source = sourceApp,
                details = "Zararli havola: $url ($reason)"
            )
        )
        // Optionally show a non-intrusive notification here if needed
        // threatNotifier.notifyThreat(...)
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
        
        for (i in 0 until minOf(node.childCount, 50)) {
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
            urls.addAll(extractUrlsAndSpans(node))
            node.recycle()
        }
        
        return urls
    }
    
    private fun extractUrlsAndSpans(node: AccessibilityNodeInfo): Set<String> {
        val urls = mutableSetOf<String>()
        
        // 1. Check for standard text links
        node.text?.let { text ->
            urls.addAll(extractUrlsFromText(text.toString()))
        }
        
        // 2. NEW: Extract "Hidden" links from ClickableSpans (Hyperlinks)
        // Note: For remote nodes, we can try to look for URIs in contentDescription or extras
        node.contentDescription?.let { desc ->
            urls.addAll(extractUrlsFromText(desc.toString()))
        }

        // Handle Special Cases for Telegram/Messengers: 
        // Sometimes hidden links are in the extras or content description if the text is plain
        if (urls.isEmpty()) {
            val extras = node.extras
            if (extras.containsKey("android.text")) {
                val spannedText = extras.getCharSequence("android.text")
                if (spannedText is android.text.Spanned) {
                    val spans = spannedText.getSpans(0, spannedText.length, android.text.style.URLSpan::class.java)
                    spans.forEach { urls.add(it.url) }
                }
            }
        }
        
        // Recursively check child nodes (limit depth)
        for (i in 0 until minOf(node.childCount, 30)) {
            node.getChild(i)?.let { child ->
                urls.addAll(extractUrlsAndSpans(child))
                child.recycle()
            }
        }
        
        return urls
    }

    private fun extractUrlsFromText(text: String): Set<String> {
        val urls = mutableSetOf<String>()
        
        // Refined Logic: If text is just a common word with a dot at the end, IGNORE IT
        // Example: "aniqlangan." -> Ignore
        if (text.endsWith(".") && !text.contains("/") && !text.contains("http") && text.count { it == '.' } == 1) {
            val word = text.removeSuffix(".")
            if (word.all { it.isLetter() }) return emptySet()
        }

        val matcher = urlPattern.matcher(text)
        while (matcher.find()) {
            val url = matcher.group(1) ?: continue
            val extension = url.substringAfterLast(".", "").lowercase()
            // Only add if it's NOT a common file extension
            if (!ignoredExtensions.contains(extension)) {
                // One more check: don't accept raw words ending in .uz as links 
                // if they are part of a sentence and have no other domain structure
                if (!url.startsWith("http") && !url.contains("/")) {
                    val domainOnly = url.split(".")[0]
                    if (domainOnly.length < 3) continue // Skip things like a.uz
                }
                urls.add(url)
            }
        }
        
        // Try fallback domain pattern for things like "click.uz" without http
        val domainMatcher = domainPattern.matcher(text)
        while (domainMatcher.find()) {
            domainMatcher.group(1)?.let { url ->
                val extension = url.substringAfterLast(".", "").lowercase()
                if (!ignoredExtensions.contains(extension)) {
                    urls.add(url)
                }
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
            // First try with the URL as-is
            val uri = java.net.URI(url)
            if (uri.host != null) {
                return uri.host.lowercase()
            }
            // If host is null, the URL is likely missing http:// prefix (e.g. "example.uz")
            val fixedUri = java.net.URI("https://$url")
            fixedUri.host?.lowercase() ?: url.lowercase().split("/").firstOrNull() ?: url.lowercase()
        } catch (e: Exception) {
            // Last resort: try to add https:// and parse again
            try {
                val fixedUri = java.net.URI("https://$url")
                fixedUri.host?.lowercase() ?: url.lowercase()
            } catch (_: Exception) {
                url.lowercase()
            }
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

        // 0.2 CHECK BLOCKLIST (Uzbekistan Forbidden Sites)
        if (forbiddenDomains.contains(domain)) {
            return true
        }

        // 0.3 CHECK IGNORED DOMAINS (WHITELIST) — also check subdomain suffixes
        if (ignoredDomains.contains(domain) || 
            ignoredDomains.any { domain.endsWith(".$it") } ||
            trustedDomainSuffixes.any { domain.endsWith(it) }) {
            return false
        }

        // 1. Check blacklist (The dynamic database blacklist)
        if (cachedBlacklist.contains(domain)) {
            return true
        }
        
        // 2. Metadata/Keyword check on URL itself
        val lowerUrl = url.lowercase()
        // Expanded keywords for Uzbekistan fraud (Telegram, Banking, Gifts)
        val suspiciousKeywords = listOf(
            "click-", "payme-", "uzcard-", "verify", "bonus", "promo", "auth", 
            "tg=", "telegram=", "gift", "premium", "yordam", "sovg'a", "shoshiling",
            "karta", "card", "priz", "konkurs"
        )
        val matchCount = suspiciousKeywords.count { lowerUrl.contains(it) }
        
        // If it matches a known critical keyword pattern or multiple suspicious ones
        if (lowerUrl.contains("tg=") || lowerUrl.contains("bonus")) {
            // High probability fraud link — but skip if domain is trusted
            if (domain.length < 10 && !ignoredDomains.contains(domain) && !ignoredDomains.any { domain.endsWith(".$it") }) {
                return true
            }
        }

        if (matchCount >= 2 && !ignoredDomains.contains(domain) && !ignoredDomains.any { domain.endsWith(".$it") }) {
            return true
        }

        // 3. Check dangerous TLDs
        val tld = domain.substringAfterLast('.', "")
        if (dangerousTlds.contains(tld.lowercase())) {
            return true
        }
        
        // 3.1 Check for Suspicious Short Domains (e.g. cl.2u.uz)
        // Many phishing sites use 2-3 letter domains with digits
        val parts = domain.split('.')
        if (parts.size >= 2) {
            val sltd = parts[parts.size - 2] // Second-level top domain
            if (sltd.length <= 3 && sltd.any { it.isDigit() }) {
                // Potential redirector/phishing domain like 2u.uz, 1x.uz etc.
                if (!domain.contains("gov.uz") && !domain.contains("edu.uz")) {
                    return true
                }
            }
        }
        
        // 4. Check if domain is ANY IP address (highly suspicious for phishing)
        if (domain.matches(Regex("\\d+\\.\\d+\\.\\d+\\.\\d+")) && !isLocalIp) {
            return true
        }
        
        // 5. Check URL length (very long URLs are suspicious)
        if (url.length > 300) {
            return true
        }
        
        // 6. Check for homograph attack (Cyrillic/Greek characters)
        if (domain.any { it.code > 127 && it.code < 1024 }) {
            return true
        }
        
        // 7. Check if domain looks like a fake banking domain
        val fakeBanking = listOf("plclick", "payme-uz", "uzcard-auth", "humo-card")
        if (fakeBanking.any { domain.contains(it) }) {
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
                details = "Zararli havola aniqlandi: $url"
            )
        )
        // Note: Background discovery should NOT open the full screen UI.
        // It's already logged, and clicking the link later will trigger the UI.
    }
    
    private fun showPhishingWarning(url: String, domain: String, sourceApp: String) {
        // Redirecting to UrlGuardActivity for the unified "Scanning" experience as requested
        val intent = Intent(this, com.eps.android.ui.activities.UrlGuardActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_NO_ANIMATION
            putExtra("url", url)
            putExtra("is_auto_scan", true)
        }
        startActivity(intent)
    }

    private fun triggerAiScan(windowId: Int, packageName: String, shouldCache: Boolean = true) {
        // Record this window ID as scanned if requested (e.g. for installers)
        if (shouldCache) {
            scannedWindowIds.add(windowId)
        }

        // Show prominent warning activity IMMEDIATELY with AI SCAN mode
        val intent = Intent(this, PhishingWarningActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or 
                    Intent.FLAG_ACTIVITY_CLEAR_TOP or 
                    Intent.FLAG_ACTIVITY_NO_ANIMATION
            putExtra("mode", "ai_scan")
            putExtra("sourceApp", packageName)
        }
        startActivity(intent)
        
        // Note: We don't performGlobalAction(HOME) here because we want the user 
        // to see the "Scan" first. If it's danger, the Activity's button will 
        // handle the exit logic.
    }

    private fun triggerBlockAction(packageName: String) {
        // Show prominent warning activity IMMEDIATELY
        val intent = Intent(this, PhishingWarningActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or 
                    Intent.FLAG_ACTIVITY_SINGLE_TOP or
                    Intent.FLAG_ACTIVITY_NO_ANIMATION
            putExtra("mode", "block_apk")
            putExtra("sourceApp", packageName)
        }
        startActivity(intent)

        // RADICAL ACTION: Go HOME to ensure the action is interrupted
        performGlobalAction(GLOBAL_ACTION_HOME)
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
