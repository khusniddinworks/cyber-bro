package com.eps.android.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.eps.android.R
import com.eps.android.ui.theme.*
import com.eps.android.ui.viewmodel.FileScanViewModel

@Composable
fun FileScanScreen(
    viewModel: FileScanViewModel = hiltViewModel()
) {
    val results by viewModel.scannedFiles.collectAsState()
    val isScanning by viewModel.isScanning.collectAsState()
    val hasScanned by viewModel.hasScanned.collectAsState()
    val hasStoragePermission by viewModel.hasStoragePermission.collectAsState()
    
    val context = androidx.compose.ui.platform.LocalContext.current

    // Re-check permission when screen is visible
    LaunchedEffect(Unit) {
        viewModel.checkStoragePermission()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(stringResource(R.string.title_file_scan), color = GoldPremium, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        
        // Permission Warning Banner
        if (!hasStoragePermission) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.Red.copy(alpha = 0.2f))
                    .padding(12.dp)
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Warning, contentDescription = null, tint = Color.Red, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            "Storage ruxsati kerak!",
                            color = Color.Red,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "APK fayllarni topish uchun \"Barcha fayllarga ruxsat\" yoqilishi kerak.",
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 12.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = {
                            val intent = android.content.Intent(
                                android.provider.Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION,
                                android.net.Uri.parse("package:${context.packageName}")
                            )
                            context.startActivity(intent)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Sozlamalarga o'tish", color = Color.White)
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Scan Progress / Button
        Box(
            modifier = Modifier
                .size(160.dp)
                .clip(CircleShape)
                .background(Brush.radialGradient(listOf(GoldPremium.copy(alpha=0.1f), Color.Transparent))),
            contentAlignment = Alignment.Center
        ) {
            if (isScanning) {
                CircularProgressIndicator(color = GoldPremium, strokeWidth = 2.dp, modifier = Modifier.size(120.dp))
            } else {
                IconButton(
                    onClick = { viewModel.scanStorage() },
                    modifier = Modifier.size(100.dp).background(GoldPremium.copy(alpha=0.1f), CircleShape)
                ) {
                    Icon(Icons.Default.Speed, contentDescription = null, tint = GoldPremium, modifier = Modifier.size(48.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        Text(
            if(isScanning) stringResource(R.string.scan_scanning) 
            else if(hasScanned) stringResource(R.string.scan_completed)
            else stringResource(R.string.scan_idle),
            color = Color.White,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(30.dp))

        if (hasScanned) {
            Text(stringResource(R.string.label_apks_found, results.size), color = GoldMuted, fontSize = 12.sp)
            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(results) { item ->
                    FileResultCard(item, onDelete = { viewModel.deleteFile(item.file) })
                }
            }
        }
    }
}

@Composable
fun FileResultCard(item: FileScanViewModel.ScannedFile, onDelete: () -> Unit) {
    val isMalicious = item.aiVerdict != null && item.aiVerdict.probability > 0.85f
    val isSuspicious = item.verdict.isSuspicious
    
    val statusColor = when {
        isMalicious -> Color.Red
        isSuspicious -> PlasmaYellow
        else -> MatrixGreen
    }
    
    val statusText = when {
        isMalicious -> stringResource(R.string.status_malicious)
        isSuspicious -> stringResource(R.string.status_suspicious)
        else -> stringResource(R.string.status_clean)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(GlassSurface)
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Android, contentDescription = null, tint = statusColor, modifier = Modifier.size(32.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(item.file.name, color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold, maxLines = 1)
                Text(statusText, color = statusColor, fontSize = 10.sp, fontWeight = FontWeight.Black)
                if (isSuspicious || isMalicious) {
                    Text(item.verdict.reason, color = Color.Gray, fontSize = 10.sp)
                }
            }
            if (isMalicious || isSuspicious) {
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.DeleteForever, contentDescription = null, tint = Color.Red)
                }
            }
        }
    }
}
