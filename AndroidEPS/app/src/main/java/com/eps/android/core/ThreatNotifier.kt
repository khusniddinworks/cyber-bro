package com.eps.android.core

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.app.NotificationCompat
import com.eps.android.R
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ThreatNotifier @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    private val CHANNEL_ID = "threat_alert_channel"
    private val ALERT_ID = 1001

    init {
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        val name = "Kiber-Xavf Ogohlantirishlari"
        val descriptionText = "Jiddiy xavfsizlik tahdidlari haqida bildirishnomalar"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
            enableVibration(true)
            vibrationPattern = longArrayOf(0, 500, 200, 500) // Double pulse
            enableLights(true)
            lightColor = android.graphics.Color.RED
            lockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC
        }
        notificationManager.createNotificationChannel(channel)
    }

    /**
     * Shows a high-priority notification for a detected threat
     */
    fun showThreatAlert(title: String, message: String, packageName: String? = null, filePath: String? = null) {
        val uniqueId = (packageName?.hashCode() ?: filePath?.hashCode() ?: 0) + ALERT_ID
        
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_shield)
            .setContentTitle("🛑 $title")
            .setContentText(message)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .setPriority(NotificationCompat.PRIORITY_MAX) 
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setVibrate(longArrayOf(0, 500, 250, 500))
            .setColor(android.graphics.Color.RED)
            .setAutoCancel(true)
            .setFullScreenIntent(getAlertPendingIntent(title, message, packageName ?: "", "HIGH", "Threat Detected"), true)

        // Action 1: Uninstall App
        packageName?.let {
            val uninstallUri = Uri.parse("package:$it")
            val uninstallIntent = Intent(Intent.ACTION_DELETE, uninstallUri).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            val pendingIntent = PendingIntent.getActivity(
                context, 0, uninstallIntent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )
            
            builder.addAction(
                android.R.drawable.ic_menu_delete,
                "ILOVANI O'CHIRISH",
                pendingIntent
            )
        }

        // Action 2: Delete File
        filePath?.let { path ->
            val deleteIntent = Intent(context, ThreatActionReceiver::class.java).apply {
                action = ThreatActionReceiver.ACTION_DELETE_FILE
                putExtra(ThreatActionReceiver.EXTRA_FILE_PATH, path)
                putExtra(ThreatActionReceiver.EXTRA_NOTIFICATION_ID, uniqueId)
            }
            val pendingIntent = PendingIntent.getBroadcast(
                context, path.hashCode(), deleteIntent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )

            builder.addAction(
                android.R.drawable.ic_menu_close_clear_cancel,
                "FAYLNI O'CHIRISH",
                pendingIntent
            )
        }

        // CRITICAL: Actually show the notification!
        notificationManager.notify(uniqueId, builder.build())
        Timber.w("🔔 PUSH SENT: $title")
    }

    fun showInstallAlert(appName: String, packageName: String) {
        val uniqueId = packageName.hashCode()
        
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_shield)
            .setContentTitle("Yangi Ilova Aniqlandi")
            .setContentText("$appName tekshirilmoqda...")
            .setStyle(NotificationCompat.BigTextStyle().bigText("Yangi o'rnatilgan ilova: $appName\nXavfsizlik tekshiruvi ketmoqda..."))
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setFullScreenIntent(getAlertPendingIntent(appName, "Ilova tekshirilmoqda...", packageName, "SCANNING", "New Install"), true)
            .setAutoCancel(true)

        notificationManager.notify(uniqueId, builder.build())
    }

    fun launchAlertActivity(appName: String, packageName: String, riskLevel: String, reason: String) {
        try {
            val intent = Intent(context, com.eps.android.ui.activities.ThreatAlertActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                putExtra("APP_NAME", appName)
                putExtra("PACKAGE_NAME", packageName)
                putExtra("RISK_LEVEL", riskLevel)
                putExtra("REASON", reason)
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
            // Fallback to notification if activity launch fails
            showThreatAlert("XAVFLI ILOVA!", "Ilova: $appName\nSabab: $reason", packageName)
        }
    }
    private fun getAlertPendingIntent(title: String, message: String, packageName: String, risk: String, reason: String): PendingIntent {
        val intent = Intent(context, com.eps.android.ui.activities.ThreatAlertActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            putExtra("APP_NAME", title)
            putExtra("PACKAGE_NAME", packageName)
            putExtra("RISK_LEVEL", risk)
            putExtra("REASON", message)
        }
        return PendingIntent.getActivity(
            context, 
            packageName.hashCode(), 
            intent, 
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
    }
}
