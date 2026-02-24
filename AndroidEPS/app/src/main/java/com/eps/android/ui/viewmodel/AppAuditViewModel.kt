package com.eps.android.ui.viewmodel

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eps.android.analysis.AppRiskAuditor
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AppAuditViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val auditor: AppRiskAuditor,
    private val threatEventDao: com.eps.android.data.ThreatEventDao,
    private val trustedAppDao: com.eps.android.data.TrustedAppDao
) : ViewModel() {

    fun trustApp(packageName: String) {
        viewModelScope.launch {
            trustedAppDao.addTrustedApp(com.eps.android.data.TrustedApp(packageName))
            // Refresh scan to reflect change
            scanApps()
        }
    }

    fun uninstallApp(packageName: String) {
        try {
            val intent = android.content.Intent(android.content.Intent.ACTION_DELETE).apply {
                data = android.net.Uri.fromParts("package", packageName, null)
                flags = android.content.Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(intent)
            // Re-scan after a short delay to allow uninstall to complete
            viewModelScope.launch {
                kotlinx.coroutines.delay(3000)
                scanApps()
            }
        } catch (e: Exception) {
            timber.log.Timber.e(e, "Failed to start uninstall for $packageName")
        }
    }

    data class AppAuditInfo(
        val name: String,
        val packageName: String,
        val riskReport: AppRiskAuditor.RiskReport,
        val isSystem: Boolean,
        val icon: Drawable? = null
    )

    private val _apps = MutableStateFlow<List<AppAuditInfo>>(emptyList())
    val apps: StateFlow<List<AppAuditInfo>> = _apps

    private val _isScanning = MutableStateFlow(false)
    val isScanning: StateFlow<Boolean> = _isScanning

    private val _scanProgress = MutableStateFlow(0f)
    val scanProgress: StateFlow<Float> = _scanProgress
    
    private val _currentScanningApp = MutableStateFlow("")
    val currentScanningApp: StateFlow<String> = _currentScanningApp

    fun scanApps() {
        viewModelScope.launch {
            _isScanning.value = true
            _scanProgress.value = 0f
            
            val resultList = mutableListOf<AppAuditInfo>()
            
            withContext(Dispatchers.IO) {
                val pm = context.packageManager
                val packages = pm.getInstalledPackages(PackageManager.GET_PERMISSIONS)
                val totalApps = packages.size
                
                packages.forEachIndexed { index, pkg ->
                    val appName = pkg.applicationInfo.loadLabel(pm).toString()
                    val packageName = pkg.packageName
                    
                    // Update UI state for "Real" feel
                    _currentScanningApp.value = appName
                    _scanProgress.value = (index.toFloat() / totalApps.toFloat())
                    
                    // Artificial delay to make scan readable and "feel" real (30ms per app)
                    kotlinx.coroutines.delay(10) 

                    val permissions = pkg.requestedPermissions?.toList() ?: emptyList()
                    val isSystem = (pkg.applicationInfo.flags and android.content.pm.ApplicationInfo.FLAG_SYSTEM) != 0
                    val riskReport = auditor.auditPermissions(packageName, permissions, isSystem)
                    
                    if (riskReport.level == AppRiskAuditor.RiskLevel.CRITICAL || riskReport.level == AppRiskAuditor.RiskLevel.HIGH) {
                        if (!isSystem) {
                            threatEventDao.insertEvent(com.eps.android.data.ThreatEvent(
                                type = "APP_AUDIT",
                                severity = riskReport.level.name,
                                source = appName,
                                details = "Xavfli ilova: ${riskReport.explanation}"
                            ))
                        }
                    }
                    
                    val icon = pkg.applicationInfo.loadIcon(pm)
                    resultList.add(AppAuditInfo(appName, packageName, riskReport, isSystem, icon))
                }
            }
            
            _apps.value = resultList.sortedByDescending { it.riskReport.score }
            _isScanning.value = false
            _scanProgress.value = 1f
        }
    }
}
