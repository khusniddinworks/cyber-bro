package com.eps.android.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.eps.android.ui.components.GlassCard
import com.eps.android.ui.components.CyberText
import com.eps.android.ui.theme.*
import com.eps.android.ui.viewmodel.*
import com.eps.android.analysis.AppRiskAuditor
import androidx.compose.ui.res.stringResource
import com.eps.android.R
import android.content.Intent
import android.net.Uri
import android.provider.Settings

// Feature navigation destinations
private object Dest {
    const val HOME = "home"
    const val SCAN = "scan"
    const val VAULT = "vault"
    const val VPN = "vpn"
    const val AI = "ai"
    const val SETTINGS = "settings"
    const val FILE_SCAN = "file_scan"
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold(
    viewModel: DashboardViewModel = hiltViewModel(),
    auditViewModel: AppAuditViewModel = hiltViewModel(),
    fileViewModel: FileScanViewModel = hiltViewModel(),
    networkViewModel: NetworkTrafficViewModel = hiltViewModel(),
    aiViewModel: AiMentorViewModel = hiltViewModel(),
    securityHubViewModel: SecurityHubViewModel = hiltViewModel()
) {
    var currentScreen by remember { mutableStateOf(Dest.HOME) }
    
    BackHandler(enabled = currentScreen != Dest.HOME) {
        currentScreen = Dest.HOME
    }

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = OnyxBlack,
                tonalElevation = 0.dp,
                modifier = Modifier
                    .padding(16.dp)
                    .clip(RoundedCornerShape(32.dp))
                    .border(1.dp, Color.White.copy(alpha=0.05f), RoundedCornerShape(32.dp))
            ) {
                NavItem(stringResource(R.string.nav_home), Icons.Default.Dashboard, currentScreen == Dest.HOME) { currentScreen = Dest.HOME }
                NavItem(stringResource(R.string.nav_shield), Icons.Default.Security, currentScreen == Dest.SCAN) { currentScreen = Dest.SCAN }
                NavItem(stringResource(R.string.nav_vault), Icons.Default.Lock, currentScreen == Dest.VAULT) { currentScreen = Dest.VAULT }
                NavItem(stringResource(R.string.nav_ai), Icons.Default.Psychology, currentScreen == Dest.AI) { currentScreen = Dest.AI }
                NavItem(stringResource(R.string.nav_settings), Icons.Default.Settings, currentScreen == Dest.SETTINGS) { currentScreen = Dest.SETTINGS }
            }
        },
        containerColor = OnyxBlack
    ) { padding ->
        Box(modifier = Modifier.padding(padding).fillMaxSize()) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(AmbientGold, Color.Transparent),
                        radius = size.minDimension
                    ),
                    center = center.copy(y = 0f)
                )
            }

            val isPremium by viewModel.isPremium.collectAsState()

            when (currentScreen) {
                Dest.HOME -> HomeDashboard(viewModel) { currentScreen = it }
                Dest.SCAN -> SecurityHubScreen(securityHubViewModel) { currentScreen = Dest.FILE_SCAN }
                Dest.FILE_SCAN -> FileScanScreen(fileViewModel)
                Dest.VAULT -> {
                    if (isPremium) FileVaultScreen()
                    else com.eps.android.ui.components.PremiumLockOverlay(
                        message = "File Vault faqat Premium foydalanuvchilar uchun ochiq."
                    ) { currentScreen = Dest.SETTINGS }
                }
                Dest.VPN -> NetworkTrafficScreen(networkViewModel)
                Dest.AI -> {
                    if (isPremium) AiVoiceChatScreen(aiViewModel)
                    else com.eps.android.ui.components.PremiumLockOverlay(
                        message = "AI Kiber-Konsultant bilan muloqot qilish uchun Premium kerak."
                    ) { currentScreen = Dest.SETTINGS }
                }
                Dest.SETTINGS -> SettingsScreen(viewModel)
            }
        }
    }
}

@Composable
fun RowScope.NavItem(label: String, icon: ImageVector, selected: Boolean, onClick: () -> Unit) {
    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = { Icon(icon, contentDescription = label, modifier = Modifier.size(24.dp)) },
        label = { Text(label, fontSize = 10.sp) },
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = GoldPremium,
            unselectedIconColor = Color.Gray.copy(alpha=0.6f),
            indicatorColor = GoldPremium.copy(alpha = 0.1f),
            selectedTextColor = GoldPremium,
            unselectedTextColor = Color.Gray
        )
    )
}

@Composable
fun HomeDashboard(viewModel: DashboardViewModel, onNavigate: (String) -> Unit) {
    val securityScore by viewModel.securityScore.collectAsState()
    val animatedScore by animateIntAsState(targetValue = securityScore, animationSpec = tween(2000), label = "")
    
    val lifecycleOwner = androidx.compose.ui.platform.LocalLifecycleOwner.current
    var isResumed by remember { mutableStateOf(false) }

    DisposableEffect(lifecycleOwner) {
        val observer = androidx.lifecycle.LifecycleEventObserver { _, event ->
            isResumed = event == androidx.lifecycle.Lifecycle.Event.ON_RESUME
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
    
    val infiniteTransition = rememberInfiniteTransition(label = "")
    
    // Only animate if lifecycle is RESUMED to save battery
    val orbRotation by if (isResumed) {
        infiniteTransition.animateFloat(
            initialValue = 0f, targetValue = 360f,
            animationSpec = infiniteRepeatable(tween(10000, easing = LinearEasing)), label = ""
        )
    } else {
        remember { mutableFloatStateOf(0f) }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp).verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Column {
                Text(stringResource(R.string.header_core), color = GoldMuted, fontSize = 10.sp, letterSpacing = 2.sp, fontWeight = FontWeight.Black)
                Text(stringResource(R.string.app_name), color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            }
            Icon(Icons.Default.NotificationsNone, contentDescription = null, tint = GoldPremium)
        }

        val protectionStatus by viewModel.protectionStatus.collectAsState()
        
        // Refresh status when resumed
        LaunchedEffect(isResumed) {
            if (isResumed) viewModel.checkAllServices()
        }

        Spacer(modifier = Modifier.height(16.dp))
        
        // --- REAL-TIME PROTECTION STATUS CARD ---
        if (!protectionStatus.isAccessibilityActive || !protectionStatus.isNotificationListenerActive || !protectionStatus.isFileGuardActive) {
            SystemStatusCard(protectionStatus)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // CENTRAL ORB
        Box(contentAlignment = Alignment.Center, modifier = Modifier.size(280.dp)) {
            Box(modifier = Modifier.size(200.dp).background(GoldPremium.copy(alpha=0.05f), CircleShape).blur(40.dp))
            Canvas(modifier = Modifier.fillMaxSize()) {
                val stroke = 2.dp.toPx()
                rotate(orbRotation) {
                    drawArc(GoldPremium.copy(alpha=0.3f), 0f, 60f, false, style = Stroke(stroke, cap = StrokeCap.Round))
                    drawArc(GoldPremium.copy(alpha=0.3f), 120f, 60f, false, style = Stroke(stroke, cap = StrokeCap.Round))
                    drawArc(GoldPremium.copy(alpha=0.3f), 240f, 60f, false, style = Stroke(stroke, cap = StrokeCap.Round))
                }
            }
            Surface(
                modifier = Modifier.size(180.dp),
                shape = CircleShape,
                color = Color(0xFF0A0F14),
                border = androidx.compose.foundation.BorderStroke(1.dp, GoldPremium.copy(alpha=0.2f))
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "$animatedScore%", fontSize = 56.sp, color = GoldPremium, fontWeight = FontWeight.Light)
                        Text(
                            text = if(animatedScore > 80) stringResource(R.string.status_secure) else stringResource(R.string.status_risk), 
                            fontSize = 12.sp, color = GoldMuted, letterSpacing = 4.sp
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        // FEATURE GRID
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.height(260.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            userScrollEnabled = false
        ) {
            item { FeatureCard(stringResource(R.string.feat_vpn), Icons.Default.VpnKey, GoldPremium) { onNavigate(Dest.VPN) } }
            item { FeatureCard(stringResource(R.string.feat_audit), Icons.Default.Search, GoldPremium) { onNavigate(Dest.FILE_SCAN) } }
            item { FeatureCard(stringResource(R.string.feat_vault), Icons.Default.Lock, GoldPremium) { onNavigate(Dest.VAULT) } }
            item { FeatureCard(stringResource(R.string.feat_ai), Icons.Default.Psychology, GoldPremium) { onNavigate(Dest.AI) } }
        }
        
        
        // Critical Fix: Only show Vishing Alert if there is a specific HIGH CONFIDENCE threat logged
        // We can check if securityScore is critically low AND we have a Vishing event. 
        // For now, let's just make it less sensitive.
        // Or check a flag from ViewModel. Since we didn't add the flag yet, let's rely on Score < 50 for CRITICAL ALERT, 
        // but change the text to be generic unless we know it's Vishing.
        // Better approach: Check if criticalCount > 0.
        
        val criticalThreats by viewModel.criticalCount.collectAsState()
        
        if (criticalThreats > 0) {
            Spacer(modifier = Modifier.height(16.dp))
            // We should ideally check the TYPE of threat here, but for now, 
            // if there are critical threats, we show an alert. 
            // However, the previous alert was SPECIFIC to Vishing. 
            // Let's change it to a generic "Security Alert" card unless we filter for Vishing.
            VishingAlertCard(isGeneric = true) 
        }
    }
}

@Composable
fun FeatureCard(title: String, icon: ImageVector, color: Color, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(GlassSurface)
            .border(1.dp, Color.White.copy(alpha=0.08f), RoundedCornerShape(24.dp))
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

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(Brush.verticalGradient(listOf(Color(0xFF1E2632), Color(0xFF0F1419))))
            .border(1.dp, GoldPremium.copy(alpha=0.3f), RoundedCornerShape(24.dp))
            .padding(20.dp)
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.AutoFixHigh, contentDescription = null, tint = GoldPremium, modifier = Modifier.size(32.dp))
                Spacer(modifier = Modifier.width(12.dp))
                Text("XAVFSIZLIK YORDAMCHISI", color = Color.White, fontWeight = FontWeight.ExtraBold, fontSize = 18.sp)
            }
            
            Text(
                "Himoyani 3 ta bosqichda to'liq yoqing:", 
                color = Color.Gray, 
                fontSize = 12.sp, 
                modifier = Modifier.padding(top = 4.dp, bottom = 20.dp)
            )

            // STEP 1: RESTRICTED SETTINGS UNBLOCK
            WizardStep(
                step = "1",
                title = "Cheklovni yechish",
                desc = "Sozlamalarda tepada 3ta nuqtani bosing va 'Allow restricted settings' ni tanlang",
                isDone = status.isAccessibilityActive || status.isNotificationListenerActive // Usually if one is active, it's unlocked
            ) {
                val intent = Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.parse("package:${context.packageName}")
                }
                context.startActivity(intent)
                showUnblockGuide = true
            }

            // STEP 2: FISHING PROTECTION
            WizardStep(
                step = "2",
                title = "Fishingdan himoya",
                desc = "Brauzerda karta o'g'rilarini to'xtatish uchun",
                isDone = status.isAccessibilityActive,
                isEnabled = true
            ) {
                context.startActivity(Intent(android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS))
            }

            // STEP 3: TELEGRAM PROTECTION
            WizardStep(
                step = "3",
                title = "Telegram himoyasi",
                desc = "Sizga keladigan virusli fayllarni bloklash uchun",
                isDone = status.isNotificationListenerActive,
                isEnabled = true
            ) {
                openNotificationListenerSettings(context)
            }
        }
    }

    if (showUnblockGuide) {
        AlertDialog(
            onDismissRequest = { showUnblockGuide = false },
            title = { Text("Nima qilish kerak?", color = Color.White) },
            text = { 
                Text(
                    "1. Ochilgan oynada tepa o'ng burchakdagi ⋮ (3ta nuqta) ni bosing.\n\n" +
                    "2. 'Cheklangan sozlamalarga ruxsat berish' (Allow restricted settings) ni tanlang.\n\n" +
                    "3. Keyin ilovaga qaytib Step 2 va 3 ni yoqing.",
                    color = Color.LightGray
                )
            },
            confirmButton = {
                Button(onClick = { showUnblockGuide = false }) { Text("TUSHUNARLI") }
            },
            containerColor = Color(0xFF161B22)
        )
    }
}

@Composable
fun WizardStep(step: String, title: String, desc: String, isDone: Boolean, isEnabled: Boolean = true, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
            .alpha(if (isDone) 0.5f else 1f),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .background(if (isDone) Color.Green.copy(alpha=0.2f) else GoldPremium.copy(alpha=0.1f), CircleShape)
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
            IconButton(onClick = onClick) {
                Icon(Icons.Default.ArrowForwardIos, contentDescription = null, tint = GoldPremium, modifier = Modifier.size(16.dp))
            }
        }
    }
}

private fun openNotificationListenerSettings(context: android.content.Context) {
    try {
        context.startActivity(Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"))
    } catch (e: Exception) {
        context.startActivity(Intent(android.provider.Settings.ACTION_SETTINGS))
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


@Composable
fun NetworkTrafficScreen(viewModel: NetworkTrafficViewModel) {
    val isVpnActive by viewModel.isVpnActive.collectAsState()
    Box(modifier = Modifier.fillMaxSize().background(OnyxBlack), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(Icons.Default.Security, contentDescription = null, tint = GoldPremium, modifier = Modifier.size(100.dp))
            Spacer(modifier = Modifier.height(24.dp))
            Text("DNS SHIELD", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text(if(isVpnActive) "PROTECTED" else "UNPROTECTED", color = if(isVpnActive) GoldPremium else Color.Gray)
            Spacer(modifier = Modifier.height(40.dp))
            Switch(
                checked = isVpnActive,
                onCheckedChange = { viewModel.toggleVpn(it) },
                colors = SwitchDefaults.colors(checkedThumbColor = GoldPremium, checkedTrackColor = GoldMuted)
            )
        }
    }
}

