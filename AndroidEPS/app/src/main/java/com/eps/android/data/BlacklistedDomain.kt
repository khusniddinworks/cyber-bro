package com.eps.android.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "blacklist")
data class BlacklistedDomain(
    @PrimaryKey
    val domain: String,
    val reason: String = "Malicious",
    val addedAt: Long = System.currentTimeMillis()
)
