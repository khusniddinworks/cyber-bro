package com.eps.android.core

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import java.io.File
import java.security.MessageDigest
import android.util.Base64

/**
 * CYBER BROTHER ARMOR LAYER
 * Advanced anti-reverse engineering and integrity protection.
 */
object SecurityHardening {

    // Your production signing certificate hash (SHA-256)
    // IMPORTANT: This should be updated with your real release key hash
    private const val RELEASE_SIGNATURE_HASH = "YOUR_REAL_RELEASE_SHA256_HASH_HERE"

    /**
     * Checks if the app is being tampered with or running in a compromised environment.
     */
    fun isEnvironmentSafe(context: Context): Boolean {
        if (isDebuggerAttached()) return false
        if (isRooted()) return false
        if (isEmulator()) return false
        return true
    }

    /**
     * Prevents hackers from attaching debuggers to inspect code execution.
     */
    private fun isDebuggerAttached(): Boolean {
        return android.os.Debug.isDebuggerConnected()
    }

    /**
     * Detects if the device has root access (common for hackers).
     */
    private fun isRooted(): Boolean {
        val paths = arrayOf(
            "/system/app/Superuser.apk",
            "/sbin/su",
            "/system/bin/su",
            "/system/xbin/su",
            "/data/local/xbin/su",
            "/data/local/bin/su",
            "/system/sd/xbin/su",
            "/system/bin/failsafe/su",
            "/data/local/su"
        )
        for (path in paths) {
            if (File(path).exists()) return true
        }
        return false
    }

    /**
     * Detects if running on an emulator (standard reverse engineering environment).
     */
    private fun isEmulator(): Boolean {
        return (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"))
                || Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.HARDWARE.contains("goldfish")
                || Build.HARDWARE.contains("ranchu")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || Build.PRODUCT.contains("sdk_google")
                || Build.PRODUCT.contains("google_sdk")
                || Build.PRODUCT.contains("sdk")
                || Build.PRODUCT.contains("sdk_x86")
                || Build.PRODUCT.contains("vbox86p")
                || Build.PRODUCT.contains("emulator")
                || Build.PRODUCT.contains("simulator")
    }

    /**
     * Simple string obfuscation to hide API keys from 'strings' command analysis.
     */
    fun decryptSecret(encrypted: String): String {
        return try {
            val data = Base64.decode(encrypted, Base64.DEFAULT)
            // Fixed XOR key for consistency across devices
            val key = 0x42 
            val result = ByteArray(data.size)
            for (i in data.indices) {
                result[i] = (data[i].toInt() xor key).toByte()
            }
            String(result)
        } catch (e: Exception) {
            ""
        }
    }
}
