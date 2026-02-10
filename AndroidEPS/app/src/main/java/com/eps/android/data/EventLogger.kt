package com.eps.android.data

import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventLogger @Inject constructor(
    private val threatEventDao: ThreatEventDao
) {
    suspend fun logThreat(type: String, severity: String, source: String, details: String) {
        val event = ThreatEvent(type = type, severity = severity, source = source, details = details)
        threatEventDao.insertEvent(event)
        Timber.w("🚨 SECURITY ALERT: [$severity] $type at $source")
    }

    suspend fun logInfo(message: String) {
        Timber.i("ℹ️ HACKDEFENDER: $message")
        threatEventDao.insertEvent(
            ThreatEvent(type = "ACTIVITY", severity = "INFO", source = "System", details = message)
        )
    }
}
