package com.eps.android.analysis

object VishingPatternMatcher {

    /**
     * Common phishing keywords/phrases in Uzbek, Russian, and English
     * focused on "Vishing" (Voice Phishing).
     */
    val UZBEK_PATTERNS = listOf(
        "sms kod", "maxfiy kod", "kodni ayting", "tasdiqlash kodi",
        "kartangiz blok", "plastik karta", "xavfsizlik xizmati",
        "transaksiya", "hujum bo'ldi", "pul kiritildi", "shoshiling",
        "parolni bering", "raqamga sms bordi", "kartangizni blokladik"
    )

    val RUSSIAN_PATTERNS = listOf(
        "код из смс", "смс код", "скажите код", "подтверждение",
        "ваша карта заблокирована", "служба безопасности", "банк",
        "подозрительная транзакция", "атака на счет", "перевели деньги",
        "пароль", "номер карты", "срок действия"
    )

    val ENGLISH_PATTERNS = listOf(
        "sms code", "verification code", "tell me the code", "security code",
        "card blocked", "bank security", "suspicious transaction",
        "account at risk", "urgent", "provide password", "card details"
    )

    /**
     * Checks if a text snippet contains phishing indicators.
     * Returns a score between 0.0 and 1.0
     */
    fun calculateVishingScore(text: String): Double {
        val lowerText = text.lowercase()
        var matches = 0
        
        val allPatterns = UZBEK_PATTERNS + RUSSIAN_PATTERNS + ENGLISH_PATTERNS
        
        allPatterns.forEach { pattern ->
            if (lowerText.contains(pattern)) {
                matches++
            }
        }
        
        if (matches == 0) return 0.0
        
        // Simple scoring: 1 match = 0.5, 2+ matches = 0.9
        return if (matches == 1) 0.5 else 0.95
    }
}
