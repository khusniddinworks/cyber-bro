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
            
            // 1. Identify Installer Source
            val installer = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
                pm.getInstallSourceInfo(packageName).installingPackageName
            } else {
                @Suppress("DEPRECATION")
                pm.getInstallerPackageName(packageName)
            }
            
            Timber.d("New package $packageName installed via: $installer")

            val isTelegramSource = installer?.startsWith("org.telegram") == true || 
                                 installer?.contains("telegram") == true || 
                                 installer == "org.vidogram.messenger"

            // 2. APPLY USER POLICY: Only interfere if Telegram is the source
            if (!isTelegramSource) {
                Timber.i("Package $packageName source ($installer) is trusted. Skipping AI Guard.")
                return
            }

            val apkPath = appInfo.sourceDir
            val apkFile = File(apkPath)

            if (!apkFile.exists()) return

            // 3. UI Notification
            val appLabel = appInfo.loadLabel(pm).toString()
            threatNotifier.showInstallAlert(appLabel, packageName)
            
            // 4. AI Hybrid Analysis
            val verdict = staticAnalyzer.analyzeFile(apkFile)
            eventLogger.logInfo("Telegram Sourced App: $packageName (Risk: ${verdict.riskLevel})")

            // 5. Automatic Ban & Alert for Telegram Sourced Apps
            if (verdict.riskLevel in listOf("MEDIUM", "HIGH", "CRITICAL") || !checkWhitelist(packageName)) {
                // Trigger Full Screen Warning
                threatNotifier.launchAlertActivity(
                    appName = appLabel,
                    packageName = packageName,
                    riskLevel = verdict.riskLevel,
                    reason = "DIQQAT: Ushbu ilova Telegram orqali tarqatilgan va u xavfli bo'lishi mumkin!"
                )
                
                // Log the threat
                eventLogger.logThreat("VISH_TG_BAN", verdict.riskLevel, packageName, "Auto-banned Telegram source")
                
                // 🚀 AUTOMATIC BAN (UNINSTALL PROMPT)
                // Note: We can't silently uninstall on non-root without Device Owner, 
                // but we can launch the uninstall dialog immediately.
                val uninstallIntent = android.content.Intent(android.content.Intent.ACTION_DELETE).apply {
                    data = android.net.Uri.parse("package:$packageName")
                    flags = android.content.Intent.FLAG_ACTIVITY_NEW_TASK
                }
                context.startActivity(uninstallIntent)
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


