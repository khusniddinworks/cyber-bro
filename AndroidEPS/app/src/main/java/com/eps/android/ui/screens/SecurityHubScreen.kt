package com.eps.android.ui.screens

import android.content.Intent
import android.provider.Settings
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.eps.android.ui.components.GlassCard
import com.eps.android.ui.theme.*
import com.eps.android.ui.viewmodel.SecurityHubViewModel
import com.eps.android.ui.viewmodel.AppAuditViewModel
import androidx.compose.ui.res.stringResource
import com.eps.android.R
import com.eps.android.ui.screens.VulnerabilityAppsDialog


@Composable
fun SecurityHubScreen(
    viewModel: SecurityHubViewModel = hiltViewModel(),
    auditViewModel: AppAuditViewModel = hiltViewModel(),
    onOpenScanner: () -> Unit
) {
    val stats by viewModel.stats.collectAsState()
    val auditApps by auditViewModel.apps.collectAsState()
    val isAuditScanning by auditViewModel.isScanning.collectAsState()
    val context = LocalContext.current
    
    var showAppList by remember { mutableStateOf<List<SecurityHubViewModel.AppInfo>?>(null) }
    var showAuditResults by remember { mutableStateOf(false) }
    var listTitle by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header
        Text(stringResource(R.string.hub_title), color = GoldPremium, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Text(stringResource(R.string.hub_subtitle), color = GoldMuted, fontSize = 12.sp)

        Spacer(modifier = Modifier.height(30.dp))

        // Dialog states
        var showVulnerabilityDialog by remember { mutableStateOf(false) }
        var appToDelete by remember { mutableStateOf<SecurityHubViewModel.AppInfo?>(null) }

        // Combined unique list of apps with sensitive permissions (Camera + Mic)
        val vulnerableApps = remember(stats) {
            (stats.appsWithCamera + stats.appsWithMic).distinctBy { it.packageName }
        }

        // Big Status Card
        val isVulnerable = stats.isDevModeEnabled || stats.isUnknownSourcesAllowed || vulnerableApps.isNotEmpty()
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(32.dp))
                .background(if(isVulnerable) Color(0xFF2C1616) else GlassSurface)
                .border(1.dp, if(isVulnerable) Color.Red.copy(alpha=0.3f) else GoldPremium.copy(alpha=0.1f), RoundedCornerShape(32.dp))
                .clickable { if (vulnerableApps.isNotEmpty()) showVulnerabilityDialog = true }
                .padding(24.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier.size(56.dp).background(if(isVulnerable) Color.Red.copy(alpha=0.1f) else GoldPremium.copy(alpha=0.1f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if(isVulnerable) Icons.Default.ReportProblem else Icons.Default.VerifiedUser,
                        contentDescription = null,
                        tint = if(isVulnerable) Color.Red else GoldPremium,
                        modifier = Modifier.size(32.dp)
                    )
                }
                Spacer(modifier = Modifier.width(20.dp))
                Column {
                    Text(
                        if(isVulnerable) stringResource(R.string.hub_status_vulnerable) else stringResource(R.string.hub_status_secure),
                        color = if(isVulnerable) Color.Red else Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        if(vulnerableApps.isNotEmpty()) "${vulnerableApps.size} ta ruxsatli ilova topildi (Batafsil)" else stringResource(R.string.hub_no_leaks),
                        color = GoldMuted,
                        fontSize = 12.sp
                    )
                }
            }
        }

        // Vulnerability List Dialog
        if (showVulnerabilityDialog) {
            VulnerabilityAppsDialog(
                apps = vulnerableApps, 
                onDismiss = { showVulnerabilityDialog = false },
                onUninstallRequest = { appToDelete = it }
            )
        }

        // Uninstall Confirmation Dialog
        if (appToDelete != null) {
            AlertDialog(
                onDismissRequest = { appToDelete = null },
                containerColor = CyberDark,
                title = { Text("Ilovani o'chirish", color = Color.White, fontWeight = FontWeight.Bold) },
                text = { Text("Haqiqatan ham ${appToDelete?.name} ilovasini o'chirib tashlamoqchimisiz?", color = Color.Gray) },
                confirmButton = {
                    Button(
                        onClick = {
                            val intent = Intent(Intent.ACTION_DELETE).apply {
                                data = android.net.Uri.parse("package:${appToDelete?.packageName}")
                                flags = Intent.FLAG_ACTIVITY_NEW_TASK
                            }
                            context.startActivity(intent)
                            appToDelete = null
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                    ) {
                        Text("HA, O'CHIRISH")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { appToDelete = null }) {
                        Text("YO'Q", color = GoldPremium)
                    }
                }
            )
        }

        Spacer(modifier = Modifier.height(20.dp))
        
        // FILE SCAN BUTTON
        Button(
            onClick = onOpenScanner,
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = GoldPremium.copy(alpha=0.1f)),
            shape = RoundedCornerShape(20.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, GoldPremium.copy(alpha=0.2f))
        ) {
            Icon(Icons.Default.ManageSearch, contentDescription = null, tint = GoldPremium)
            Spacer(modifier = Modifier.width(12.dp))
            Text(stringResource(R.string.title_file_scan), color = Color.White, fontWeight = FontWeight.Bold)
        }
        
        Spacer(modifier = Modifier.height(12.dp))
        
        // APP AUDIT BUTTON - O'rnatilgan ilovalarni tekshirish
        Button(
            onClick = { 
                auditViewModel.scanApps()
            },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1A2A1A)),
            shape = RoundedCornerShape(20.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF4CAF50).copy(alpha=0.3f)),
            enabled = !isAuditScanning
        ) {
            Icon(Icons.Default.Apps, contentDescription = null, tint = Color(0xFF4CAF50))
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                "O'rnatilgan Ilovalar Auditi",
                color = Color.White, 
                fontWeight = FontWeight.Bold
            )
        }

        // Show Scan Results automatically when scan finishes
        LaunchedEffect(isAuditScanning) {
            if (!isAuditScanning && auditApps.isNotEmpty()) {
                // Determine if we should show results based on previous state logic if needed
                // For now, let user click to see or auto-show if desired.
                // Keeping manual check or auto-show:
                if (auditApps.any { it.riskReport.level.name == "HIGH" }) {
                     showAuditResults = true
                }
            }
        }
        
        // SCANNING OVERLAY (Full Screen Dialog-like)
        if (isAuditScanning) {
            val progress by auditViewModel.scanProgress.collectAsState()
            val currentApp by auditViewModel.currentScanningApp.collectAsState()
            
            AlertDialog(
                onDismissRequest = {}, // Prevent dismissing while scanning
                containerColor = Color(0xFF111111),
                title = { Text("Tizim Tekshirilmoqda...", color = GoldPremium) },
                text = {
                     Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                         CircularProgressIndicator(
                             progress = progress,
                             color = GoldPremium,
                             modifier = Modifier.size(64.dp),
                             trackColor = GoldPremium.copy(alpha = 0.2f),
                         )
                         Spacer(modifier = Modifier.height(16.dp))
                         Text("${(progress * 100).toInt()}%", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                         Spacer(modifier = Modifier.height(8.dp))
                         Text(currentApp, color = Color.Gray, fontSize = 12.sp, maxLines = 1, overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis)
                     }
                },
                confirmButton = {}
            )
        }
        
        // Show audit results count if available
        if (auditApps.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            val riskyApps = auditApps.count { it.riskReport.level.name in listOf("HIGH", "CRITICAL") }
            Text(
                "Jami: ${auditApps.size} ta ilova | Xavfli: $riskyApps ta",
                color = if (riskyApps > 0) Color(0xFFFF6B6B) else Color(0xFF4CAF50),
                fontSize = 12.sp
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        // Permission Monitoring
        Text(stringResource(R.string.label_sensitive_access), color = GoldPremium, fontSize = 11.sp, fontWeight = FontWeight.Black, modifier = Modifier.align(Alignment.Start))
        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            AccessMetricCard(
                modifier = Modifier.weight(1f),
                icon = Icons.Default.PhotoCamera,
                count = stats.appsWithCamera.size,
                label = stringResource(R.string.label_camera)
            ) {
                listTitle = context.getString(R.string.label_camera)
                showAppList = stats.appsWithCamera
            }
            AccessMetricCard(
                modifier = Modifier.weight(1f),
                icon = Icons.Default.Mic,
                count = stats.appsWithMic.size,
                label = stringResource(R.string.label_mic)
            ) {
                listTitle = context.getString(R.string.label_mic)
                showAppList = stats.appsWithMic
            }
            AccessMetricCard(
                modifier = Modifier.weight(1f),
                icon = Icons.Default.Place,
                count = stats.appsWithLocation.size,
                label = stringResource(R.string.label_location)
            ) {
                listTitle = context.getString(R.string.label_location)
                showAppList = stats.appsWithLocation
            }
        }

        if (showAppList != null) {
            AppListDialog(title = listTitle, apps = showAppList!!) {
                showAppList = null
            }
        }
        
        if (showAuditResults) {
            HarmfulAppsDialog(apps = auditApps) {
                showAuditResults = false
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        // System Checks
        Text(stringResource(R.string.label_security_audit), color = GoldPremium, fontSize = 11.sp, fontWeight = FontWeight.Black, modifier = Modifier.align(Alignment.Start))
        Spacer(modifier = Modifier.height(16.dp))

        AuditItem(stringResource(R.string.audit_screen_lock), stats.screenLockSecure) {
            context.startActivity(Intent(Settings.ACTION_SECURITY_SETTINGS))
        }
        AuditItem(stringResource(R.string.audit_dev_mode), !stats.isDevModeEnabled) {
            context.startActivity(Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS))
        }
        AuditItem(stringResource(R.string.audit_adb), !stats.isAdbEnabled) {
            context.startActivity(Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS))
        }
        AuditItem(stringResource(R.string.audit_unknown_sources), !stats.isUnknownSourcesAllowed) {
            try { context.startActivity(Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES)) } catch(e: Exception) {
                context.startActivity(Intent(Settings.ACTION_SECURITY_SETTINGS))
            }
        }
    }
}

@Composable
fun AccessMetricCard(modifier: Modifier, icon: ImageVector, count: Int, label: String, onClick: () -> Unit) {
    Box(
        modifier = modifier
            .height(100.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(GlassSurface)
            .clickable { onClick() }
            .padding(12.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(icon, contentDescription = null, tint = GoldPremium, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Text("$count", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Black)
            Text(label, color = GoldMuted, fontSize = 10.sp)
        }
    }
}

@Composable
fun AppListDialog(title: String, apps: List<SecurityHubViewModel.AppInfo>, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = CyberDark,
        title = { Text(title, color = GoldPremium) },
        text = {
            LazyColumn(modifier = Modifier.heightIn(max = 400.dp)) {
                items(apps) { app ->
                    val context = LocalContext.current
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // App Icon using AndroidView
                        if (app.icon != null) {
                            AndroidView(
                                factory = { ctx ->
                                    android.widget.ImageView(ctx).apply {
                                        scaleType = android.widget.ImageView.ScaleType.FIT_CENTER
                                    }
                                },
                                update = { view ->
                                    view.setImageDrawable(app.icon)
                                },
                                modifier = Modifier.size(40.dp)
                            )
                        } else {
                            Icon(Icons.Default.Android, contentDescription = null, tint = GoldMuted, modifier = Modifier.size(40.dp))
                        }
                        
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(app.name, color = Color.White, fontWeight = FontWeight.SemiBold)
                            Text(app.packageName, color = Color.Gray, fontSize = 10.sp)
                        }
                        
                        // Manage Button
                        IconButton(onClick = {
                            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                                data = android.net.Uri.fromParts("package", app.packageName, null)
                                flags = Intent.FLAG_ACTIVITY_NEW_TASK
                            }
                            context.startActivity(intent)
                        }) {
                            Icon(Icons.Default.Settings, contentDescription = "Manage", tint = GoldPremium)
                        }
                    }
                    Divider(color = Color.White.copy(alpha = 0.1f))
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.btn_close), color = GoldPremium)
            }
        }
    )
}

@Composable
fun HarmfulAppsDialog(apps: List<AppAuditViewModel.AppAuditInfo>, onDismiss: () -> Unit) {
    val context = LocalContext.current
    val riskyApps = apps.filter { it.riskReport.level.name in listOf("HIGH", "CRITICAL") }
    
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = Color(0xFF1A1111),
        title = { 
             Row(verticalAlignment = Alignment.CenterVertically) {
                 Icon(Icons.Default.Warning, contentDescription = null, tint = Color.Red)
                 Spacer(modifier = Modifier.width(8.dp))
                 Text("Zararli Ilovalar (${riskyApps.size})", color = Color.Red, fontWeight = FontWeight.Bold)
             }
        },
        text = {
            LazyColumn(modifier = Modifier.heightIn(max = 400.dp)) {
                 if (riskyApps.isEmpty()) {
                     item { Text("Hozircha xavfli ilovalar topilmadi.", color = Color.Green) }
                 } else {
                    items(riskyApps) { app ->
                        Column(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                // App Icon using AndroidView
                                if (app.icon != null) {
                                    AndroidView(
                                        factory = { ctx ->
                                            android.widget.ImageView(ctx).apply {
                                                scaleType = android.widget.ImageView.ScaleType.FIT_CENTER
                                            }
                                        },
                                        update = { view ->
                                            view.setImageDrawable(app.icon)
                                        },
                                        modifier = Modifier.size(40.dp)
                                    )
                                } else {
                                    Icon(Icons.Default.Warning, contentDescription = null, tint = Color.Red, modifier = Modifier.size(40.dp))
                                }
                                
                                Spacer(modifier = Modifier.width(12.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(app.name, color = Color.White, fontWeight = FontWeight.Bold)
                                    Text("Xavf: ${app.riskReport.level.name}", color = Color.Red, fontSize = 12.sp)
                                }
                            }
                            
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("Sabab: ${app.riskReport.explanation}", color = Color.Gray, fontSize = 11.sp)
                            
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(
                                onClick = {
                                    try {
                                        val intent = Intent(Intent.ACTION_DELETE).apply {
                                            data = android.net.Uri.fromParts("package", app.packageName, null)
                                            flags = Intent.FLAG_ACTIVITY_NEW_TASK
                                        }
                                        context.startActivity(intent)
                                    } catch (e: Exception) {
                                        e.printStackTrace()
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                                modifier = Modifier.fillMaxWidth().height(36.dp)
                            ) {
                                Text("O'CHIRISH (UNINSTALL)", fontSize = 12.sp)
                            }
                            Divider(color = Color.White.copy(alpha=0.1f), modifier = Modifier.padding(top=8.dp))
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.btn_close), color = Color.Gray)
            }
        }
    )
}

@Composable
fun AuditItem(label: String, isSecure: Boolean, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(GlassSurface)
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            if(isSecure) Icons.Default.CheckCircle else Icons.Default.Warning,
            contentDescription = null,
            tint = if(isSecure) MatrixGreen else Color.Red,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(label, color = Color.White, modifier = Modifier.weight(1f), fontSize = 14.sp)
        if(!isSecure) {
            Text(stringResource(R.string.btn_fix), color = Color.Red, fontWeight = FontWeight.Black, fontSize = 12.sp)
        }
    }
}
