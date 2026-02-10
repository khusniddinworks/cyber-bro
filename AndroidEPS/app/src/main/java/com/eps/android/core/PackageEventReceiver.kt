package com.eps.android.core

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.eps.android.analysis.ApkAnalyzer
import com.eps.android.analysis.AppRiskAuditor
import com.eps.android.data.EventLogger
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class PackageEventReceiver : BroadcastReceiver() {

    @Inject
    lateinit var packageAnalysisManager: com.eps.android.analysis.PackageAnalysisManager

    @Inject
    lateinit var eventLogger: EventLogger

    private val scope = CoroutineScope(Dispatchers.IO)

    override fun onReceive(context: Context?, intent: Intent?) {
        val packageName = intent?.data?.encodedSchemeSpecificPart ?: return
        
        // Only trigger on actual package additions
        if (intent.action == Intent.ACTION_PACKAGE_ADDED) {
            Timber.d("New package detected: $packageName - Starting AI Audit")
            
            scope.launch {
                packageAnalysisManager.analyzeNewPackage(packageName)
            }
        }
    }
}
