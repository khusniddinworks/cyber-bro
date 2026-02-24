package com.eps.android.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ThreatEvent::class, BlacklistedDomain::class, UrlScanCache::class, TrustedApp::class], version = 4, exportSchema = false)
abstract class EpsDatabase : RoomDatabase() {
    abstract fun threatEventDao(): ThreatEventDao
    abstract fun blacklistDao(): BlacklistDao
    abstract fun urlScanCacheDao(): UrlScanCacheDao
    abstract fun trustedAppDao(): TrustedAppDao
}
