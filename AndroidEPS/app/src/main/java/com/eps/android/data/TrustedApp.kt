package com.eps.android.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trusted_apps")
data class TrustedApp(
    @PrimaryKey
    val packageName: String,
    val addedAt: Long = System.currentTimeMillis()
)
