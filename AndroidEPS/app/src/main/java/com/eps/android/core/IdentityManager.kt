package com.eps.android.core

import android.content.Context
import android.os.Build
import android.provider.Settings
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.util.UUID

object IdentityManager {

    // Tasdiqlangan 50 ta slot ID-si
    private val ID_POOL = listOf(
        "RAIJ-FIK3-3LQU", "YIWI-JY0X-BV1P", "IS8B-337I-CZMK", "CJEH-MD0Z-REBN", "FOBG-U6MW-PLWZ",
        "02CP-5E3Y-L1CC", "PDO5-5O7J-5YVP", "X5PO-EA2Z-WQNL", "GA6O-802G-7TZ8", "L1V3-XQ9Z-10U9",
        "UNV5-U05U-7OBK", "D41C-7KT7-HJYD", "Z1LJ-W8D2-D8CN", "0GE7-J78N-P1HQ", "HLEZ-LBQN-LOAF",
        "0A10-S2DX-8HAE", "R2S2-FUAR-DR4F", "JZE2-3Q6B-V365", "25ZV-4L1I-LIMR", "XLQ3-IXIN-VCHV",
        "RCZ0-JYTX-R012", "PBI6-JQSJ-I3BM", "55WN-Q0OM-9ARZ", "VG4U-QL12-XLDJ", "3Z1X-469R-ANMJ",
        "XF13-BDPX-QBTL", "8M7O-60YA-XJVR", "6RT5-8GK9-PJPG", "QUT9-YY7I-DSKM", "JCJQ-YMBX-HPEK",
        "J415-WC9B-HT40", "YOTI-5CBZ-V35U", "RK3N-IPQX-2WNR", "T7FA-B8FZ-16ZH", "UME0-A5TT-99GN",
        "U33A-MMMO-NJEC", "Q7TS-HEL8-RC90", "1BNV-GA1T-32DC", "O1OG-CEJN-6WUY", "M30Y-W2MX-B1Z4",
        "C0N5-XCRN-97BK", "O8PE-WFFD-Q99T", "TK3X-XADK-XH8E", "62YR-WMOU-N4HH", "7ERU-ZEJB-5WRT",
        "QXR4-42E3-3A45", "JDIX-G7H4-84BL", "71EY-6JRW-3ZW9", "KQT0-E5RF-VL31", "NNU7-89OJ-MPSS"
    )

    /**
     * Qurilma uchun mavjud 10 ta slotdan birini tanlaydi (Deterministic Mapping).
     * Internetsiz ham bir xil qurilmada bir xil ID chiqadi.
     */
    fun getDeviceId(context: Context): String {
        val androidId = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID) ?: "UNKNOWN"
        val hardwareSignature = Build.BOARD + Build.BRAND + Build.MANUFACTURER + Build.MODEL
        
        // Qurilma fingerprintidan hash olamiz
        val seed = (androidId + hardwareSignature).hashCode()
        
        // Hashni 10 ta ID pooliga moslaymiz (Modulo)
        val index = if (seed < 0) (seed * -1) % ID_POOL.size else seed % ID_POOL.size
        
        return ID_POOL[index]
    }

    /**
     * Validates a license key.
     * Logic: A simple hash check based on Device ID + Salt (Secret).
     * In production, this would use Asymmetric Crypto (Public/Private Key).
     */
    fun validateLicense(context: Context, key: String): Boolean {
        if (key.length != 16) return false
        
        val deviceId = getDeviceId(context)
        val expectedKey = generateLicenseForDevice(deviceId)
        
        return key == expectedKey
    }

    /**
     * SIMULATION: This generates what the valid key SHOULD be.
     * In reality, this logic exists ONLY on the Telegram Bot (Server-side).
     * We keep it here for testing purposes only.
     */
    fun generateLicenseForDevice(deviceId: String): String {
        val salt = "CYBER_BROTHER_SECRET_2026"
        val raw = deviceId + salt
        return hashString(raw).substring(0, 16).uppercase()
    }

    private fun hashString(input: String): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(input.toByteArray(StandardCharsets.UTF_8))
        return bytes.joinToString("") { "%02x".format(it) }
    }
}
