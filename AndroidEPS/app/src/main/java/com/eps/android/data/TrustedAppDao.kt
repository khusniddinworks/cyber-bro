package com.eps.android.data

import androidx.room.*

@Dao
interface TrustedAppDao {
    @Query("SELECT * FROM trusted_apps")
    suspend fun getAllTrustedApps(): List<TrustedApp>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTrustedApp(app: TrustedApp)

    @Delete
    suspend fun removeTrustedApp(app: TrustedApp)

    @Query("SELECT EXISTS(SELECT 1 FROM trusted_apps WHERE packageName = :packageName LIMIT 1)")
    suspend fun isTrusted(packageName: String): Boolean
}
