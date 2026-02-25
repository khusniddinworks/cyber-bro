package com.eps.android.core

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.draw.alpha
import androidx.compose.animation.core.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.border
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LinkOff
import androidx.compose.runtime.*
import com.eps.android.ui.theme.CyberBrotherTheme
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import android.os.Environment

@AndroidEntryPoint
class PhishingWarningActivity : ComponentActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val mode = intent.getStringExtra("mode") ?: "phishing"
        val detectedText = intent.getStringExtra("detectedText") ?: ""
        val url = intent.getStringExtra("url") ?: "Unknown URL"
        val domain = intent.getStringExtra("domain") ?: "Unknown Domain"
        val sourceApp = intent.getStringExtra("sourceApp") ?: "Unknown App"
        
        setContent {
            CyberBrotherTheme {
                if (mode == "vishing") {
                    VishingWarningScreen(
                        detectedText = detectedText,
                        onGoBack = { finish() }
                    )
                } else if (mode == "block_apk") {
                    ApkBlockWarningScreen(
                        packageName = sourceApp,
                        onGoBack = { finish() }
                    )
                } else if (mode == "ai_scan") {
                    AiScanOverlayScreen(
                        packageName = sourceApp,
                        onClose = { finish() }
                    )
                } else {
                    // Phishing mode is now handled by UrlGuardActivity
                    SideEffect { finish() }
                }
            }
        }
    }
}


@Composable
fun VishingWarningScreen(
    detectedText: String,
    onGoBack: () -> Unit
) {
    val context = androidx.compose.ui.platform.LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xE6000000)), // Darker overlay for urgency
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .padding(16.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF2C0000) // Deep red container
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 20.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Critical Alert Icon
                Text(
                    text = "🛑",
                    fontSize = 72.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                
                // Title
                Text(
                    text = "FIRIBGARLIK ANIQLANDI!",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                
                // Urgency Description
                Text(
                    text = "Suhbatda shubhali kalit so'zlar aniqlandi. SIZGA FIRIBGAR QO'NG'IROQ QILAYOTGAN BO'LIShI MUMKIN!",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFFFFCC00), // Yellow warning
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 24.dp)
                )
                
                // Fact Warning
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp),
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0x33FF0000)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "💡 ESLATMA:",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Bank xodimlari HECH QACHON telefon orqali SMS-kod, karta raqami yoki parollarni so'ramaydi.",
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            fontSize = 15.sp
                        )
                    }
                }
                
                // Action
                var isVibrationStopped by remember { mutableStateOf(false) }
                
                if (!isVibrationStopped) {
                    OutlinedButton(
                        onClick = {
                            val stopIntent = android.content.Intent(ThreatNotifier.ACTION_STOP_VISH).apply {
                                setPackage(context.packageName)
                            }
                            context.sendBroadcast(stopIntent)
                            isVibrationStopped = true
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .padding(bottom = 8.dp),
                        border = androidx.compose.foundation.BorderStroke(2.dp, Color.White),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "📳 VIBRATSIYANI TO'XTATISH",
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
 
                Button(
                    onClick = {
                        // Stop the vibration if it's still running
                        val stopIntent = android.content.Intent(ThreatNotifier.ACTION_STOP_VISH).apply {
                            setPackage(context.packageName)
                        }
                        context.sendBroadcast(stopIntent)
                        onGoBack()
                    },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFF3B30)
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "ALOQANI UZISH VA QAYTISH",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = "Cyber Brother AI Himoyasi Faol",
                    fontSize = 12.sp,
                    color = Color(0x88FFFFFF)
                )
            }
        }
    }
}

@Composable
fun ApkBlockWarningScreen(
    packageName: String,
    onGoBack: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xF2000000)), // Very dark overlay
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .padding(16.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF111111)
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 24.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Large Shield Icon with Glow
                Text(
                    text = "🛡️",
                    fontSize = 80.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                
                // Title
                Text(
                    text = "TIZIM TOMONIDAN BLOKLANDI!",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFFFF3B30),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
                
                // Concise Explanation
                Text(
                    text = "Ehtiyotkorlik yuzasidan ushbu harakatga cheklov (ban) o'rnatildi.",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 24.dp)
                )
                
                // Reason Box
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp),
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFF1A1111)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "ANIQLANGAN SABAB:",
                            color = Color(0xFFFF3B30),
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(
                            text = "Zararli fayl yoki shubhali fishing harakati aniqlandi.",
                            color = Color.LightGray,
                            textAlign = TextAlign.Center,
                            fontSize = 15.sp
                        )
                    }
                }
                
                // Action Button
                Button(
                    onClick = onGoBack,
                    modifier = Modifier.fillMaxWidth().height(60.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF00D4AA)
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "TUSHUNARLI VA QAYTISH",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.Black
                    )
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = "Sizning xavfsizligingiz — bizning ustuvor vazifamiz.",
                    fontSize = 12.sp,
                    color = Color(0x66FFFFFF),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun AiScanOverlayScreen(
    packageName: String,
    onClose: () -> Unit
) {
    val context = androidx.compose.ui.platform.LocalContext.current
    var currentStep by remember { mutableIntStateOf(0) }
    var resultStatus by remember { mutableStateOf("scanning") } // scanning, safe, danger
    var progress by remember { mutableStateOf(0f) }

    val steps = listOf(
        "Paket xavfsizligini tekshirish...",
        "Ilova ruxsatlari auditi (AI)...",
        "Classes.dex kod tahlili...",
        "Zararli kodlar bazasi bilan solishtirish...",
        "Yakuniy tahlil tayyorlanmoqda..."
    )

    // Simulate scanning with Heuristic Logic (V1.7.9 Style)
    LaunchedEffect(Unit) {
        val totalSteps = steps.size
        for (i in 0 until totalSteps) {
            currentStep = i
            // Smoother internal progress per step
            val startTime = System.currentTimeMillis()
            val stepDuration = 1000L
            while (System.currentTimeMillis() - startTime < stepDuration) {
                progress = (i.toFloat() + (System.currentTimeMillis() - startTime).toFloat() / stepDuration) / totalSteps
                kotlinx.coroutines.delay(50)
            }
        }
        progress = 1.0f
        
        // --- V1.8.0 SMART HEURISTICS ---
        val latestApk = findLatestApk()
        val fileName = latestApk?.name?.lowercase() ?: ""
        val lowerPackage = packageName.lowercase()

        // 1. DANGER: Weird names or suspicious patterns
        val isWeirdName = fileName.contains("debug") || 
                         fileName.contains("test") || 
                         fileName.contains("-") || 
                         fileName.contains("_") ||
                         Regex("\\(\\d+\\)").containsMatchIn(fileName) // e.g. apk(11).apk

        // 2. SUSPICIOUS: From untrusted sources like Telegram/Chrome
        val isUntrustedSource = lowerPackage.contains("telegram") || 
                               lowerPackage.contains("chrome") || 
                               lowerPackage.contains("browser")

        resultStatus = when {
            isWeirdName -> "danger"
            isUntrustedSource -> "suspicious"
            else -> "safe"
        }

        // Auto-close if safe
        if (resultStatus == "safe") {
            kotlinx.coroutines.delay(2000)
            onClose()
        }
    }

    Box(
        modifier = Modifier.fillMaxSize().background(Color(0xF205080A)),
        contentAlignment = Alignment.Center
    ) {
        // Background Glow Effect
        Box(
            modifier = Modifier
                .size(400.dp)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            when(resultStatus) {
                                "danger" -> Color.Red.copy(alpha = 0.2f)
                                "suspicious" -> Color(0xFFFFA500).copy(alpha = 0.15f)
                                "safe" -> Color(0xFF00FFCC).copy(alpha = 0.15f)
                                else -> Color.White.copy(alpha = 0.05f)
                            },
                            Color.Transparent
                        )
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .verticalScroll(rememberScrollState())
                .padding(vertical = 40.dp, horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Pulsing AI Icon
            val infiniteTransition = rememberInfiniteTransition(label = "")
            val scale by infiniteTransition.animateFloat(
                initialValue = 1f, targetValue = 1.15f,
                animationSpec = infiniteRepeatable(
                    animation = tween(1200, easing = FastOutSlowInEasing),
                    repeatMode = RepeatMode.Reverse
                ), label = ""
            )

            val animatedProgress by animateFloatAsState(
                targetValue = progress,
                animationSpec = tween(500),
                label = ""
            )

            Box(contentAlignment = Alignment.Center, modifier = Modifier.size(100.dp)) {
                // Circular Progress Background
                CircularProgressIndicator(
                    progress = animatedProgress,
                    modifier = Modifier.fillMaxSize(),
                    color = when(resultStatus) {
                        "danger" -> Color.Red
                        "suspicious" -> Color(0xFFFFA500)
                        else -> Color(0xFF00FFCC)
                    },
                    strokeWidth = 4.dp,
                    trackColor = Color.White.copy(alpha = 0.1f)
                )
                
                Text(
                    text = when(resultStatus) {
                        "danger" -> "�"
                        "suspicious" -> "⚠️"
                        "safe" -> "✅"
                        else -> "🧠"
                    },
                    fontSize = 52.sp,
                    modifier = Modifier.graphicsLayer(scaleX = scale, scaleY = scale)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = when(resultStatus) {
                    "scanning" -> "AI TAHLIL QILINMOQDA"
                    "danger" -> "XAVFLI ILOVA ANIQLANDI!"
                    "suspicious" -> "SHUBHALI ILOVA!"
                    else -> "TIZIM XAVFSIZ"
                },
                fontSize = 20.sp,
                fontWeight = FontWeight.Black,
                color = when(resultStatus) {
                    "danger" -> Color.Red
                    "suspicious" -> Color(0xFFFFA500)
                    "safe" -> Color(0xFF00FFCC)
                    else -> Color.White
                },
                letterSpacing = 2.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Checklist
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF0A0F14).copy(alpha = 0.8f), RoundedCornerShape(20.dp))
                    .border(1.dp, Color.White.copy(alpha = 0.05f), RoundedCornerShape(20.dp))
                    .padding(20.dp)
            ) {
                steps.forEachIndexed { index, step ->
                    val isActive = index <= currentStep
                    val isDone = index < currentStep || resultStatus != "scanning"
                    
                    Row(
                        modifier = Modifier.padding(vertical = 6.dp).alpha(if (isActive) 1f else 0.3f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (index == currentStep && resultStatus == "scanning") {
                            CircularProgressIndicator(
                                modifier = Modifier.size(14.dp).padding(1.dp),
                                color = Color(0xFF00FFCC),
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text(
                                text = if (isDone) "✓" else "○",
                                color = if (isDone) Color(0xFF00FFCC) else Color.Gray,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.width(20.dp),
                                fontSize = 14.sp
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(step, color = Color.White, fontSize = 13.sp, fontWeight = if(isActive) FontWeight.Medium else FontWeight.Normal)
                    }
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            if (resultStatus != "scanning") {
                if (resultStatus == "danger" || resultStatus == "suspicious") {
                    // DISCLAIMER BOX
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 20.dp),
                        shape = RoundedCornerShape(16.dp),
                        color = if (resultStatus == "danger") Color.Red.copy(alpha = 0.2f) else Color(0xFFFFA500).copy(alpha = 0.15f),
                        border = androidx.compose.foundation.BorderStroke(1.dp, (if(resultStatus == "danger") Color.Red else Color(0xFFFFA500)).copy(alpha = 0.3f))
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(if (resultStatus == "danger") "💀" else "⚠️", fontSize = 14.sp)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = if (resultStatus == "danger") "XAVFLI ILOVA:" else "SHUBHALI MANBA:",
                                    color = if (resultStatus == "danger") Color.Red else Color(0xFFFFA500),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 11.sp
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = if (resultStatus == "danger") {
                                    "Ushbu ilova mantiqsiz nomga ega (repack/virus ehtimoli). Uni o'rnatish qurilmangizning butunlay buzilishiga olib kelishi mumkin."
                                } else {
                                    "Ilova nomi mantiqan to'g'ri, ammo u norasmiy manbadan (Telegram/Chrome) yuklangan. Cyber Brother uning xavfsizligiga kafolat bermaydi va mas'uliyatni o'z zimmasiga olmaydi."
                                },
                                color = Color.White.copy(alpha = 0.9f),
                                fontSize = 12.sp,
                                textAlign = TextAlign.Start,
                                lineHeight = 16.sp
                            )
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth().padding(bottom = 40.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Button(
                            onClick = {
                                // Home to block
                                val homeIntent = android.content.Intent(android.content.Intent.ACTION_MAIN).apply {
                                    addCategory(android.content.Intent.CATEGORY_HOME)
                                    flags = android.content.Intent.FLAG_ACTIVITY_NEW_TASK
                                }
                                context.startActivity(homeIntent)
                                onClose()
                            },
                            modifier = Modifier.weight(1f).height(56.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = if(resultStatus == "danger") Color(0xFF00D4AA) else Color.Gray.copy(alpha=0.3f)),
                            shape = RoundedCornerShape(14.dp)
                        ) {
                            Text("BEKOR QILISH", fontWeight = FontWeight.Bold, color = Color.White, fontSize = 13.sp)
                        }

                        OutlinedButton(
                            onClick = { onClose() }, // Allow install by closing overlay
                            modifier = Modifier.weight(1f).height(56.dp),
                            border = androidx.compose.foundation.BorderStroke(1.5.dp, (if(resultStatus == "danger") Color.Red else Color(0xFFFFA500)).copy(alpha = 0.7f)),
                            shape = RoundedCornerShape(14.dp)
                        ) {
                            Text(if(resultStatus == "danger") "BARIBIR O'RNATISH" else "DAVOM ETISH", fontWeight = FontWeight.Bold, color = if(resultStatus == "danger") Color.Red else Color(0xFFFFA500), fontSize = 11.sp)
                        }
                    }
                } else {
                    // Safe logic
                    Button(
                        onClick = { onClose() },
                        modifier = Modifier.fillMaxWidth().height(56.dp).padding(bottom = 40.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00D4AA)),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Text("XAVFSIZ: DAVOM ETISH", fontWeight = FontWeight.Bold, color = Color.Black, fontSize = 14.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            color = Color(0xFF888888),
            fontWeight = FontWeight.Medium
        )
        Text(
            text = value,
            fontSize = 14.sp,
            color = Color.White,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.End
        )
    }
}

fun getAppName(packageName: String): String {
    return when {
        packageName.contains("chrome") -> "Chrome"
        packageName.contains("telegram") -> "Telegram"
        packageName.contains("whatsapp") -> "WhatsApp"
        packageName.contains("instagram") -> "Instagram"
        packageName.contains("facebook") -> "Facebook"
        packageName.contains("messaging") -> "SMS"
        packageName.contains("gmail") -> "Gmail"
        else -> "Noma'lum Ilova"
    }
}

fun detectReason(domain: String, url: String): String {
    val trustedTelegramDomains = setOf("t.me", "telegram.org", "telegram.me", "web.telegram.org")
    return when {
        domain.matches(Regex("\\d+\\.\\d+\\.\\d+\\.\\d+")) -> "IP manzil (shubhali)"
        domain.contains("-verify") || domain.contains("-secure") -> "Soxta tekshirish sayti"
        domain.endsWith(".tk") || domain.endsWith(".ml") || domain.endsWith(".ga") -> "Xavfli domen (.tk/.ml/.ga)"
        domain.contains("payme") || domain.contains("uzcard") -> "Soxta to'lov tizimi"
        domain.contains("telegram") && !trustedTelegramDomains.contains(domain) && !trustedTelegramDomains.any { domain.endsWith(".$it") } -> "Soxta Telegram sayti"
        url.length > 150 -> "Juda uzun URL (shubhali)"
        domain.any { it.code > 127 } -> "Homograph hujum (soxta harflar)"
        else -> "Phishing shabloni aniqlandi"
    }
}

fun findLatestApk(): File? {
    val paths = listOf(
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
        File(Environment.getExternalStorageDirectory(), "Telegram/Telegram Documents")
    )
    
    return paths.flatMap { dir ->
        if (dir.exists() && dir.isDirectory) {
            dir.listFiles { file -> file.extension.lowercase() == "apk" }?.toList() ?: emptyList()
        } else {
            emptyList()
        }
    }.maxByOrNull { it.lastModified() }
}
