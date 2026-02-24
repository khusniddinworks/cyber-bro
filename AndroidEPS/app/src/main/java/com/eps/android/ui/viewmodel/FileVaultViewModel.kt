package com.eps.android.ui.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eps.android.utils.FileEncryptor
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

@HiltViewModel
class FileVaultViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val encryptor: FileEncryptor
) : ViewModel() {

    private val _isProcessing = MutableStateFlow(false)
    val isProcessing: StateFlow<Boolean> = _isProcessing

    private val _resultPath = MutableStateFlow<String?>(null)
    val resultPath: StateFlow<String?> = _resultPath

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _vaultFiles = MutableStateFlow<List<File>>(emptyList())
    val vaultFiles: StateFlow<List<File>> = _vaultFiles

    init {
        refreshVault()
    }

    fun refreshVault() {
        val vaultDir = File(context.filesDir, "vault")
        if (vaultDir.exists()) {
            _vaultFiles.value = vaultDir.listFiles()?.toList()?.sortedByDescending { it.lastModified() } ?: emptyList()
        }
    }

    fun clearResult() {
        _resultPath.value = null
        _error.value = null
    }

    fun clearError() {
        _error.value = null
    }

    fun encryptFiles(sourceUris: List<Uri>, password: String, shredOriginal: Boolean) {
        viewModelScope.launch {
            _isProcessing.value = true
            _error.value = null
            _resultPath.value = null
            
            val passwordChars = password.toCharArray()
            
            withContext(Dispatchers.IO) {
                try {
                    val timeStamp = System.currentTimeMillis()
                    val outputDir = File(context.cacheDir, "vault").apply { mkdirs() }
                    
                    val fileToEncrypt: File
                    val finalExtension: String
                    
                    if (sourceUris.size == 1) {
                        // Single file: just copy to temp and encrypt
                        fileToEncrypt = createTempFileFromUri(sourceUris[0]) ?: throw Exception("Failed to read file")
                        // original name is now handled by Encryptor V6 reading from Uri metadata or passed manually if needed
                        // But wait, createTempFileFromUri makes a temp file with generic name.
                        // We need to pass original URI to encryptor so it can read the name from ContentResolver!
                        // Yes, encryptor.encrypt takes URI.
                    } else {
                        // Multiple files: Zip them together
                        fileToEncrypt = createZipFromUris(sourceUris)
                        // For zip, the name is container_...zip.
                    }
                    
                    val persistentVaultDir = File(context.filesDir, "vault").apply { mkdirs() }
                    
                    // Improve Filename: Use original name if possible
                    val baseName = if (sourceUris.size == 1) {
                         getFileName(sourceUris[0]).substringBeforeLast(".")
                    } else {
                         "Archive"
                    }
                    val safeName = baseName.replace(Regex("[^a-zA-Z0-9.-]"), "_").take(30)
                    val outputFile = File(persistentVaultDir, "${safeName}_${timeStamp}.cbr")
                    
                    val fos = FileOutputStream(outputFile)
                    
                    // IF single file, we pass original URI so Encryptor reads metadata for Header
                    // IF zip, we pass temp file URI
                    val inputUri = if(sourceUris.size == 1) sourceUris[0] else Uri.fromFile(fileToEncrypt)
                    
                    val success = encryptor.encrypt(inputUri, fos, passwordChars)
                    fos.close()
                    fileToEncrypt.delete() // Cleanup temp

                    if (success) {
                        _resultPath.value = outputFile.absolutePath
                        if (shredOriginal) {
                            sourceUris.forEach { uri ->
                                try {
                                    context.contentResolver.delete(uri, null, null)
                                } catch (e: Exception) {}
                            }
                        }
                    } else {
                        _error.value = "Encryption Failed"
                        outputFile.delete()
                    }
                    refreshVault() // Update List
                } catch (e: Exception) {
                    _error.value = e.message
                }
            }
            passwordChars.fill('0')
            _isProcessing.value = false
        }
    }

    private fun createTempFileFromUri(uri: Uri): File? {
        return try {
            val file = File(context.cacheDir, "temp_${System.currentTimeMillis()}")
            context.contentResolver.openInputStream(uri)?.use { input ->
                file.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
            file
        } catch (e: Exception) { null }
    }

    private fun createZipFromUris(uris: List<Uri>): File {
        val zipFile = File(context.cacheDir, "container_${System.currentTimeMillis()}.zip")
        java.util.zip.ZipOutputStream(zipFile.outputStream()).use { zos ->
            uris.forEach { uri ->
                val fileName = getFileName(uri)
                context.contentResolver.openInputStream(uri)?.use { input ->
                    zos.putNextEntry(java.util.zip.ZipEntry(fileName))
                    input.copyTo(zos)
                    zos.closeEntry()
                }
            }
        }
        return zipFile
    }

    private fun getFileName(uri: Uri): String {
        var name = "file_${System.currentTimeMillis()}"
        context.contentResolver.query(uri, arrayOf(android.provider.OpenableColumns.DISPLAY_NAME), null, null, null)?.use { cursor ->
            if (cursor.moveToFirst()) {
                name = cursor.getString(0)
            }
        }
        return name
    }

    private fun getExtension(uri: Uri): String {
        return getFileName(uri).substringAfterLast(".", "dat")
    }

    fun decryptFile(encryptedFile: File, password: String) {
        viewModelScope.launch {
            _isProcessing.value = true
            _error.value = null
            _resultPath.value = null
            
            if (!encryptedFile.exists()) {
                _error.value = "File not found"
                _isProcessing.value = false
                return@launch
            }

            val passwordChars = password.toCharArray()

            withContext(Dispatchers.IO) {
                try {
                    val outputDir = File(context.getExternalFilesDir(null), "decrypted").apply { mkdirs() }
                    // We don't clear old files anymore, user might want to keep them.
                    
                    val sourceUri = Uri.fromFile(encryptedFile)
                    val restoredFile = encryptor.decrypt(sourceUri, outputDir, passwordChars)

                    if (restoredFile != null && restoredFile.exists()) {
                        _resultPath.value = restoredFile.absolutePath
                    } else {
                        _error.value = "Parol noto'g'ri yoki fayl buzilgan!"
                    }
                } catch (e: Exception) {
                    _error.value = "Decryption System Error: ${e.message}"
                }
            }
            passwordChars.fill('0')
            _isProcessing.value = false
        }
    }

    fun exportToPublicStorage(file: File) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val downloadsDir = android.os.Environment.getExternalStoragePublicDirectory(android.os.Environment.DIRECTORY_DOWNLOADS)
                val destFile = java.io.File(downloadsDir, file.name)
                
                // Ensure unique name
                var uniqueFile = destFile
                var counter = 1
                while (uniqueFile.exists()) {
                    val name = destFile.nameWithoutExtension
                    val ext = destFile.extension
                    uniqueFile = java.io.File(downloadsDir, "${name}_($counter).$ext")
                    counter++
                }

                file.inputStream().use { input ->
                    java.io.FileOutputStream(uniqueFile).use { output ->
                        input.copyTo(output)
                    }
                }
                
                withContext(Dispatchers.Main) {
                    android.widget.Toast.makeText(context, "Fayl 'Downloads' papkasiga saqlandi!", android.widget.Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    android.widget.Toast.makeText(context, "Eksport xatosi: ${e.message}", android.widget.Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    fun deleteFile(file: File) {
        if (file.exists()) {
            file.delete()
            refreshVault()
        }
    }
}
