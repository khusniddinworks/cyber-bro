package com.eps.android.analysis

import android.net.Uri
import timber.log.Timber

object UrlHeuristicAnalyzer {

    private val dangerousTlds = setOf(
        "tk", "ml", "ga", "cf", "gq", "top", "xyz", "pw", "cc", "site", "online", 
        "surf", "casa", "link", "click", "shop", "work", "bid", "loan", "men", "live", "icu", "cyou"
    )

    // Trusted domains that should NEVER be flagged
    private val trustedDomains = setOf(
        // Messengers & Social Media
        "t.me", "telegram.org", "telegram.me", "web.telegram.org",
        "instagram.com", "facebook.com", "fb.com", "messenger.com",
        "twitter.com", "x.com", "linkedin.com", "tiktok.com",
        "whatsapp.com", "web.whatsapp.com", "signal.org",
        // Media & Entertainment
        "youtube.com", "youtu.be", "spotify.com", "netflix.com",
        // Search & Tech Giants
        "google.com", "google.uz", "google.ru", "yandex.ru", "yandex.uz",
        "apple.com", "microsoft.com", "play.google.com", "bing.com",
        // Knowledge & Dev
        "wikipedia.org", "github.com", "stackoverflow.com", "reddit.com",
        // Uzbekistan trusted
        "gov.uz", "edu.uz", "ziyonet.uz", "kun.uz", "daryo.uz",
        "gazeta.uz", "spot.uz", "norma.uz", "pivot.uz",
        "click.uz", "payme.uz", "uzum.uz", "olx.uz"
    )

    private val trustedSuffixes = listOf(
        ".t.me", ".telegram.org", ".telegram.me",
        ".google.com", ".google.uz",
        ".instagram.com", ".facebook.com", ".youtube.com",
        ".twitter.com", ".x.com", ".linkedin.com",
        ".tiktok.com", ".whatsapp.com", ".spotify.com",
        ".wikipedia.org", ".github.com",
        ".gov.uz", ".edu.uz"
    )

    fun analyze(url: String): HeuristicResult {
        val lowerUrl = url.lowercase()
        val uri = try { Uri.parse(url) } catch (e: Exception) { null }
        val domain = uri?.host?.lowercase() ?: extractDomainManual(url)

        // 0. TRUSTED DOMAIN CHECK — skip analysis entirely for known safe domains
        if (trustedDomains.contains(domain) || 
            trustedDomains.any { domain.endsWith(".$it") } ||
            trustedSuffixes.any { domain.endsWith(it) }) {
            Timber.d("Trusted domain skipped: $domain")
            return HeuristicResult(false)
        }

        // 1. Check for IP addresses (except local)
        if (domain.matches(Regex("\\d+\\.\\d+\\.\\d+\\.\\d+"))) {
            val isLocal = domain.startsWith("127.") || domain.startsWith("192.168.") || domain.startsWith("10.") || domain == "localhost"
            if (!isLocal) return HeuristicResult(true, "IP manzil orqali kirish (shubhali)")
        }

        // 2. Dangerous TLDs
        val tld = domain.substringAfterLast('.', "")
        if (dangerousTlds.contains(tld)) {
            return HeuristicResult(true, "Xavfli domen zonasi (.$tld)")
        }

        // 3. Phishing Keywords
        val suspiciousKeywords = listOf(
            "click-", "payme-", "uzcard-", "verify", "bonus", "promo", "auth", 
            "tg=", "telegram=", "gift", "premium", "yordam", "sovg'a", "shoshiling",
            "karta", "card", "priz", "konkurs", "kabin-", "security-check",
            "clck", "pyme", "my-humo", "fastpay", "alif-", "tbc-", "apelsin-", "uzum-"
        )
        
        val matches = suspiciousKeywords.filter { lowerUrl.contains(it) }
        
        // High probability patterns
        if (lowerUrl.contains("tg=") || lowerUrl.contains("bonus") || lowerUrl.contains("premium")) {
            if (domain.length < 12 && !trustedDomains.contains(domain) && !trustedDomains.any { domain.endsWith(".$it") }) {
                return HeuristicResult(true, "Soxta Telegram yoki sovg'a havolasi")
            }
        }

        if (matches.size >= 2 && !domain.contains("click.uz") && !domain.contains("payme.uz") && !domain.contains("uzum.uz")) {
            return HeuristicResult(true, "Fishingga xos kalit so'zlar aniqlandi (${matches.take(2).joinToString()})")
        }

        // 4. Homograph Attack (Non-ASCII characters in domain)
        if (domain.any { it.code > 127 }) {
            return HeuristicResult(true, "Soxta harflar ishlatilgan (Homograph hujum)")
        }

        // 5. Excessive length
        if (url.length > 200) {
            return HeuristicResult(true, "Haddan tashqari uzun havola (shubhali)")
        }

        return HeuristicResult(false)
    }

    private fun extractDomainManual(url: String): String {
        return try {
            val cleanUrl = if (!url.contains("://")) "http://$url" else url
            val uri = Uri.parse(cleanUrl)
            uri.host?.lowercase() ?: ""
        } catch (e: Exception) {
            ""
        }
    }

    data class HeuristicResult(
        val isSuspicious: Boolean,
        val reason: String? = null
    )
}
