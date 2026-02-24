package com.eps.android.ui.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eps.android.analysis.network.UrlScanRepository
import com.eps.android.ui.theme.CyberBrotherTheme
import com.eps.android.ui.theme.LaserRed
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class UrlGuardActivity : ComponentActivity() {

    @Inject
    lateinit var urlScanRepository: UrlScanRepository

    // Trusted domains — pass through immediately without scanning
    private val trustedDomains = setOf(
        "t.me", "telegram.org", "telegram.me", "web.telegram.org",
        "instagram.com", "facebook.com", "fb.com", "messenger.com",
        "twitter.com", "x.com", "linkedin.com", "tiktok.com",
        "whatsapp.com", "web.whatsapp.com", "signal.org",
        "youtube.com", "youtu.be", "spotify.com", "netflix.com",
        "google.com", "google.uz", "google.ru", "yandex.ru", "yandex.uz",
        "apple.com", "microsoft.com", "play.google.com", "bing.com",
        "wikipedia.org", "github.com", "stackoverflow.com", "reddit.com",
        "gov.uz", "edu.uz", "ziyonet.uz", "kun.uz", "daryo.uz",
        "gazeta.uz", "spot.uz", "norma.uz", "pivot.uz",
        "click.uz", "payme.uz", "uzum.uz", "olx.uz"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val targetUrl = intent.data?.toString() ?: intent.getStringExtra("url") ?: ""
        val isAutoScan = intent.getBooleanOfDefault("is_auto_scan", false)

        if (targetUrl.isBlank()) {
            finishAndRemoveTask()
            return
        }

        // Quick check: if trusted domain, skip scan and open in browser immediately
        val domain = try {
            val parsed = Uri.parse(targetUrl)
            if (parsed.host != null) {
                parsed.host!!.lowercase()
            } else {
                // URL might be without scheme, try adding one
                Uri.parse("https://$targetUrl").host?.lowercase() ?: ""
            }
        } catch (_: Exception) { "" }

        val isTrusted = trustedDomains.contains(domain) || 
            trustedDomains.any { domain.endsWith(".$it") } ||
            com.eps.android.core.PhishingAccessibilityService.tempWhitelist.contains(domain)

        if (isTrusted) {
            openInBrowser(targetUrl)
            return
        }

        setContent {
            CyberBrotherTheme {
                UrlGuardScreen(
                    url = targetUrl,
                    isAutoScan = isAutoScan,
                    onVerified = { verifiedUrl ->
                        openInBrowser(verifiedUrl)
                    },
                    onBack = { 
                        finishAndRemoveTask()
                    },
                    urlScanRepository = urlScanRepository
                )
            }
        }
    }

    private fun Intent.getBooleanOfDefault(key: String, default: Boolean): Boolean {
        return if (hasExtra(key)) getBooleanExtra(key, default) else default
    }

    private fun openInBrowser(url: String) {
        if (url.isBlank()) {
            finishAndRemoveTask()
            return
        }
        
        // Ensure URL has a valid scheme
        val safeUrl = when {
            url.startsWith("http://") || url.startsWith("https://") -> url
            url.startsWith("//") -> "https:$url"
            else -> "https://$url"
        }
        
        val uri = try {
            Uri.parse(safeUrl)
        } catch (_: Exception) {
            finishAndRemoveTask()
            return
        }
        
        val browserIntent = Intent(Intent.ACTION_VIEW, uri).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        
        try {
            // Try Chrome first
            browserIntent.setPackage("com.android.chrome")
            startActivity(browserIntent)
        } catch (e: Exception) {
            try {
                // Fallback to any browser
                browserIntent.setPackage(null)
                startActivity(browserIntent)
            } catch (e2: Exception) {
                // Last resort: try with ACTION_VIEW intent chooser
                try {
                    val chooser = Intent.createChooser(
                        Intent(Intent.ACTION_VIEW, uri),
                        "Brauzer tanlang"
                    ).apply {
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    }
                    startActivity(chooser)
                } catch (_: Exception) {
                    Timber.e("No browser found to open URL: $safeUrl")
                }
            }
        }
        
        finishAndRemoveTask()
    }
}

@Composable
fun UrlGuardScreen(
    url: String,
    isAutoScan: Boolean = false,
    onVerified: (String) -> Unit,
    onBack: () -> Unit,
    urlScanRepository: UrlScanRepository
) {
    var status by remember { mutableStateOf("SCANNING") } // SCANNING, SAFE, DANGEROUS, UNVERIFIED
    var maliciousCount by remember { mutableIntStateOf(0) }
    var scannerCount by remember { mutableIntStateOf(0) }
    var isHeuristic by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    var screenshotUrl by remember { mutableStateOf<String?>(null) }

    suspend fun scanUrl(
        repository: UrlScanRepository, 
        url: String, 
        setStatus: (String) -> Unit,
        setMalicious: (Int) -> Unit,
        setTotal: (Int) -> Unit,
        setIsHeuristic: (Boolean) -> Unit,
        setScreenshot: (String?) -> Unit
    ) {
        setStatus("SCANNING")
        val result = repository.checkUrl(url)
        delay(2000) // 🛡️ Aesthetic delay
        
        setIsHeuristic(result.isHeuristicResult)
        setScreenshot(result.screenshotUrl)
        
        if (result.isUnknown) {
            setStatus("UNVERIFIED")
        } else if (result.isSecure) {
            setStatus("SAFE")
            delay(1000)
            onVerified(url)
        } else {
            setStatus("DANGEROUS")
            setMalicious(result.maliciousCount)
            setTotal(result.scannerCount)
        }
    }

    LaunchedEffect(url) {
        if (url.isEmpty()) return@LaunchedEffect
        scanUrl(urlScanRepository, url, 
            { s: String -> status = s }, 
            { m: Int -> maliciousCount = m }, 
            { t: Int -> scannerCount = t }, 
            { h: Boolean -> isHeuristic = h }, 
            { sc: String? -> screenshotUrl = sc }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(24.dp)
        ) {
            when (status) {
                "SCANNING" -> ScanningView(url)
                "SAFE" -> SafeView()
                "UNVERIFIED" -> UnverifiedView(
                    isRateLimited = false,
                    onBack = onBack, 
                    onProceed = {
                        // Add to whitelist so it doesn't get blocked again
                        try {
                            val d = android.net.Uri.parse(url).host?.lowercase()
                                ?: android.net.Uri.parse("https://$url").host?.lowercase() ?: ""
                            if (d.isNotEmpty()) com.eps.android.core.PhishingAccessibilityService.addToWhitelist(d)
                        } catch (_: Exception) {}
                        onVerified(url)
                    },
                    onRetry = {
                        scope.launch {
                            scanUrl(urlScanRepository, url, 
                                { s: String -> status = s }, 
                                { m: Int -> maliciousCount = m }, 
                                { t: Int -> scannerCount = t }, 
                                { h: Boolean -> isHeuristic = h }, 
                                { sc: String? -> screenshotUrl = sc }
                            )
                        }
                    }
                )
                "DANGEROUS" -> DangerousView(
                    url = url,
                    malicious = maliciousCount, 
                    total = scannerCount,
                    isHeuristic = isHeuristic,
                    screenshotUrl = screenshotUrl,
                    onBack = onBack,
                    onProceed = {
                        // Add to whitelist so it doesn't get blocked again
                        try {
                            val d = android.net.Uri.parse(url).host?.lowercase()
                                ?: android.net.Uri.parse("https://$url").host?.lowercase() ?: ""
                            if (d.isNotEmpty()) com.eps.android.core.PhishingAccessibilityService.addToWhitelist(d)
                        } catch (_: Exception) {}
                        onVerified(url)
                    }
                )
            }
        }
    }
}

@Composable
fun ScanningView(url: String) {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(tween(2500, easing = LinearEasing))
    )
    
    val pulse by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.3f,
        animationSpec = infiniteRepeatable(tween(1500, easing = FastOutSlowInEasing), repeatMode = RepeatMode.Reverse)
    )

    Box(contentAlignment = Alignment.Center, modifier = Modifier.size(280.dp)) {
        // Outer Rings
        Box(modifier = Modifier.size(240.dp).border(2.dp, Brush.sweepGradient(listOf(Color.Cyan, Color.Transparent, Color.Cyan)), CircleShape).rotate(rotation))
        Box(modifier = Modifier.size(200.dp).border(1.dp, Color.Cyan.copy(alpha = 0.3f), CircleShape).rotate(-rotation * 0.5f))
        
        // Inner Glow
        Box(
            modifier = Modifier
                .size(140.dp)
                .background(Color(0xFF00E5FF).copy(alpha = 0.1f), CircleShape)
                .blur(40.dp * pulse)
        )
        
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = Icons.Default.Shield,
                contentDescription = null,
                tint = Color(0xFF00E5FF),
                modifier = Modifier.size(80.dp).scale(pulse)
            )
            // SCANNING text inside orb
            Text(
                "SCANNING", 
                color = Color(0xFF00E5FF), 
                fontWeight = FontWeight.Black, 
                fontSize = 12.sp, 
                letterSpacing = 2.sp,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }

    Spacer(modifier = Modifier.height(48.dp))

    Text(
        text = "TIZIM HAVOLANI TEKSHIRMOQDA",
        fontSize = 22.sp,
        fontWeight = FontWeight.ExtraBold,
        color = Color.White,
        textAlign = TextAlign.Center
    )

    Text(
        text = "CYBER BROTHER AI GATEKEEPER",
        fontSize = 12.sp,
        fontWeight = FontWeight.Light,
        color = Color.Cyan.copy(alpha = 0.7f),
        letterSpacing = 4.sp,
        modifier = Modifier.padding(top = 8.dp)
    )

    Spacer(modifier = Modifier.height(32.dp))

    Text(
        text = "UrlScan Cloud va AI algoritmlar\nyordamida chuqur tahlil o'tkazilmoqda...",
        textAlign = TextAlign.Center,
        color = Color.Gray,
        fontSize = 14.sp,
        lineHeight = 20.sp
    )

    Spacer(modifier = Modifier.height(48.dp))

    Surface(
        color = Color(0xFF111111),
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(alpha = 0.1f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.Lock, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = url,
                color = Color.LightGray,
                fontSize = 13.sp,
                maxLines = 1,
                overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun SafeView() {
    Icon(
        imageVector = Icons.Default.Shield,
        contentDescription = null,
        tint = Color(0xFF00FFCC),
        modifier = Modifier.size(100.dp)
    )
    Spacer(modifier = Modifier.height(24.dp))
    Text("HAVOLA XAVFSIZ", fontSize = 28.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFF00FFCC))
    Text("Biroz kutib turing...", color = Color.Gray, fontSize = 16.sp)
}

@Composable
fun DangerousView(
    url: String,
    malicious: Int, 
    total: Int,
    isHeuristic: Boolean,
    screenshotUrl: String?,
    onBack: () -> Unit,
    onProceed: () -> Unit
) {
    Icon(
        imageVector = Icons.Default.Warning,
        contentDescription = null,
        tint = LaserRed,
        modifier = Modifier.size(100.dp)
    )
    
    Spacer(modifier = Modifier.height(24.dp))
    
    Text(
        text = "XAVFLI HAVOLA!", 
        fontSize = 28.sp, 
        fontWeight = FontWeight.ExtraBold, 
        color = LaserRed, 
        textAlign = TextAlign.Center
    )
    
    Text(
        text = if (isHeuristic) "Cyber Brother AI ushbu havolada xavf aniqladi." else "Ushbu sayt ma'lumotlaringizni o'g'irlashi mumkin.",
        color = Color.White.copy(alpha = 0.8f),
        textAlign = TextAlign.Center,
        fontSize = 16.sp,
        modifier = Modifier.padding(top = 8.dp)
    )

    Spacer(modifier = Modifier.height(32.dp))
    
    Card(
        colors = CardDefaults.cardColors(containerColor = LaserRed.copy(alpha = 0.05f)),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth().border(1.dp, LaserRed.copy(alpha = 0.3f), RoundedCornerShape(16.dp))
    ) {
        Column(modifier = Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "TAHLIL NATIJASI:",
                color = LaserRed,
                fontWeight = FontWeight.Black,
                fontSize = 12.sp,
                letterSpacing = 1.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = if (isHeuristic) "Havola mantiqiy jihatdan fishing shabloniga mos keladi." else "UrlScan tahlili ushbu havolani xavfli deb belgiladi!",
                color = Color.White,
                textAlign = TextAlign.Center,
                fontSize = 15.sp,
                lineHeight = 22.sp
            )
            
            screenshotUrl?.let {
                Spacer(modifier = Modifier.height(12.dp))
                Text("Sayt skrinshoti bazada mavjud.", color = Color.Gray, fontSize = 12.sp)
            }
        }
    }

    Spacer(modifier = Modifier.height(40.dp))

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Button(
            onClick = onBack,
            modifier = Modifier.weight(1f).height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00D4AA)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Orqaga", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }
        
        OutlinedButton(
            onClick = onProceed,
            modifier = Modifier.weight(1f).height(56.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = LaserRed),
            border = androidx.compose.foundation.BorderStroke(1.dp, LaserRed),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Baribir Kirish", fontWeight = FontWeight.Bold, fontSize = 14.sp)
        }
    }
}

@Composable
fun UnverifiedView(isRateLimited: Boolean, onBack: () -> Unit, onProceed: () -> Unit, onRetry: () -> Unit) {
    Icon(
        imageVector = Icons.Default.Warning,
        contentDescription = null,
        tint = Color.Yellow,
        modifier = Modifier.size(100.dp)
    )
    Spacer(modifier = Modifier.height(24.dp))
    Text(
        text = if (isRateLimited) "TIZIM BAND" else "TEKSHIRIB BO'LMADI", 
        fontSize = 24.sp, 
        fontWeight = FontWeight.ExtraBold, 
        color = Color.Yellow,
        textAlign = TextAlign.Center
    )
    Spacer(modifier = Modifier.height(16.dp))
    Text(
        text = if (isRateLimited) "Hozirda so'rovlar juda ko'p. 1 daqiqadan so'ng qayta urinib ko'ring." 
               else "Ushbu havola uchun UrlScan hisoboti hali tayyor emas yoki internet aloqasi past.",
        color = Color.White.copy(alpha = 0.8f),
        textAlign = TextAlign.Center,
        fontSize = 14.sp
    )
    
    Spacer(modifier = Modifier.height(24.dp))
    
    Button(
        onClick = onRetry,
        modifier = Modifier.fillMaxWidth().height(50.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.1f)),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text("Qayta tekshirish", color = Color.Yellow)
    }

    Spacer(modifier = Modifier.height(32.dp))
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Button(
            onClick = onBack,
            modifier = Modifier.weight(1f).height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Orqaga", color = Color.White, fontWeight = FontWeight.Bold)
        }
        
        OutlinedButton(
            onClick = onProceed,
            modifier = Modifier.weight(1f).height(56.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Yellow),
            border = androidx.compose.foundation.BorderStroke(1.dp, Color.Yellow),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Baribir Kirish", fontWeight = FontWeight.Bold, fontSize = 14.sp)
        }
    }
}
