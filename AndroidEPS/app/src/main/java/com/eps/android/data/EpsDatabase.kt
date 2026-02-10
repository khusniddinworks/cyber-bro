package com.eps.android.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ThreatEvent::class, BlacklistedDomain::class], version = 2, exportSchema = false)
abstract class EpsDatabase : RoomDatabase() {
    abstract fun threatEventDao(): ThreatEventDao
    abstract fun blacklistDao(): BlacklistDao
}
