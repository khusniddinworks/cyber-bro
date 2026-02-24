package com.eps.android.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.content.Intent
import android.net.Uri
import androidx.hilt.navigation.compose.hiltViewModel
import com.eps.android.R
import com.eps.android.ui.components.*
import com.eps.android.ui.theme.*
import com.eps.android.ui.viewmodel.*

private object Dest {
    const val HOME = "home"
    const val SCAN = "scan"
    const val VAULT = "vault"
    const val AI = "ai"
    const val SETTINGS = "settings"
    const val FILE_SCAN = "file_scan"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold(
    viewModel: DashboardViewModel = hiltViewModel(),
    fileViewModel: FileScanViewModel = hiltViewModel(),
    aiViewModel: AiMentorViewModel = hiltViewModel(),
    securityHubViewModel: SecurityHubViewModel = hiltViewModel()
) {
    var currentScreen by remember { mutableStateOf(Dest.HOME) }
    
    val density = LocalDensity.current
    val cappedDensity = remember(density) {
        Density(
            density = density.density,
            fontScale = density.fontScale.coerceAtMost(1.15f)
        )
    }

    CompositionLocalProvider(LocalDensity provides cappedDensity) {
        BackHandler(enabled = currentScreen != Dest.HOME) {
            currentScreen = Dest.HOME
        }

        Scaffold(
            bottomBar = {
                NavigationBar(
                    containerColor = OnyxBlack,
                    tonalElevation = 0.dp,
                    modifier = Modifier
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                        .clip(RoundedCornerShape(32.dp))
                        .border(1.dp, Color.White.copy(alpha=0.05f), RoundedCornerShape(32.dp))
                        .height(80.dp)
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

                val areFeaturesUnlocked by viewModel.areFeaturesUnlocked.collectAsState()
                val trialDays by viewModel.trialDaysRemaining.collectAsState()

                when (currentScreen) {
                    Dest.HOME -> HomeDashboard(viewModel) { currentScreen = it }
                    Dest.SCAN -> SecurityHubScreen(securityHubViewModel) { currentScreen = Dest.FILE_SCAN }
                    Dest.FILE_SCAN -> FileScanScreen(fileViewModel)
                    Dest.VAULT -> {
                        if (areFeaturesUnlocked) FileVaultScreen()
                        else PremiumLockOverlay(
                            message = if (trialDays <= 0) 
                                "Sinov muddati tugadi!\nKeyingi foydalanish uchun 10 000 so'm to'lov qiling." 
                                else "File Vault faqat Premium foydalanuvchilar uchun ochiq."
                        ) { currentScreen = Dest.SETTINGS }
                    }

                    Dest.AI -> {
                        if (areFeaturesUnlocked) AiVoiceChatScreen(aiViewModel)
                        else PremiumLockOverlay(
                            message = if (trialDays <= 0) 
                                "Sinov muddati tugadi!\nAI Konsultantdan foydalanish uchun 10 000 so'm to'lov qiling." 
                                else "AI Kiber-Konsultant bilan muloqot qilish uchun Premium kerak."
                        ) { currentScreen = Dest.SETTINGS }
                    }
                    Dest.SETTINGS -> SettingsScreen(viewModel)
                }
            }
        }
    }
}

@Composable
fun RowScope.NavItem(label: String, icon: ImageVector, selected: Boolean, onClick: () -> Unit) {
    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = { Icon(icon, contentDescription = label, modifier = Modifier.size(22.dp)) },
        label = { 
            Text(
                text = label, 
                fontSize = 9.sp, 
                maxLines = 1,
                overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
            ) 
        },
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = GoldPremium,
            unselectedIconColor = Color.Gray.copy(alpha=0.6f),
            indicatorColor = GoldPremium.copy(alpha = 0.1f),
            selectedTextColor = GoldPremium,
            unselectedTextColor = Color.Gray
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeDashboard(viewModel: DashboardViewModel, onNavigate: (String) -> Unit) {
    val activityLogs by viewModel.recentEvents.collectAsState()
    val securityScore by viewModel.securityScore.collectAsState()
    
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

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp).verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // --- HEADER ---
        var showNotifications by remember { mutableStateOf(false) }
        val dailyReport by viewModel.dailyReportResource.collectAsState()

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Column {
                Text(stringResource(R.string.header_core), color = GoldMuted, fontSize = 10.sp, letterSpacing = 2.sp, fontWeight = FontWeight.Black)
                Text("CYBER BROTHER", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.ExtraBold)
            }
            IconButton(onClick = { showNotifications = true }) {
                BadgedBox(
                    badge = { 
                        if (activityLogs.isNotEmpty()) {
                            Badge(
                                containerColor = Color.Red,
                                contentColor = Color.White,
                                modifier = Modifier.offset(x = (-4).dp, y = 4.dp)
                            ) { 
                                Text(
                                    text = activityLogs.size.toString(),
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                ) 
                            }
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.NotificationsNone,
                        contentDescription = "Notifications",
                        tint = GoldPremium,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
        }

        if (showNotifications) {
            NotificationDialog(
                dailyReport = dailyReport,
                logs = activityLogs,
                onDismiss = { showNotifications = false }
            )
        }

        val protectionStatus by viewModel.protectionStatus.collectAsState()
        
        LaunchedEffect(isResumed) {
            if (isResumed) viewModel.checkAllServices()
        }

        Spacer(modifier = Modifier.height(16.dp))
        
        if (!protectionStatus.isAccessibilityActive || !protectionStatus.isNotificationListenerActive || !protectionStatus.isFileGuardActive) {
            SystemStatusCard(protectionStatus)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Spacer(modifier = Modifier.height(16.dp))


        // --- CYBER ORB (IMPROVED) ---
        CyberSecurityOrb(
            score = securityScore,
            isResumed = isResumed
        )

        Spacer(modifier = Modifier.height(30.dp))

        // --- FEATURE GRID ---
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.heightIn(max = 400.dp), // Responsive height
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            userScrollEnabled = false
        ) {
            item { FeatureCard(stringResource(R.string.feat_audit), Icons.Default.Search, GoldPremium) { onNavigate("file_scan") } }
            item { FeatureCard(stringResource(R.string.feat_vault), Icons.Default.Lock, GoldPremium) { onNavigate("vault") } }
            item { FeatureCard(stringResource(R.string.feat_ai), Icons.Default.Psychology, GoldPremium) { onNavigate("ai") } }
        }
    }
}
