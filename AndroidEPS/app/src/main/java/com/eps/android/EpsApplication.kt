package com.eps.android

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class EpsApplication : Application(), Configuration.Provider {

    @Inject lateinit var workerFactory: HiltWorkerFactory

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        
        // --- BATTERY & PERFORMANCE OPTIMIZATION ---
        // Schedule heavy AI scans ONLY when:
        // 1. Device is CHARGING
        // 2. Device is IDLE (not in use)
        // 3. Battery is not low
        
        val constraints = androidx.work.Constraints.Builder()
            .setRequiresCharging(true)
            .setRequiresDeviceIdle(true) // Android 6.0+
            .setRequiresBatteryNotLow(true)
            .build()
            
        val heavyScanRequest = androidx.work.PeriodicWorkRequestBuilder<com.eps.android.core.EpsWorker>(
            24, java.util.concurrent.TimeUnit.HOURS // Run once a day
        )
            .setConstraints(constraints)
            .build()
            
        androidx.work.WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "DailySmartScan",
            androidx.work.ExistingPeriodicWorkPolicy.KEEP,
            heavyScanRequest
        )
    }
}
