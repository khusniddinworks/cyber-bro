package com.eps.android.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "url_scan_cache")
data class UrlScanCache(
    @PrimaryKey
    val url: String,
    val isSecure: Boolean,
    val maliciousCount: Int = 0,
    val scannerCount: Int = 0,
    val timestamp: Long = System.currentTimeMillis()
)
