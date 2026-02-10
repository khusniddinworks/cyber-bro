package com.eps.android.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BlacklistDao {
    @Query("SELECT * FROM blacklist")
    suspend fun getAllDomains(): List<BlacklistedDomain>

    @Query("SELECT COUNT(*) FROM blacklist WHERE domain = :domain")
    suspend fun isBlacklisted(domain: String): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(domains: List<BlacklistedDomain>)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(domain: BlacklistedDomain)
    
    @Query("DELETE FROM blacklist")
    suspend fun clearBlacklist()
}
