package com.eps.android.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "threat_events")
data class ThreatEvent(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val timestamp: Long = System.currentTimeMillis(),
    val type: String, // e.g., "FILE_GUARD", "APP_AUDIT"
    val severity: String, // "LOW", "MEDIUM", "HIGH", "CRITICAL"
    val source: String, // e.g., file path or package name
    val details: String,
    val isResolved: Boolean = false
)
