package com.eps.android.core

import android.content.Context
import android.os.Build
import android.provider.Settings
import java.nio.charset.StandardCharsets
import java.security.MessageDigest

/**
 * CYBER IDENTITY V2 (STEALTH PROTOCOL)
 * Pro-level encrypted device identity system.
 */
object IdentityManager {

    private const val SECRET_ALPHA = "CB_SHIELD_V2_2026"
    private const val SECRET_BETA = "ULTRA_SECURITY_CORE"
    
    // Stealth Encoding Alphabet (Removed similar looking chars like 0, O, I, 1)
    private const val ALPHABET = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789"

    /**
     * Generates a cryptographically strong, unique Device ID.
     * Hidden logic: Checksums are embedded at specific non-sequential positions.
     */
    fun getDeviceId(context: Context): String {
        val androidId = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID) ?: "777"
        val hwInfo = Build.BRAND + Build.MODEL + Build.BOARD + Build.HARDWARE
        
        // Multi-Layer Hash
        val rawHash = hashString(androidId + SECRET_ALPHA + hwInfo + SECRET_BETA)
        
        // Convert Hash to Encoded String (Take middle part for more randomness)
        val encoded = encodeToBase32(rawHash.substring(10, 30))
        
        // Structure: PREFIX - BODY(4) - BODY(4) - CHECKSUM(2)
        // Hidden Checksum logic: Sum of ASCII codes of indices 0, 4, 8 mod 32 mapped to ALPHABET
        val body = encoded.take(8)
        val checksumPart = generateHiddenChecksum(body)
        
        return "CYB-" + body.substring(0, 4) + "-" + body.substring(4, 8) + "-" + checksumPart
    }

    private fun generateHiddenChecksum(body: String): String {
        // Obfuscated math to make verification non-obvious
        val sum = body.fold(0) { acc, char -> acc + char.code }
        val c1 = ALPHABET[(sum * 7) % ALPHABET.length]
        val c2 = ALPHABET[(sum + 31) % ALPHABET.length]
        return "$c1$c2"
    }

    fun validateLicense(context: Context, key: String): Boolean {
        if (key.length != 16) return false
        val deviceId = getDeviceId(context)
        val expectedKey = generateLicenseForDevice(deviceId)
        return key == expectedKey
    }

    fun generateLicenseForDevice(deviceId: String): String {
        val salt = "CYBER_BROTHER_PRIVACY_PROTECT"
        val raw = deviceId + salt + SECRET_ALPHA
        return hashString(raw).substring(5, 21).uppercase()
    }

    private fun encodeToBase32(hex: String): String {
        var sb = StringBuilder()
        // Convert hex chunks to numeric values and map to ALPHABET
        for (i in 0 until hex.length step 2) {
            val num = hex.substring(i, i + 2).toInt(16)
            sb.append(ALPHABET[num % ALPHABET.length])
        }
        return sb.toString()
    }

    private fun hashString(input: String): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(input.toByteArray(StandardCharsets.UTF_8))
        return bytes.joinToString("") { "%02x".format(it) }
    }
}
