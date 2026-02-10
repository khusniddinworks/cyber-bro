package com.eps.android

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.eps.android.MainActivity
import com.eps.android.core.EpsMonitoringService
import com.eps.android.core.EpsWorker
import com.eps.android.ui.screens.MainScaffold
import com.eps.android.ui.theme.HACKDEFENDERTheme
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @javax.inject.Inject lateinit var blacklistDao: com.eps.android.data.BlacklistDao

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        // Handle results if needed
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        applySavedLocale()
        
        handleIntent(intent)
        
        // Android 13+ Notification Permission Request
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissionLauncher.launch(arrayOf(
                Manifest.permission.POST_NOTIFICATIONS,
                Manifest.permission.RECEIVE_SMS
            ))
        }

        // Start core monitoring service
        startForegroundService(Intent(this, EpsMonitoringService::class.java))
        
        // Schedule background worker
        scheduleBackgroundTasks()

        setContent {
            val sharedPrefs = remember { getSharedPreferences("settings", MODE_PRIVATE) }
            var isDarkTheme by remember { 
                mutableStateOf(sharedPrefs.getBoolean("dark_theme", true)) 
            }
            
            HACKDEFENDERTheme(useDarkTheme = isDarkTheme) {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    var showOnboarding by remember { mutableStateOf(checkFirstRun()) }
                    
                    if (showOnboarding) {
                        com.eps.android.ui.screens.OnboardingScreen(
                            onLanguageSelected = { lang ->
                                setAppLocale(lang)
                            },
                            onFinish = {
                                markOnboardingComplete()
                                showOnboarding = false
                                checkManageExternalStorage()
                            }
                        )
                    } else {
                        MainScaffold()
                    }
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent?) {
        val action = intent?.action
        val data = intent?.data
        
        if (action == Intent.ACTION_VIEW && data != null) {
            val url = data.toString()
            val domain = data.host ?: ""
            
            lifecycleScope.launch(Dispatchers.IO) {
                val isBlacklisted = blacklistDao.isBlacklisted(domain) > 0
                
                withContext(Dispatchers.Main) {
                    if (isBlacklisted) {
                        val warningIntent = Intent(this@MainActivity, com.eps.android.core.PhishingWarningActivity::class.java).apply {
                            putExtra("url", url)
                            putExtra("domain", domain)
                            putExtra("sourceApp", "External Link")
                        }
                        startActivity(warningIntent)
                        finish()
                    } else {
                        // For safe links, we could redirect to a real browser
                        // or just log and let the app continue.
                        Timber.i("Intercepted safe URL: $url")
                    }
                }
            }
        }
    }

    private fun applySavedLocale() {
        // AppCompatDelegate handles persistence automatically if configured or we can manually sync
        val lang = getSharedPreferences("settings", MODE_PRIVATE).getString("app_lang", null)
        lang?.let {
            val appLocale: LocaleListCompat = LocaleListCompat.forLanguageTags(it)
            AppCompatDelegate.setApplicationLocales(appLocale)
        }
    }

    private fun setAppLocale(lang: String) {
        getSharedPreferences("settings", MODE_PRIVATE).edit().putString("app_lang", lang).apply()
        val appLocale: LocaleListCompat = LocaleListCompat.forLanguageTags(lang)
        AppCompatDelegate.setApplicationLocales(appLocale)
    }

    private fun checkFirstRun(): Boolean {
        val prefs = getSharedPreferences("app_prefs", MODE_PRIVATE)
        return prefs.getBoolean("is_first_run", true)
    }

    private fun markOnboardingComplete() {
        val prefs = getSharedPreferences("app_prefs", MODE_PRIVATE)
        prefs.edit().putBoolean("is_first_run", false).apply()
    }
    
    private fun checkManageExternalStorage() {
        // Special check for MANAGE_EXTERNAL_STORAGE (Android 11+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION).apply {
                    data = Uri.parse("package:${packageName}")
                }
                startActivity(intent)
            }
        }
    }

    private fun scheduleBackgroundTasks() {
        val constraints = Constraints.Builder()
            .setRequiresCharging(false)
            .setRequiresBatteryNotLow(true)
            .build()

        val workRequest = PeriodicWorkRequestBuilder<EpsWorker>(6, TimeUnit.HOURS)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "eps_background_scan",
            androidx.work.ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }
}
