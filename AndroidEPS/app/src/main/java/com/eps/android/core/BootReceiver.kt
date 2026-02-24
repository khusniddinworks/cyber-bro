package com.eps.android.core

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import timber.log.Timber

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            Timber.i("Boot completion detected. Restarting HACKDEFENDER services.")
            
            // 1. Start core monitoring service
            val serviceIntent = Intent(context, EpsMonitoringService::class.java)
            context.startForegroundService(serviceIntent)
            

        }
    }
}
