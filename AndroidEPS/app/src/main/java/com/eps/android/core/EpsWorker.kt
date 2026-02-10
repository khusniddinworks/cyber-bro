package com.eps.android.core

import android.content.Context
import android.os.Environment
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.eps.android.analysis.StaticAnalyzer
import com.eps.android.data.ThreatEvent
import com.eps.android.data.ThreatEventDao
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.File

@HiltWorker
class EpsWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val staticAnalyzer: StaticAnalyzer,
    private val threatEventDao: ThreatEventDao,
    private val blacklistDao: com.eps.android.data.BlacklistDao
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            // 1. Perform background storage scan
            val root = Environment.getExternalStorageDirectory()
            val apkFiles = mutableListOf<File>()
            findApks(root, apkFiles)

            apkFiles.forEach { file ->
                val verdict = staticAnalyzer.analyzeFile(file)
                if (verdict.isSuspicious) {
                    threatEventDao.insertEvent(ThreatEvent(
                        type = "FILE_GUARD_BG",
                        severity = verdict.riskLevel,
                        source = file.name,
                        details = "Fon rejimida xavfli APK topildi: ${verdict.reason}"
                    ))
                }
            }

            // 2. Offline Mode: External blacklist updates disabled for privacy
            Result.success()
        } catch (e: Exception) {
            Timber.e(e, "Error in EpsWorker background scan")
            Result.retry()
        }
    }

    private fun findApks(dir: File, list: MutableList<File>) {
        val files = dir.listFiles() ?: return
        for (file in files) {
            if (file.isDirectory) {
                if (!file.name.startsWith(".")) {
                    findApks(file, list)
                }
            } else if (file.name.endsWith(".apk", ignoreCase = true)) {
                list.add(file)
            }
        }
    }
}
