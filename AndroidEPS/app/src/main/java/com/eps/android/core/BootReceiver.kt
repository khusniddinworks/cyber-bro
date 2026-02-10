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
            
            // 2. Start VPN Service if it was previously enabled
            // Note: We cannot start VPN directly from background on Android 10+ without user interaction
            // unless it's an "Always-on VPN". We will verify this state in the main service or prompts.
            // For now, we prepare the intent.
            val vpnIntent = Intent(context, AntiPhishingVpnService::class.java)
            try {
                context.startService(vpnIntent) 
            } catch (e: Exception) {
                Timber.e(e, "Could not start VPN service directly from boot.")
            }
        }
    }
}
