package com.eps.android.core

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Environment
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.eps.android.R
import com.eps.android.data.EventLogger
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.File
import javax.inject.Inject

@AndroidEntryPoint
class EpsMonitoringService : Service() {

    @Inject
    lateinit var fileMonitor: FileMonitor
    
    @Inject
    lateinit var eventLogger: EventLogger

    private val NOTIFICATION_ID = 202
    private val CHANNEL_ID = "hackdefender_core_channel"
    private val serviceScope = CoroutineScope(Dispatchers.IO)

    private val quickScanReceiver = object : android.content.BroadcastReceiver() {
        override fun onReceive(context: android.content.Context?, intent: android.content.Intent?) {
            if (intent?.action == "com.eps.android.ACTION_QUICK_SCAN") {
                Timber.w("⚡ QUICK SCAN TRIGGERED via Broadcast")
                serviceScope.launch {
                    performManualCheck()
                }
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        Timber.i("EpsMonitoringService created")
        createNotificationChannel()
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            startForeground(
                NOTIFICATION_ID, 
                createNotification(),
                android.content.pm.ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC
            )
        } else {
            startForeground(NOTIFICATION_ID, createNotification())
        }
        
        // Register quick scan receiver
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(
                quickScanReceiver,
                android.content.IntentFilter("com.eps.android.ACTION_QUICK_SCAN"),
                android.content.Context.RECEIVER_NOT_EXPORTED
            )
        } else {
            registerReceiver(
                quickScanReceiver,
                android.content.IntentFilter("com.eps.android.ACTION_QUICK_SCAN")
            )
        }
        
        // Monitor multiple download directories for better coverage
        startMonitoringAllDownloadPaths()
        
        // Efficient Fallback: Check every 1 hour instead of 15 seconds to save battery
        serviceScope.launch {
            while (true) {
                kotlinx.coroutines.delay(3600000) // 🛡️ 1 hour fallback
                Timber.d("🕒 Hourly safety check running...")
                performManualCheck()
            }
        }

        serviceScope.launch {
            eventLogger.logInfo("HACKDEFENDER Protection Shield Initialized - Monitoring Downloads")
        }
    }

    private fun performManualCheck() {
        // Force manual re-scan of common download paths
        val possiblePaths = listOf(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath,
            "${Environment.getExternalStorageDirectory()}/Telegram/Telegram Documents"
        )
        
        for (path in possiblePaths) {
            val dir = File(path)
            if (dir.exists() && dir.canRead()) {
                val files = dir.listFiles()
                files?.forEach { file ->
                    if (file.name.endsWith(".apk", ignoreCase = true) && (System.currentTimeMillis() - file.lastModified()) < 30000) {
                        Timber.i("🎯 Quick Scan found recent APK: ${file.name}")
                        fileMonitor.analyzeIncomingFile(file)
                    }
                }
            }
        }
    }
    
    private fun startMonitoringAllDownloadPaths() {
        // Try multiple possible download paths
        val possiblePaths = listOf(
            // Standard paths
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath,
            "/sdcard/Download",
            "/storage/emulated/0/Download",
            // Telegram downloads
            "${Environment.getExternalStorageDirectory()}/Telegram/Telegram Documents",
            "/sdcard/Telegram/Telegram Documents",
            // WhatsApp
            "${Environment.getExternalStorageDirectory()}/WhatsApp/Media",
            // Chrome downloads
            "${Environment.getExternalStorageDirectory()}/Android/data/com.android.chrome/files/Download"
        )
        
        var monitoredCount = 0
        for (path in possiblePaths) {
            val dir = File(path)
            if (dir.exists() && dir.isDirectory && dir.canRead()) {
                Timber.i("✅ Starting FileMonitor for: $path")
                fileMonitor.startMonitoring(path)
                monitoredCount++
                
                serviceScope.launch {
                    eventLogger.logInfo("FileMonitor faol: $path")
                }
            } else {
                Timber.w("❌ Cannot monitor path (doesn't exist or no access): $path")
            }
        }
        
        if (monitoredCount == 0) {
            Timber.e("⚠️ WARNING: No download directories could be monitored!")
            serviceScope.launch {
                eventLogger.logInfo("⚠️ DIQQAT: Download papkaga kirish imkoni yo'q. Ruxsatlarni tekshiring!")
            }
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Timber.i("EpsMonitoringService onStartCommand")
        return START_STICKY
    }

    override fun onDestroy() {
        unregisterReceiver(quickScanReceiver)
        fileMonitor.stopMonitoring()
        Timber.i("EpsMonitoringService destroyed")
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun createNotification(): Notification {
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Cyber Brother Faol")
            .setContentText("Download papka kuzatilmoqda 🛡️")
            .setSmallIcon(R.drawable.ic_shield)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setOngoing(true)
            .build()
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            CHANNEL_ID,
            "HACKDEFENDER Protection",
            NotificationManager.IMPORTANCE_LOW
        )
        val manager = getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel)
    }
}
