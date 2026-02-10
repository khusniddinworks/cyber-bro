package com.eps.android.core

import android.content.Intent
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import timber.log.Timber

class EpsNotificationListener : NotificationListenerService() {
    
    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        val packageName = sbn?.packageName ?: return
        val extras = sbn.notification.extras
        val title = extras.getString("android.title") ?: ""
        val text = extras.getCharSequence("android.text")?.toString() ?: ""
        
        // 1. URL Phishing Detection
        if (text.contains("http://") || text.contains("https://")) {
            Timber.w("🔗 URL detected in notification from $packageName: $text")
        }

        // 2. Real-time Download Detection (Telegram, Chrome, etc.)
        val isDownloadNotification = when (packageName) {
            "org.telegram.messenger", "org.telegram.messenger.web", "org.telegram.plus" -> {
                text.contains(".apk", ignoreCase = true) || title.contains("download", ignoreCase = true)
            }
            "com.android.chrome" -> {
                text.contains("download complete", ignoreCase = true) || text.contains(".apk", ignoreCase = true)
            }
            else -> false
        }

        if (isDownloadNotification) {
            Timber.w("📥 Download detected in $packageName! Triggering immediate storage scan.")
            // Send broadcast to Service to force a quick scan of the downloads folder
            val intent = Intent("com.eps.android.ACTION_QUICK_SCAN")
            sendBroadcast(intent)
        }
    }
}
