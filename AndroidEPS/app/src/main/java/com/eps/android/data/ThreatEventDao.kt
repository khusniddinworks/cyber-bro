package com.eps.android.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ThreatEventDao {
    @Insert
    suspend fun insertEvent(event: ThreatEvent)

    @Query("SELECT * FROM threat_events ORDER BY timestamp DESC")
    fun getAllEvents(): Flow<List<ThreatEvent>>

    @Query("SELECT * FROM threat_events ORDER BY timestamp DESC LIMIT :limit")
    suspend fun getRecentEvents(limit: Int): List<ThreatEvent>

    @Query("SELECT COUNT(*) FROM threat_events WHERE severity IN ('HIGH', 'CRITICAL')")
    fun getCriticalThreatCount(): Flow<Int>

    @Query("DELETE FROM threat_events")
    suspend fun clearAll()
}
