package com.eps.android.analysis

import com.eps.android.R

import android.content.Context
import com.eps.android.analysis.ai.MalwareClassifier
import com.eps.android.core.ThreatNotifier
import com.eps.android.data.EventLogger
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton
import timber.log.Timber

@Singleton
class PackageAnalysisManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val staticAnalyzer: StaticAnalyzer,
    private val threatNotifier: ThreatNotifier,
    private val eventLogger: EventLogger
) {

    /**
     * Performs a deep AI audit on a newly installed package
     */
    suspend fun analyzeNewPackage(packageName: String) {
        try {
            val pm = context.packageManager
            val appInfo = pm.getApplicationInfo(packageName, 0)
            val apkPath = appInfo.sourceDir
            val apkFile = File(apkPath)

            if (!apkFile.exists()) {
                Timber.e("APK file not found for $packageName at $apkPath")
                return
            }

            // 0. Immediate Alert: Notify user that we saw the install
            val appLabel = appInfo.loadLabel(pm).toString()
            threatNotifier.showInstallAlert(appLabel, packageName)
            
            // 1. Run Real Hybrid Analysis (DEX + Metadata + AI)
            val verdict = staticAnalyzer.analyzeFile(apkFile)

            // 2. Log result to database
            val lang = context.resources.configuration.locales[0].language
            val logMsg = "App Analysis: $packageName -> Risk: ${verdict.riskLevel}"
            eventLogger.logInfo(logMsg)

            // 3. Strict Policy: Check Whitelist
            val isSystem = (appInfo.flags and android.content.pm.ApplicationInfo.FLAG_SYSTEM) != 0
            val isWhitelisted = checkWhitelist(packageName)
            
            // Logic: If threat found OR (not system AND not whitelisted) -> Show Alert
            if (verdict.riskLevel in listOf("MEDIUM", "HIGH", "CRITICAL")) {
                // Real Virus/Threat -> FULL SCREEN ALERT
                threatNotifier.launchAlertActivity(
                    appName = appLabel,
                    packageName = packageName,
                    riskLevel = verdict.riskLevel,
                    reason = verdict.reason
                )
                eventLogger.logThreat("REALTIME_SHIELD", verdict.riskLevel, packageName, "Malware Detected")
            } else if (!isSystem && !isWhitelisted) {
                // Paranoid Mode: Warn about ANY unknown app -> FULL SCREEN ALERT
                threatNotifier.launchAlertActivity(
                    appName = appLabel,
                    packageName = packageName,
                    riskLevel = "UNKNOWN (Begona)",
                    reason = "Bu ilova ishonchli ro'yxatda yo'q. Ehtiyot bo'ling!"
                )
                eventLogger.logThreat("SUSPICIOUS_INSTALL", "LOW", packageName, "Unknown app installed")
            }
            
        } catch (e: Exception) {
            Timber.e(e, "Error during package analysis: $packageName")
        }
    }

    private fun checkWhitelist(packageName: String): Boolean {
        val safeApps = listOf(
            "com.eps.android",
            "org.telegram", 
            "com.instagram", 
            "com.facebook", 
            "com.whatsapp", 
            "us.zoom", 
            "com.yandex", 
            "uz.dida.payme", 
            "air.com.ssd.click", 
            "uz.uzcard",
            "com.google",
            "com.android",
            "com.samsung",
            "tj.alif",
            "com.transsion"
        )
        return safeApps.any { packageName.startsWith(it) }
    }
}


