package com.eps.android.ui.viewmodel

import android.content.Context
import android.os.Build
import android.os.Environment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eps.android.analysis.StaticAnalyzer
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

@HiltViewModel
class FileScanViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val staticAnalyzer: StaticAnalyzer,
    private val malariaClassifier: com.eps.android.analysis.ai.MalwareClassifier,
    private val threatEventDao: com.eps.android.data.ThreatEventDao
) : ViewModel() {

    data class ScannedFile(
        val file: File,
        val verdict: StaticAnalyzer.FileVerdict,
        val aiVerdict: com.eps.android.analysis.ai.MalwareClassifier.AiVerdict? = null
    )

    private val _scannedFiles = MutableStateFlow<List<ScannedFile>>(emptyList())
    val scannedFiles: StateFlow<List<ScannedFile>> = _scannedFiles

    private val _isScanning = MutableStateFlow(false)
    val isScanning: StateFlow<Boolean> = _isScanning

    private val _hasScanned = MutableStateFlow(false)
    val hasScanned: StateFlow<Boolean> = _hasScanned
    
    private val _hasStoragePermission = MutableStateFlow(true)
    val hasStoragePermission: StateFlow<Boolean> = _hasStoragePermission
    
    init {
        checkStoragePermission()
    }
    
    fun checkStoragePermission(): Boolean {
        val hasPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            Environment.isExternalStorageManager()
        } else {
            true // Pre-Android 11 doesn't need this special permission
        }
        _hasStoragePermission.value = hasPermission
        timber.log.Timber.i("📱 Storage permission check: $hasPermission (SDK ${Build.VERSION.SDK_INT})")
        return hasPermission
    }

    fun scanStorage() {
        viewModelScope.launch {
            _isScanning.value = true
            _scannedFiles.value = emptyList()
            
            val results = withContext(Dispatchers.IO) {
                val apkFiles = mutableListOf<File>()
                
                // Scan multiple common locations - COMPREHENSIVE LIST
                val externalStorage = Environment.getExternalStorageDirectory()
                val scanPaths = listOf(
                    // Standard Download locations
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                    File(externalStorage, "Download"),
                    File(externalStorage, "Downloads"),
                    File("/sdcard/Download"),
                    File("/sdcard/Downloads"),
                    File("/storage/emulated/0/Download"),
                    File("/storage/emulated/0/Downloads"),
                    
                    // Telegram - ALL possible locations
                    File(externalStorage, "Telegram"),
                    File(externalStorage, "Telegram/Telegram Documents"),
                    File(externalStorage, "Telegram/Telegram Files"),
                    File(externalStorage, "Telegram/Telegram Video"),
                    File(externalStorage, "Telegram/Telegram Audio"),
                    File("/sdcard/Telegram"),
                    File("/sdcard/Telegram/Telegram Documents"),
                    File("/sdcard/Android/data/org.telegram.messenger/files/Telegram/Telegram Documents"),
                    File("/storage/emulated/0/Telegram"),
                    File("/storage/emulated/0/Telegram/Telegram Documents"),
                    File("/storage/emulated/0/Android/data/org.telegram.messenger/files/Telegram/Telegram Documents"),
                    
                    // Other messaging apps
                    File(externalStorage, "Documents"),
                    File(externalStorage, "WhatsApp/Media"),
                    File("/sdcard/DCIM"),
                    File("/sdcard/Android/data")
                )
                
                timber.log.Timber.w("🔍 FILE GUARD: Starting scan - checking ${scanPaths.size} paths")
                
                scanPaths.forEach { dir ->
                    if (dir.exists() && dir.canRead()) {
                        timber.log.Timber.d("✅ Scanning: ${dir.absolutePath}")
                        val beforeCount = apkFiles.size
                        findApks(dir, apkFiles)
                        val foundInDir = apkFiles.size - beforeCount
                        if (foundInDir > 0) {
                            timber.log.Timber.i("📦 Found $foundInDir APKs in: ${dir.absolutePath}")
                        }
                    } else {
                        timber.log.Timber.w("❌ Cannot access: ${dir.absolutePath} (exists=${dir.exists()}, canRead=${dir.canRead()})")
                    }
                }
                
                // Also try root external storage if we have MANAGE_EXTERNAL_STORAGE
                try {
                    val root = Environment.getExternalStorageDirectory()
                    if (root.exists() && root.canRead()) {
                        findApks(root, apkFiles)
                    }
                } catch (e: Exception) {
                    timber.log.Timber.w(e, "Cannot scan root storage")
                }
                
                // Remove duplicates by absolute path
                val uniqueApks = apkFiles.distinctBy { it.absolutePath }
                timber.log.Timber.i("Found ${uniqueApks.size} unique APK files")
                
                uniqueApks.map { file ->
                    // 1. Static Analysis (Hash, Entropy, Permissions)
                    val staticVerdict = staticAnalyzer.analyzeFile(file)
                    
                    // 2. Offline AI Analysis (Opcode Heuristics)
                    val aiVerdict = malariaClassifier.classifyApk(file)
                    
                    // Combine Logic: If AI is very confident (> 85%), override static low risk
                    var isRisk = staticVerdict.isSuspicious
                    var reason = staticVerdict.reason
                    
                    if (aiVerdict.probability > 0.85f) {
                        isRisk = true
                        reason += "\n[AI] Yuqori xavf aniqlandi (${(aiVerdict.probability * 100).toInt()}%). Kod tuzilishi zararli."
                        
                        threatEventDao.insertEvent(com.eps.android.data.ThreatEvent(
                            type = "AI_OFFLINE_MALWARE",
                            severity = "CRITICAL",
                            source = file.name,
                            details = "Offline AI aniqladi: ${(aiVerdict.probability * 100).toInt()}% ehtimol bilan virus."
                        ))
                    } else if (isRisk) {
                        // Standard static threat
                        threatEventDao.insertEvent(com.eps.android.data.ThreatEvent(
                            type = "FILE_GUARD",
                            severity = "HIGH",
                            source = file.name,
                            details = "Shubhali APK: ${staticVerdict.reason}"
                        ))
                    }
                    
                    ScannedFile(file, staticVerdict, aiVerdict)
                }
            }
            
            _scannedFiles.value = results
            _isScanning.value = false
            _hasScanned.value = true
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

    fun deleteFile(file: File) {
        if (file.exists()) {
            file.delete()
            _scannedFiles.value = _scannedFiles.value.filter { it.file != file }
        }
    }
}
