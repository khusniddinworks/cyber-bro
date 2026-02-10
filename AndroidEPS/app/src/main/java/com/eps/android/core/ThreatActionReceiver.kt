package com.eps.android.core

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.app.NotificationManager
import com.eps.android.data.EventLogger
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ThreatActionReceiver : BroadcastReceiver() {

    @Inject lateinit var eventLogger: EventLogger

    companion object {
        const val ACTION_DELETE_FILE = "com.eps.android.ACTION_DELETE_FILE"
        const val EXTRA_FILE_PATH = "extra_file_path"
        const val EXTRA_NOTIFICATION_ID = "extra_notification_id"
    }

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == ACTION_DELETE_FILE) {
            val filePath = intent.getStringExtra(EXTRA_FILE_PATH)
            val notificationId = intent.getIntExtra(EXTRA_NOTIFICATION_ID, -1)
            
            if (filePath != null) {
                val file = File(filePath)
                val fileName = file.name
                
                if (file.exists() && file.delete()) {
                    // Log success
                    CoroutineScope(Dispatchers.IO).launch {
                        eventLogger.logInfo("Xavfli fayl o'chirildi: $fileName")
                    }
                    
                    // Dismiss notification
                    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                    if (notificationId != -1) {
                        notificationManager.cancel(notificationId)
                    }
                }
            }
        }
    }
}
