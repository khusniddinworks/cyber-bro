package com.eps.android.data

import androidx.room.*

@Dao
interface UrlScanCacheDao {
    @Query("SELECT * FROM url_scan_cache WHERE url = :url LIMIT 1")
    suspend fun getCacheByUrl(url: String): UrlScanCache?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCache(cache: UrlScanCache)

    @Query("DELETE FROM url_scan_cache WHERE timestamp < :expiryTime")
    suspend fun clearOldCache(expiryTime: Long)
}
