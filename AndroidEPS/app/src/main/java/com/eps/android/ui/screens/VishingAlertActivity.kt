package com.eps.android.ui.screens

import android.media.RingtoneManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eps.android.ui.theme.CyberBrotherTheme
import com.eps.android.ui.theme.LaserRed
import com.eps.android.ui.theme.VoidBlack
import kotlinx.coroutines.delay

class VishingAlertActivity : ComponentActivity() {
    
    private var vibrator: android.os.Vibrator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Make it full screen and stay on top
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true)
            setTurnScreenOn(true)
        }

        // --- VIBRATION LOGIC ---
        vibrator = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            val vibratorManager = getSystemService(android.content.Context.VIBRATOR_MANAGER_SERVICE) as android.os.VibratorManager
            vibratorManager.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            getSystemService(android.content.Context.VIBRATOR_SERVICE) as android.os.Vibrator
        }

        val pattern = longArrayOf(0, 500, 200, 500, 200, 800)
        if (vibrator?.hasVibrator() == true) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                vibrator?.vibrate(android.os.VibrationEffect.createWaveform(pattern, 0))
            } else {
                @Suppress("DEPRECATION")
                vibrator?.vibrate(pattern, 0)
            }
        }
        
        // Play Alarm Sound at MAX volume
        val audioManager = getSystemService(android.content.Context.AUDIO_SERVICE) as android.media.AudioManager
        
        // Force Max Volume
        val maxVolume = audioManager.getStreamMaxVolume(android.media.AudioManager.STREAM_ALARM)
        audioManager.setStreamVolume(android.media.AudioManager.STREAM_ALARM, maxVolume, 0)

        val alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        val ringtone = RingtoneManager.getRingtone(applicationContext, alarmUri)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
            ringtone.isLooping = true
        }
        ringtone.play()

        setContent {
            CyberBrotherTheme {
                // CAP FONT SCALE for Emergency Screen
                val density = androidx.compose.ui.platform.LocalDensity.current
                val cappedDensity = remember(density) {
                    androidx.compose.ui.unit.Density(
                        density = density.density,
                        fontScale = density.fontScale.coerceAtMost(1.3f)
                    )
                }

                CompositionLocalProvider(androidx.compose.ui.platform.LocalDensity provides cappedDensity) {
                    VishingAlertScreen(
                        sender = intent.getStringExtra("sender") ?: "Noma'lum",
                        reason = intent.getStringExtra("reason") ?: "Qo'ng'iroq paytida SMS-kod keldi!\nBu odam sizni aldayapti!",
                        onClose = {
                            ringtone.stop()
                            vibrator?.cancel()
                            finish()
                        }
                    )
                }
            }
        }
    }

    override fun onDestroy() {
        vibrator?.cancel()
        super.onDestroy()
    }
}

@Composable
fun VishingAlertScreen(sender: String, reason: String, onClose: () -> Unit) {
    val infiniteTransition = rememberInfiniteTransition(label = "panic")
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(400, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "flash"
    )
    
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(400, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        // Flashing Background
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            LaserRed.copy(alpha = alpha * 0.8f),
                            Color.Black
                        )
                    )
                )
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(24.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .size(120.dp)
                    .scale(scale)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "DIQQAT! JINOYATCHI!",
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White,
                textAlign = TextAlign.Center,
                lineHeight = 38.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Surface(
                color = Color.White.copy(alpha = 0.1f),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.border(2.dp, Color.White, RoundedCornerShape(16.dp))
            ) {
                Text(
                    text = reason,
                    color = Color.White,
                    modifier = Modifier.padding(16.dp),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "KODNI MUTLAQO AYTMANG!",
                fontSize = 24.sp,
                color = Color.Yellow,
                fontWeight = FontWeight.Black,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(60.dp))

            Button(
                onClick = onClose,
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp),
                shape = RoundedCornerShape(32.dp)
            ) {
                Text(
                    text = "TUSHUNDIM, O'CHIRISH",
                    color = Color.Black,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 18.sp
                )
            }
        }
    }
}
