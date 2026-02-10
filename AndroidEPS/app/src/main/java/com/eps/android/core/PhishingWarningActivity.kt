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
import com.eps.android.ui.theme.HACKDEFENDERTheme

class PhishingWarningActivity : ComponentActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val mode = intent.getStringExtra("mode") ?: "phishing"
        val detectedText = intent.getStringExtra("detectedText") ?: ""
        val url = intent.getStringExtra("url") ?: "Unknown URL"
        val domain = intent.getStringExtra("domain") ?: "Unknown Domain"
        val sourceApp = intent.getStringExtra("sourceApp") ?: "Unknown App"
        
        setContent {
            HACKDEFENDERTheme {
                if (mode == "vishing") {
                    VishingWarningScreen(
                        detectedText = detectedText,
                        onGoBack = { finish() }
                    )
                } else {
                    PhishingWarningScreen(
                        url = url,
                        domain = domain,
                        sourceApp = sourceApp,
                        onGoBack = { finish() },
                        onProceedAnyway = { 
                            // User chose to proceed - add to whitelist and close
                            PhishingAccessibilityService.addToWhitelist(domain)
                            finish()
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun PhishingWarningScreen(
    url: String,
    domain: String,
    sourceApp: String,
    onGoBack: () -> Unit,
    onProceedAnyway: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xCC000000)), // Semi-transparent black
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF1A1A1A)
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Warning Icon
                Text(
                    text = "⚠️",
                    fontSize = 64.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                
                // Title
                Text(
                    text = "XAVFLI HAVOLA ANIQLANDI!",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFF3B30),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                
                // Description
                Text(
                    text = "Bu sayt sizning ma'lumotlaringizni o'g'irlashga harakat qilmoqda.",
                    fontSize = 16.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 24.dp)
                )
                
                // URL Info
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0xFF2A2A2A)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        InfoRow("Havola:", domain)
                        Spacer(modifier = Modifier.height(8.dp))
                        InfoRow("Manba:", getAppName(sourceApp))
                        Spacer(modifier = Modifier.height(8.dp))
                        InfoRow("Sabab:", detectReason(domain, url))
                    }
                }
                
                // Warning Text
                Text(
                    text = "⚡ DIQQAT: Bu saytga kirish xavfli!",
                    fontSize = 14.sp,
                    color = Color(0xFFFFCC00),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 24.dp)
                )
                
                // Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Go Back Button (Primary)
                    Button(
                        onClick = onGoBack,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF00D4AA)
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "Orqaga",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                    }
                    
                    // Proceed Anyway Button (Danger)
                    OutlinedButton(
                        onClick = onProceedAnyway,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color(0xFFFF3B30)
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "Baribir Kirish",
                            fontSize = 14.sp,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                    }
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
                Button(
                    onClick = onGoBack,
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
    return when {
        domain.matches(Regex("\\d+\\.\\d+\\.\\d+\\.\\d+")) -> "IP manzil (shubhali)"
        domain.contains("-verify") || domain.contains("-secure") -> "Soxta tekshirish sayti"
        domain.endsWith(".tk") || domain.endsWith(".ml") || domain.endsWith(".ga") -> "Xavfli domen (.tk/.ml/.ga)"
        domain.contains("payme") || domain.contains("uzcard") -> "Soxta to'lov tizimi"
        domain.contains("telegram") -> "Soxta Telegram sayti"
        url.length > 150 -> "Juda uzun URL (shubhali)"
        domain.any { it.code > 127 } -> "Homograph hujum (soxta harflar)"
        else -> "Phishing shabloni aniqlandi"
    }
}
