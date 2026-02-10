package com.eps.android.analysis

import android.content.Context
import com.eps.android.analysis.ai.MalwareClassifier
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StaticAnalyzer @Inject constructor(
    @ApplicationContext private val context: Context,
    private val hashCalculator: HashCalculator,
    private val entropyCalculator: EntropyCalculator,
    private val apkAnalyzer: ApkAnalyzer,
    private val malwareClassifier: MalwareClassifier
) {
    data class FileVerdict(
        val hash: String,
        val entropy: Double,
        val riskLevel: String,
        val isSuspicious: Boolean,
        val reason: String,
        val packageName: String = "Unknown",
        val detectedPermissions: List<String> = emptyList(),
        val aiScore: Float = 0f
    )

    fun analyzeFile(file: File): FileVerdict {
        val hash = hashCalculator.calculateSha256(file)
        val entropy = entropyCalculator.calculateEntropy(file)
        
        var riskLevel = "LOW"
        var isSuspicious = false
        var reason = "Fayl toza ko'rinadi."
        var finalAiScore = 0f

        // 1. Initial Entropy Check
        if (entropy > 7.7) {
            riskLevel = "HIGH"
            isSuspicious = true
            reason = "Yuqori entropiya: Fayl shifrlangan yoki siqilgan bo'lishi mumkin."
        }
        
        // 2. Comprehensive APK Analysis (Metadata + AI)
        if (file.name.endsWith(".apk", ignoreCase = true)) {
            // A. Permission & Metadata Audit
            val apkVerdict = apkAnalyzer.analyzeApk(context, file)
            
            // B. Real AI Content Analysis (DEX Features)
            val aiVerdict = malwareClassifier.classifyApk(file)
            finalAiScore = aiVerdict.probability

            // C. Final Decision Logic (Hybrid) - RADICAL INCREASE IN SENSITIVITY
            val combinedScore = (apkVerdict.riskReport.score + (finalAiScore * 100)) / 2
            
            // If AI is even slightly suspicious (> 35%) or metadata shows risk (> 25)
            if (combinedScore > 25 || aiVerdict.probability > 0.35) {
                isSuspicious = true
                riskLevel = when {
                    combinedScore > 70 || aiVerdict.probability > 0.8 -> "CRITICAL"
                    combinedScore > 40 || aiVerdict.probability > 0.5 -> "HIGH"
                    else -> "MEDIUM"
                }
                
                reason = "AI Tahlil: ${aiVerdict.behaviorTags.joinToString(", ")}. " +
                         "Audit: ${apkVerdict.riskReport.explanation}"
            }
            
            return FileVerdict(
                hash, entropy, riskLevel, isSuspicious, reason,
                apkVerdict.packageName, apkVerdict.permissions, finalAiScore
            )
        }

        return FileVerdict(hash, entropy, riskLevel, isSuspicious, reason, aiScore = finalAiScore)
    }
}
