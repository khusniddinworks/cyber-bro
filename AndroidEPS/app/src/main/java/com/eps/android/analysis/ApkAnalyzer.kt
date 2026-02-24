package com.eps.android.analysis

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApkAnalyzer @Inject constructor(
    private val auditor: AppRiskAuditor
) {

    suspend fun analyzeApk(context: Context, file: File): ApkVerdict {
        val pm = context.packageManager
        val packageInfo = pm.getPackageArchiveInfo(file.absolutePath, PackageManager.GET_PERMISSIONS)
        
        if (packageInfo == null) {
            return ApkVerdict(
                packageName = "Unknown",
                permissions = emptyList(),
                riskReport = AppRiskAuditor.RiskReport(
                    AppRiskAuditor.RiskLevel.LOW, 0, emptyList(), "Could not parse APK metadata."
                )
            )
        }

        val packageName = packageInfo.packageName
        val permissions = packageInfo.requestedPermissions?.toList() ?: emptyList()
        
        // --- REAL SIGNATURE ANALYSIS ---
        var isTestSigned = false
        var signDetails = ""
        try {
            // Get signatures (modern API for Android 9+)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                val pkgInfoSig = pm.getPackageArchiveInfo(file.absolutePath, PackageManager.GET_SIGNING_CERTIFICATES)
                val sigInfo = pkgInfoSig?.signingInfo
                if (sigInfo != null) {
                    val certs = if (sigInfo.hasMultipleSigners()) sigInfo.apkContentsSigners else sigInfo.signingCertificateHistory
                    if (certs != null && certs.isNotEmpty()) {
                        val certBytes = certs[0].toByteArray()
                        val md = java.security.MessageDigest.getInstance("SHA-1")
                        val sha1 = md.digest(certBytes)
                        val fingerprint = sha1.joinToString(":") { "%02X".format(it) }
                        
                        // Known debug/test certificate SHA-1 fingerprints
                        val debugFingerprints = setOf(
                            "A4:0D:A8:0A:59:D1:70:CA:A9:50:CF:15:C1:8C:45:4D:47:A3:9B:26", // Android Studio default debug
                            "5A:8F:16:AE:4D:00:8B:2F:E1:65:D2:7B:76:23:8D:4A:11:B1:F9:3D"  // Common test key
                        )
                        
                        isTestSigned = debugFingerprints.any { fingerprint.equals(it, ignoreCase = true) }
                        signDetails = if (isTestSigned) "⚠️ DEBUG/TEST CERTIFICATE" else "✅ Signed: ${fingerprint.take(20)}..."
                    } else {
                        signDetails = "⚠️ No certificate found"
                    }
                } else {
                    signDetails = "⚠️ Could not read signature"
                }
            } else {
                // Fallback for older Android versions
                @Suppress("DEPRECATION")
                val pkgInfoOld = pm.getPackageArchiveInfo(file.absolutePath, PackageManager.GET_SIGNATURES)
                val signatures = pkgInfoOld?.signatures
                if (signatures != null && signatures.isNotEmpty()) {
                    val certBytes = signatures[0].toByteArray()
                    val md = java.security.MessageDigest.getInstance("SHA-1")
                    val sha1 = md.digest(certBytes)
                    val fingerprint = sha1.joinToString(":") { "%02X".format(it) }
                    signDetails = "Signed: ${fingerprint.take(20)}..."
                } else {
                    signDetails = "⚠️ No signature"
                }
            }
        } catch (e: Exception) {
            signDetails = "❌ Signature Error: ${e.message}"
        }

        // Use the improved auditor that accounts for system app context
        val riskReport = auditor.auditPermissions(packageName, permissions, isSystem = false)
        
        // Adjust risk based on signature
        val finalReport = when {
            signDetails.contains("Error") || signDetails.contains("No signature") -> 
                riskReport.copy(level = AppRiskAuditor.RiskLevel.CRITICAL, score = 100, explanation = "SIGNATURE ERROR: $signDetails")
            isTestSigned -> 
                riskReport.copy(level = AppRiskAuditor.RiskLevel.HIGH, score = maxOf(riskReport.score, 70), explanation = "${riskReport.explanation} | DEBUG CERT DETECTED")
            else -> riskReport
        }

        return ApkVerdict(
            packageName = packageName,
            permissions = permissions,
            riskReport = finalReport
        )
    }

    suspend fun analyzeInstalledApp(context: Context, packageName: String): ApkVerdict {
        val pm = context.packageManager
        return try {
            val pkgInfo = pm.getPackageInfo(packageName, PackageManager.GET_PERMISSIONS)
            val permissions = pkgInfo.requestedPermissions?.toList() ?: emptyList()
            val isSystem = (pkgInfo.applicationInfo.flags and android.content.pm.ApplicationInfo.FLAG_SYSTEM) != 0
            
            // Check if installed from Play Store
            val installer = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                try { pm.getInstallSourceInfo(packageName).installingPackageName } catch (e: Exception) { null }
            } else {
                @Suppress("DEPRECATION")
                pm.getInstallerPackageName(packageName)
            }
            val isPlayStore = installer == "com.android.vending"

            val riskReport = auditor.auditPermissions(packageName, permissions, isSystem, isPlayStore)
            
            ApkVerdict(packageName, permissions, riskReport)
        } catch (e: Exception) {
            ApkVerdict(packageName, emptyList(), AppRiskAuditor.RiskReport(
                AppRiskAuditor.RiskLevel.LOW, 0, emptyList(), "Error analyzing installed app: ${e.message}"
            ))
        }
    }

    data class ApkVerdict(
        val packageName: String,
        val permissions: List<String>,
        val riskReport: AppRiskAuditor.RiskReport
    )
}
