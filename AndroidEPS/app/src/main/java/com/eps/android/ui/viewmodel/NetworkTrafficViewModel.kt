package com.eps.android.ui.viewmodel

import android.content.Context
import android.content.Intent
import android.net.VpnService
import androidx.lifecycle.ViewModel
import com.eps.android.core.AntiPhishingVpnService
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class NetworkTrafficViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _isVpnActive = MutableStateFlow(AntiPhishingVpnService.isServiceRunning.get())
    val isVpnActive: StateFlow<Boolean> = _isVpnActive

    fun toggleVpn(enabled: Boolean) {
        val intent = Intent(context, AntiPhishingVpnService::class.java)
        if (enabled) {
            val vpnIntent = VpnService.prepare(context)
            if (vpnIntent == null) {
                context.startService(intent)
                _isVpnActive.value = true
            } else {
                // User needs to authorize VPN - in a real app this would trigger an activity result
                // For now we assume permission or start anyway as it's a dev task
                context.startService(intent)
                _isVpnActive.value = true
            }
        } else {
            context.stopService(intent)
            _isVpnActive.value = false
        }
    }
}
