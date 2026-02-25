package com.eps.android.ui.components

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eps.android.R
import com.eps.android.data.ThreatEvent
import com.eps.android.ui.theme.*
import com.eps.android.ui.viewmodel.DashboardViewModel

@Composable
fun FeatureCard(title: String, icon: ImageVector, color: Color, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(GlassSurface)
            .border(1.dp, Color.White.copy(alpha = 0.08f), RoundedCornerShape(24.dp))
            .clickable { onClick() }
            .padding(20.dp)
    ) {
        Column {
            Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(28.dp))
            Spacer(modifier = Modifier.height(12.dp))
            Text(title, color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
fun SystemStatusCard(status: DashboardViewModel.ProtectionStatus) {
    val context = LocalContext.current
    var showUnblockGuide by remember { mutableStateOf(false) }
    var showStepGuide by remember { mutableStateOf<String?>(null) } // "accessibility", "notifications"

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(Brush.verticalGradient(listOf(Color(0xFF1E2632), Color(0xFF0F1419))))
            .border(1.dp, GoldPremium.copy(alpha = 0.3f), RoundedCornerShape(24.dp))
            .padding(20.dp)
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.AutoFixHigh, contentDescription = null, tint = GoldPremium, modifier = Modifier.size(32.dp))
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "XAVFSIZLIK YORDAMCHISI",
                    color = Color.White,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 16.sp,
                    lineHeight = 20.sp
                )
            }

            Text(
                text = "Himoyani 3 ta bosqichda to'liq yoqing:",
                color = Color.Gray,
                fontSize = 11.sp,
                modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
            )

            // STEP 1: RESTRICTED SETTINGS UNBLOCK (Smart Detection)
            val isRestricted = !status.isAccessibilityActive && !status.isNotificationListenerActive

            if (isRestricted) {
                WizardStep(
                    step = "!",
                    title = "1-QADAM: Ta'qiqni yechish",
                    desc = "Android 13+ da 'Restricted settings' cheklovini olib tashlash uchun bosing.",
                    isDone = false
                ) {
                    val intent = Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = Uri.parse("package:${context.packageName}")
                    }
                    context.startActivity(intent)
                    showUnblockGuide = true
                }
            }

            // STEP 2: FISHING PROTECTION
            WizardStep(
                step = if (isRestricted) "2" else "1",
                title = "Fishingdan himoya",
                desc = if (status.isAccessibilityActive) "✅ Himoya yoqilgan" else "⚠️ Yoqish uchun bosing (Cheklovni yechish)",
                isDone = status.isAccessibilityActive,
                isEnabled = true,
                onHow = { showStepGuide = "accessibility" }
            ) {
                context.startActivity(Intent(android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS))
            }

            // STEP 3: TELEGRAM PROTECTION
            WizardStep(
                step = if (isRestricted) "3" else "2",
                title = "Xavfli fayllar filtri",
                desc = if (status.isNotificationListenerActive) "✅ Himoya yoqilgan" else "⚠️ Yoqish uchun bosing",
                isDone = status.isNotificationListenerActive,
                isEnabled = true,
                onHow = { showStepGuide = "notifications" }
            ) {
                context.startActivity(Intent(android.provider.Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS))
            }

            // EXTRA: Battery Optimization (To prevent random "bans")
            val pm = context.getSystemService(Context.POWER_SERVICE) as android.os.PowerManager
            val isIgnoringBattery = pm.isIgnoringBatteryOptimizations(context.packageName)
            if (!isIgnoringBattery) {
                WizardStep(
                    step = "?",
                    title = "Tizim barqarorligi",
                    desc = "Ilova o'chib qolmasligi uchun cheklovni olib tashlang.",
                    isDone = false
                ) {
                    val intent = Intent(android.provider.Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS).apply {
                        data = Uri.parse("package:${context.packageName}")
                    }
                    context.startActivity(intent)
                }
            }

            // NEW: Auto-Revoke (The reason why permissions vanish)
            if (!status.isAutoRevokeDisabled && android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
                WizardStep(
                    step = "!",
                    title = "Doimiy himoyani saqlash",
                    desc = "Tizim ruhsatlarni o'chirib yubormasligi uchun buni sozlang.",
                    isDone = false
                ) {
                    val intent = Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = Uri.parse("package:${context.packageName}")
                    }
                    context.startActivity(intent)
                    showUnblockGuide = true
                }
            }
        }
    }

    if (showUnblockGuide) {
        AlertDialog(
            onDismissRequest = { showUnblockGuide = false },
            title = { Text("Ruhsatlar uchib ketmasligi uchun", color = Color.White, fontWeight = FontWeight.Bold) },
            text = {
                Column {
                    Text(
                        "Android tizimi uzoq vaqt ishlatilmagan ilovalarning ruhsatlarini o'zi o'chirib yuboradi.\n\n" +
                                "1. Sahifani pastiga tushiring.\n" +
                                "2. 'Ishlatilmaganda ruxsatlarni o'chirish' (Remove permissions if app is unused) tugmasini O'CHIRIB qo'ying.\n\n" +
                                "3. Shuningdek, 'Avtomatik ishga tushirish' (Auto-start) yozuvini ko'rsangiz, uni YOQIB qo'ying.",
                        color = Color.LightGray
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("💡 Bu Cyber Brother doimiy ishlashi uchun juda muhim.", color = GoldPremium, fontSize = 12.sp)
                }
            },
            confirmButton = {
                Button(
                    onClick = { showUnblockGuide = false },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF00D4AA)
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "OK, TAYYORMAN",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.Black
                    )
                }
            },
            containerColor = Color(0xFF111111),
            shape = RoundedCornerShape(24.dp)
        )
    }

    if (showStepGuide != null) {
        AlertDialog(
            onDismissRequest = { showStepGuide = null },
            title = { Text(if (showStepGuide == "accessibility") "Fishingdan himoyani yoqish" else "Telegram himoyasini yoqish", color = Color.White, fontWeight = FontWeight.Bold) },
            text = {
                Column {
                    Text(
                        if (showStepGuide == "accessibility")
                            "1. Sozlamalardan 'Downloaded Services' yoki 'O'rnatilgan xizmatlar'ni toping.\n\n" +
                                    "2. 'Cyber Brother' ni tanlang.\n\n" +
                                    "3. 'Use Cyber Brother' kalitini yoqing."
                        else
                            "1. Ro'yxatdan 'Cyber Brother'ni toping.\n\n" +
                                    "2. 'Allow notification access' (Xabarlarga ruxsat berish) kalitini yoqing.",
                        color = Color.LightGray
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = { showStepGuide = null },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00D4AA)),
                    shape = RoundedCornerShape(12.dp)
                ) { Text("TUSHUNARLI", color = Color.Black, fontWeight = FontWeight.Bold) }
            },
            containerColor = Color(0xFF111111),
            shape = RoundedCornerShape(24.dp)
        )
    }
}

@Composable
fun WizardStep(step: String, title: String, desc: String, isDone: Boolean, isEnabled: Boolean = true, onHow: () -> Unit = {}, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
            .alpha(if (isDone) 0.5f else 1f)
            .clickable(enabled = !isDone) { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .background(if (isDone) Color.Green.copy(alpha = 0.2f) else GoldPremium.copy(alpha = 0.1f), CircleShape)
                .border(1.dp, if (isDone) Color.Green else GoldPremium, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(if (isDone) "✓" else step, color = if (isDone) Color.Green else GoldPremium, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(title, color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            Text(desc, color = Color.Gray, fontSize = 10.sp, lineHeight = 14.sp)
        }

        if (!isDone && isEnabled) {
            Text(
                "Qanday?",
                color = GoldPremium,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(end = 8.dp).clickable { onHow() }
            )
            Icon(Icons.Default.ArrowForwardIos, contentDescription = null, tint = GoldPremium, modifier = Modifier.size(14.dp))
        }
    }
}

@Composable
fun NotificationDialog(dailyReport: String, logs: List<ThreatEvent>, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.NotificationsActive, contentDescription = null, tint = GoldPremium)
                Spacer(modifier = Modifier.width(12.dp))
                Text("XAVFSIZLIK HISOBOTI", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                // Daily Summary Card
                Card(
                    colors = CardDefaults.cardColors(containerColor = GoldPremium.copy(alpha = 0.1f)),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth().border(1.dp, GoldPremium.copy(alpha = 0.2f), RoundedCornerShape(16.dp))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("KUNLIK HISOBOT", color = GoldPremium, fontSize = 10.sp, fontWeight = FontWeight.Black)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(dailyReport, color = Color.White, fontSize = 13.sp, lineHeight = 18.sp)
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text("OXIRGI HARAKATLAR", color = Color.Gray, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(12.dp))

                if (logs.isEmpty()) {
                    Text("Hozircha ro'yxat bo'sh", color = Color.DarkGray, fontSize = 12.sp, modifier = Modifier.padding(vertical = 20.dp).align(Alignment.CenterHorizontally))
                } else {
                    Box(modifier = Modifier.heightIn(max = 300.dp)) {
                        LazyColumn {
                            items(logs) { log ->
                                ActivityLogItem(log)
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("YOPISH", color = GoldPremium, fontWeight = FontWeight.Bold)
            }
        },
        containerColor = Color(0xFF0D1117),
        shape = RoundedCornerShape(28.dp)
    )
}

@Composable
fun ActivityLogItem(log: ThreatEvent) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(GlassSurface.copy(alpha = 0.3f))
            .border(0.5.dp, Color.White.copy(alpha = 0.05f), RoundedCornerShape(16.dp))
            .padding(12.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(
                        if (log.severity == "HIGH") Color.Red.copy(alpha = 0.1f)
                        else Color.Green.copy(alpha = 0.1f),
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (log.severity == "HIGH") Icons.Default.GppBad else Icons.Default.GppGood,
                    contentDescription = null,
                    tint = if (log.severity == "HIGH") Color.Red else Color(0xFF00FFCC),
                    modifier = Modifier.size(18.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(log.type, color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                Text(log.details, color = Color.Gray, fontSize = 10.sp, maxLines = 1)
            }
            Spacer(modifier = Modifier.weight(1f))
            val sdf = java.text.SimpleDateFormat("HH:mm", java.util.Locale.getDefault())
            Text(sdf.format(java.util.Date(log.timestamp)), color = Color.DarkGray, fontSize = 10.sp)
        }
    }
}

@Composable
fun VishingAlertCard(isGeneric: Boolean = false) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(Color(0xFF2C1616))
            .border(1.dp, Color.Red.copy(alpha=0.3f), RoundedCornerShape(24.dp))
            .padding(20.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier.size(48.dp).background(Color.Red.copy(alpha=0.15f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Warning, contentDescription = null, tint = Color.Red)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                if (isGeneric) {
                    Text("XAVF ANIQLANDI", color = Color.Red, fontSize = 10.sp, fontWeight = FontWeight.Black, letterSpacing = 1.sp)
                    Text("Tizimda zaifliklar mavjud", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    Text("Himoya markazini tekshiring!", color = Color.Gray, fontSize = 11.sp)
                } else {
                    Text(stringResource(R.string.alert_ai_threat), color = Color.Red, fontSize = 10.sp, fontWeight = FontWeight.Black, letterSpacing = 1.sp)
                    Text(stringResource(R.string.alert_vishing_pattern), color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    Text(stringResource(R.string.alert_vishing_desc), color = Color.Gray, fontSize = 11.sp)
                }
            }
        }
    }
}
