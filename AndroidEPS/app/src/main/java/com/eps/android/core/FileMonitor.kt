package com.eps.android.core

import android.os.FileObserver
import com.eps.android.analysis.StaticAnalyzer
import com.eps.android.data.EventLogger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton
import timber.log.Timber

@Singleton
class FileMonitor @Inject constructor(
    private val staticAnalyzer: StaticAnalyzer,
    private val eventLogger: EventLogger,
    private val threatNotifier: ThreatNotifier
) {
    private val observers = mutableListOf<FileObserver>()
    private val scope = CoroutineScope(Dispatchers.IO)
    private var isMonitoring = false

    fun startMonitoring(path: String) {
        Timber.i("🔍 Adding FileMonitor for path: $path")
        
        val downloadDir = File(path)
        if (!downloadDir.exists() || !downloadDir.canRead()) {
            Timber.w("❌ Cannot monitor path: $path")
            return
        }

        val observer = @Suppress("DEPRECATION")
        object : FileObserver(path, ALL_EVENTS) {
            override fun onEvent(event: Int, fileName: String?) {
                if (fileName != null && !fileName.startsWith(".")) {
                    val file = File(path, fileName)
                    
                    // Trigger analysis on finalization events
                    if (event and (CLOSE_WRITE or MOVED_TO) != 0) {
                        if (fileName.endsWith(".apk", ignoreCase = true)) {
                            Timber.w("🔴 APK FINALIZED: $fileName via event code $event")
                            triggerAnalysis(file)
                        }
                    } else if (event and (CREATE or MODIFY) != 0) {
                        // For CREATE/MODIFY, we wait a bit and check if it's an APK
                        if (fileName.endsWith(".apk", ignoreCase = true)) {
                            Timber.d("🟡 APK DETECTED (PENDING): $fileName via event code $event")
                            triggerAnalysis(file)
                        }
                    }
                }
            }
        }
        
        try {
            observer.startWatching()
            observers.add(observer)
            isMonitoring = true
            Timber.i("✅ FileObserver STARTED for: $path")
        } catch (e: Exception) {
            Timber.e(e, "❌ Failed to start FileObserver for $path")
        }
    }

    private val lastAnalyzed = mutableMapOf<String, Long>()

    private fun triggerAnalysis(file: File) {
        val now = System.currentTimeMillis()
        val path = file.absolutePath
        
        // Debounce: don't analyze same file within 3 seconds
        if (now - (lastAnalyzed[path] ?: 0L) < 3000) return
        lastAnalyzed[path] = now

        scope.launch {
            eventLogger.logInfo("📝 Fayl o'zgardi: ${file.name}")
            // Wait for file to be ready
            kotlinx.coroutines.delay(1000)
            if (file.exists() && file.length() > 0) {
                analyzeIncomingFile(file)
            }
        }
    }

    fun analyzeIncomingFile(file: File) {
        scope.launch {
            try {
                eventLogger.logInfo("🔍 Tekshirilmoqda: ${file.name}")
                Timber.i("🔍 Analyzing file: ${file.absolutePath}")
                
                val verdict = staticAnalyzer.analyzeFile(file)
                
                Timber.i("📊 Analysis result for ${file.name}: suspicious=${verdict.isSuspicious}, risk=${verdict.riskLevel}")
                
                if (verdict.isSuspicious) {
                    Timber.w("🛑 THREAT DETECTED: ${file.name} - ${verdict.riskLevel}")
                    
                    // Show alert for HIGH and CRITICAL threats
                    if (verdict.riskLevel in listOf("HIGH", "CRITICAL", "MEDIUM")) {
                        threatNotifier.showThreatAlert(
                            title = "XAVFLI FAYL ANIQLANDI!",
                            message = "📁 Fayl: ${file.name}\n⚠️ Xavf: ${verdict.riskLevel}\n📝 Sabab: ${verdict.reason}",
                            filePath = file.absolutePath
                        )
                    }

                    eventLogger.logThreat(
                        type = "FILE_GUARD",
                        severity = verdict.riskLevel,
                        source = file.absolutePath,
                        details = "Zararli deb topildi: ${verdict.reason}"
                    )
                } else {
                    eventLogger.logInfo("✅ Xavfsiz: ${file.name}")
                }
            } catch (e: Exception) {
                Timber.e(e, "❌ Error analyzing file: ${file.name}")
                eventLogger.logInfo("⚠️ Tahlil xatosi: ${file.name}")
            }
        }
    }

    fun stopMonitoring() {
        observers.forEach { it.stopWatching() }
        observers.clear()
        isMonitoring = false
        Timber.i("⏹️ FileMonitor stopped (all observers cleared)")
    }
    
    fun isActive(): Boolean = isMonitoring
}
