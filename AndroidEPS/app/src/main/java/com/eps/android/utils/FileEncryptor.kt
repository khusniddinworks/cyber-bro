package com.eps.android.utils

import android.content.Context
import android.net.Uri
import java.io.InputStream
import java.io.OutputStream
import java.security.SecureRandom
import java.security.spec.KeySpec
import javax.crypto.Cipher
import javax.crypto.CipherInputStream
import javax.crypto.CipherOutputStream
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

/**
 * Master-Level File Encryptor
 * Uses AES-256-GCM with PBKDF2 key derivation.
 */
import javax.inject.Inject
import dagger.hilt.android.qualifiers.ApplicationContext

class FileEncryptor @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val ALGORITHM = "AES"
    private val TRANSFORMATION = "AES/GCM/NoPadding"
    private val TAG_LENGTH_BIT = 128
    private val IV_LENGTH_BYTE = 12
    private val SALT_LENGTH_BYTE = 16
    private val PBKDF2_ITERATIONS = 65536 // Master Level
    private val KEY_LENGTH_BIT = 256
    private val SIGNATURE_V5 = "VOID_V5".toByteArray() 
    private val SIGNATURE_V6 = "VOID_V6".toByteArray() // New Protocol: Stores FileName, not just extension

    /**
     * Encrypts a file from sourceUri to destStream using password
     * Header Structure: [SIG 7B] [ExtLen 1B] [Ext N_Bytes] [Salt 16B] [IV 12B]
     */
    fun encrypt(sourceUri: Uri, destStream: OutputStream, password: CharArray): Boolean {
        return try {
            val contentResolver = context.contentResolver
            
            // 1. Get Original Filename
            val fileName = getFileName(sourceUri) ?: "encrypted_file.dat"
            val nameBytes = fileName.toByteArray(Charsets.UTF_8)
            if (nameBytes.size > 65535) throw IllegalArgumentException("Filename too long")

            // 2. Write Header (V6)
            destStream.write(SIGNATURE_V6)
            
            // Write Name Length (2 Bytes - Short)
            val lenHi = (nameBytes.size shr 8) and 0xFF
            val lenLo = nameBytes.size and 0xFF
            destStream.write(lenHi)
            destStream.write(lenLo)
            
            // Write Name
            destStream.write(nameBytes)

            // 3. Generate and write Salt & IV
            val salt = ByteArray(SALT_LENGTH_BYTE)
            SecureRandom().nextBytes(salt)
            destStream.write(salt)

            val iv = ByteArray(IV_LENGTH_BYTE)
            SecureRandom().nextBytes(iv)
            destStream.write(iv)

            // 4. Encrypt Content
            val secretKey = deriveKey(password, salt)
            val cipher = Cipher.getInstance(TRANSFORMATION)
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, GCMParameterSpec(TAG_LENGTH_BIT, iv))

            val inputStream = contentResolver.openInputStream(sourceUri)
            val cipherOutputStream = CipherOutputStream(destStream, cipher)

            inputStream?.use { input ->
                input.copyTo(cipherOutputStream)
            }
            cipherOutputStream.close()
            
            password.fill('0') 
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    /**
     * Decrypts a file. Returns the restored file if successful, null otherwise.
     */
    fun decrypt(sourceUri: Uri, outputDir: java.io.File, password: CharArray): java.io.File? {
        var inputStream: InputStream? = null
        return try {
            inputStream = context.contentResolver.openInputStream(sourceUri) ?: return null
            
            // 1. Verify Signature
            val sigCheck = ByteArray(7) // Length of VOID_V5 / VOID_V6
            if (!readFully(inputStream, sigCheck)) return null

            var originalName = "restored_file.dat"
            
            if (sigCheck.contentEquals(SIGNATURE_V6)) {
                 // V6 Logic: Read Filename
                 val lenHi = inputStream.read()
                 val lenLo = inputStream.read()
                 if (lenHi == -1 || lenLo == -1) return null
                 
                 val nameLen = (lenHi shl 8) or lenLo
                 val nameBytes = ByteArray(nameLen)
                 if (!readFully(inputStream, nameBytes)) return null
                 
                 originalName = String(nameBytes, Charsets.UTF_8)
                 
            } else if (sigCheck.contentEquals(SIGNATURE_V5)) {
                 // V5 Logic: Read Extension Only
                 val extLen = inputStream.read()
                 if (extLen == -1) return null
                 val extBytes = ByteArray(extLen)
                 if (!readFully(inputStream, extBytes)) return null
                 val ext = String(extBytes, Charsets.UTF_8)
                 originalName = "RESTORED_${System.currentTimeMillis()}.${if(ext.isEmpty()) "dat" else ext}"
                 
            } else {
                return null // Unknown Signature
            }

            // 3. Read Salt & IV
            val salt = ByteArray(SALT_LENGTH_BYTE)
            if (!readFully(inputStream, salt)) return null
            
            val iv = ByteArray(IV_LENGTH_BYTE)
            if (!readFully(inputStream, iv)) return null

            // 4. Decrypt
            val secretKey = deriveKey(password, salt)
            val cipher = Cipher.getInstance(TRANSFORMATION)
            cipher.init(Cipher.DECRYPT_MODE, secretKey, GCMParameterSpec(TAG_LENGTH_BIT, iv))

            val outputFile = java.io.File(outputDir, originalName)
            // Ensure unique name if exists
            var uniqueFile = outputFile
            var counter = 1
            while (uniqueFile.exists()) {
                val name = outputFile.nameWithoutExtension
                val ext = outputFile.extension
                uniqueFile = java.io.File(outputDir, "${name}_($counter).$ext")
                counter++
            }
            
            val fos = java.io.FileOutputStream(uniqueFile)
            val cipherInputStream = CipherInputStream(inputStream, cipher)

            cipherInputStream.use { input ->
                input.copyTo(fos)
            }
            fos.close()
            
            // 5. MAGIC BYTE FIX (Only for V5 legacy or if V6 name was generic)
            var finalFile = uniqueFile
            if (sigCheck.contentEquals(SIGNATURE_V5) && (originalName.endsWith(".dat") || originalName.endsWith(".bin"))) {
                  val detectedExt = detectExtensionFromMagicBytes(uniqueFile)
                  if (detectedExt != null) {
                      val newFile = java.io.File(outputDir, "RESTORED_${System.currentTimeMillis()}.$detectedExt")
                      if (uniqueFile.renameTo(newFile)) {
                          finalFile = newFile
                      }
                  }
            }

            password.fill('0')
            finalFile
        } catch (e: Exception) {
            e.printStackTrace()
            null
        } finally {
            inputStream?.close()
        }
    }

    private fun readFully(iStream: InputStream, b: ByteArray): Boolean {
        var offset = 0
        while (offset < b.size) {
            val count = iStream.read(b, offset, b.size - offset)
            if (count <= 0) return false
            offset += count
        }
        return true
    }

    private fun detectExtensionFromMagicBytes(file: java.io.File): String? {
        try {
            val iStream = java.io.FileInputStream(file)
            val header = ByteArray(8) // Read first 8 bytes
            if (iStream.read(header) < 4) {
                iStream.close()
                return null
            }
            iStream.close()
            
            // Convert to Hex String for easy checking
            val hex = header.joinToString("") { "%02X".format(it) }
            
            return when {
                hex.startsWith("FFD8FF") -> "jpg"
                hex.startsWith("89504E47") -> "png"
                hex.startsWith("25504446") -> "pdf" // %PDF
                hex.startsWith("504B0304") -> "zip" // ZIP, APK, DOCX (Generic)
                hex.startsWith("52617221") -> "rar"
                hex.startsWith("494433") -> "mp3"
                hex.contains("66747970") -> "mp4" // ...ftyp (MP4) - check substring
                else -> null
            }
        } catch (e: Exception) { return null }
    }

    private fun getFileName(uri: Uri): String? {
        var result: String? = null
        if (uri.scheme == "content") {
            try {
                context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
                    if (cursor.moveToFirst()) {
                        val index = cursor.getColumnIndex(android.provider.OpenableColumns.DISPLAY_NAME)
                        if (index != -1) result = cursor.getString(index)
                    }
                }
            } catch (e: Exception) {}
        }
        if (result == null) {
            result = uri.path
            val cut = result?.lastIndexOf('/')
            if (cut != null && cut != -1) {
                result = result?.substring(cut + 1)
            }
        }
        return result
    }

    private fun getExtension(uri: Uri): String? {
        // 1. Try from URL
        val extFromUrl = android.webkit.MimeTypeMap.getFileExtensionFromUrl(uri.toString())
        if (!extFromUrl.isNullOrEmpty()) return extFromUrl
        
        // 2. Try from MimeType
        val mimeType = context.contentResolver.getType(uri)
        if (mimeType != null) {
            val extFromMime = android.webkit.MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType)
            if (!extFromMime.isNullOrEmpty()) return extFromMime
        }
        
        // 3. Try from FileName
        val name = getFileName(uri)
        return name?.substringAfterLast(".", "")
    }

    private fun deriveKey(password: CharArray, salt: ByteArray): SecretKeySpec {
        val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
        val spec: KeySpec = PBEKeySpec(password, salt, PBKDF2_ITERATIONS, KEY_LENGTH_BIT)
        val tmp = factory.generateSecret(spec)
        return SecretKeySpec(tmp.encoded, ALGORITHM)
    }
}
