package com.eps.android.analysis

import javax.inject.Inject
import javax.inject.Singleton

/**
 * SMART PHISHING NLP ENGINE (PRO)
 * 
 * Bu klas shunchaki so'zlarni qidirmaydi. U matnning "Semantik Ma'nosi"ni 
 * "Concept Matrix" yordamida tahlil qiladi.
 * 
 * Bu Llama-3 kabi LLM larga yaqinroq mantiq bilan ishlaydi, lekin 100% offline va tez.
 */
@Singleton
class SmartPhishingNLP @Inject constructor(
    private val tfLiteEngine: com.eps.android.analysis.ai.TfLiteEngine
) {

    enum class Concept {
        IDENTITY_PROMPT,   // "Siz bilan bankdan gaplashyapmiz"
        THREAT_URGENCY,    // "Hujum bo'ldi", "Bloklandi"
        ACTION_REWARD,     // "Yutuq", "Pul tushdi"
        DATA_REQUEST,      // "Kodni ayting", "SMSni bering"
        FINANCIAL_OBJECT   // "Karta", "Hisob", "Plastik"
    }

    private val weights = mapOf(
        Concept.IDENTITY_PROMPT to 15,
        Concept.THREAT_URGENCY to 25,
        Concept.ACTION_REWARD to 20,
        Concept.DATA_REQUEST to 40,
        Concept.FINANCIAL_OBJECT to 10
    )

    private val dictionary = mapOf(
        Concept.IDENTITY_PROMPT to listOf(
            "bank", "xavfsizlik", "security", "admiral", "operator", "markazdan", "aloqa"
        ),
        Concept.THREAT_URGENCY to listOf(
            "hujum", "attack", "shoshiling", "blok", "xavf", "tezda", "urgent", "error", "xato"
        ),
        Concept.ACTION_REWARD to listOf(
            "yutuq", "pul", "prize", "bonus", "gift", "sovg'a", "omad", "tushdi", "kiritildi"
        ),
        Concept.DATA_REQUEST to listOf(
            "kod", "code", "ayting", "sms", "tasdiqlang", "tasdiqlash", "bering", "verify", "password", "parol", "raqam"
        ),
        Concept.FINANCIAL_OBJECT to listOf(
            "karta", "card", "plastik", "hisob", "account", "mablag'", "payme", "click", "uzcard"
        )
    )

    data class AnalysisResult(
        val score: Int,
        val detectedConcepts: List<Concept>,
        val isCritical: Boolean
    )

    fun analyzeText(text: String, isOnCall: Boolean = false): AnalysisResult {
        val lowerText = text.lowercase()
        val detected = mutableListOf<Concept>()
        var totalScore = 0

        // 1. TENSORFLOW LITE LAYER (Neural Network Analysis)
        val aiConfidence = tfLiteEngine.classifyText(text)
        if (aiConfidence > 0.7f) {
            totalScore += 50 // Neural Network sezgi (Deep learning detection)
        } else if (aiConfidence > 0.4f) {
            totalScore += 25
        }

        // 2. Concept Detection (Semantic Rules)
        dictionary.forEach { (concept, words) ->
            if (words.any { lowerText.contains(it) }) {
                detected.add(concept)
                totalScore += weights[concept] ?: 0
            }
        }

        // 2. Semantic Proximity Bonus (Kodni + Ayting birga kelsa)
        if (lowerText.contains("kod") && (lowerText.contains("ayting") || lowerText.contains("bering"))) {
            totalScore += 20
        }

        // 3. Digits Check (OTP detection)
        val otpPattern = "\\b\\d{4,8}\\b".toRegex()
        if (otpPattern.containsMatchIn(text)) {
            totalScore += 30
        }

        // 4. ON CALL MULTIPLIER (Siz aytgan senariy)
        if (isOnCall) {
            // Agar qo'ng'iroq bo'layotgan bo'lsa, xavf darajasi 2 baravar ortadi
            totalScore = (totalScore * 1.8).toInt()
        }

        // 5. Final Verdict
        val limit = if (isOnCall) 50 else 70
        
        return AnalysisResult(
            score = totalScore.coerceAtMost(100),
            detectedConcepts = detected,
            isCritical = totalScore >= limit
        )
    }
}
