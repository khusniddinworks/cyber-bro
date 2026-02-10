package com.eps.android.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.stringResource
import com.eps.android.R
import com.eps.android.ui.theme.*
import com.eps.android.ui.viewmodel.DashboardViewModel

@Composable
fun SettingsScreen(viewModel: DashboardViewModel) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val sharedPrefs = remember { context.getSharedPreferences("settings", android.content.Context.MODE_PRIVATE) }
    
    var currentLang by remember { mutableStateOf(sharedPrefs.getString("app_lang", "uz") ?: "uz") }
    var vpnAutoConnect by remember { mutableStateOf(sharedPrefs.getBoolean("vpn_auto", false)) }
    var scanSensitivity by remember { mutableFloatStateOf(sharedPrefs.getFloat("scan_sensitivity", 0.7f)) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(stringResource(R.string.settings_title), color = GoldPremium, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Text(stringResource(R.string.settings_subtitle), color = GoldMuted, fontSize = 12.sp)
        
        Spacer(modifier = Modifier.height(30.dp))
        
        // Subscription Section
        val isPremium by viewModel.isPremium.collectAsState()
        val deviceId by viewModel.deviceId.collectAsState()
        var licenseInput by remember { mutableStateOf("") }
        
        Text(stringResource(R.string.label_subscription), color = GoldPremium, fontSize = 11.sp, fontWeight = FontWeight.Black, letterSpacing = 1.sp)
        Spacer(modifier = Modifier.height(12.dp))
        
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .background(GlassSurface)
                .border(1.dp, if(isPremium) GoldPremium.copy(alpha=0.3f) else Color.White.copy(alpha=0.05f), RoundedCornerShape(24.dp))
                .padding(20.dp)
        ) {
             Column {
                 Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                     Text(stringResource(R.string.label_status), color = Color.Gray, fontSize = 13.sp)
                     Text(if(isPremium) stringResource(R.string.status_pro) else stringResource(R.string.status_free), color = GoldPremium, fontWeight = FontWeight.Black)
                 }
                 Spacer(modifier = Modifier.height(16.dp))
                 
                 Text(stringResource(R.string.label_device_id), color = Color.Gray, fontSize = 10.sp)
                 Text(deviceId, color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                 
                 if (!isPremium) {
                     Spacer(modifier = Modifier.height(24.dp))
                     OutlinedTextField(
                         value = licenseInput,
                         onValueChange = { licenseInput = it.uppercase() },
                         placeholder = { Text(stringResource(R.string.placeholder_license), color = Color.Gray) },
                         modifier = Modifier.fillMaxWidth(),
                         shape = RoundedCornerShape(12.dp),
                         colors = OutlinedTextFieldDefaults.colors(
                             focusedBorderColor = GoldPremium,
                             unfocusedBorderColor = Color.White.copy(alpha=0.1f)
                         )
                     )
                     Spacer(modifier = Modifier.height(12.dp))
                     Button(
                         onClick = { viewModel.activateLicense(licenseInput) },
                         modifier = Modifier.fillMaxWidth().height(48.dp),
                         colors = ButtonDefaults.buttonColors(containerColor = GoldPremium, contentColor = OnyxBlack),
                         shape = RoundedCornerShape(24.dp)
                     ) {
                         Text(stringResource(R.string.btn_activate), fontWeight = FontWeight.Black)
                     }
                 }
             }
        }
        
        Spacer(modifier = Modifier.height(30.dp))
        
        // Language Section
        Text(stringResource(R.string.label_language), color = GoldPremium, fontSize = 11.sp, fontWeight = FontWeight.Black)
        Spacer(modifier = Modifier.height(12.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            LanguageBtn("O'zbek", currentLang == "uz", Modifier.weight(1f)) {
                currentLang = "uz"
                updateLocale(context, "uz")
            }
            LanguageBtn("English", currentLang == "en", Modifier.weight(1f)) {
                currentLang = "en"
                updateLocale(context, "en")
            }
            LanguageBtn("Русский", currentLang == "ru", Modifier.weight(1f)) {
                currentLang = "ru"
                updateLocale(context, "ru")
            }
        }
        
        Spacer(modifier = Modifier.height(30.dp))
        
        // VPN Auto-Connect setting removed as per user request to avoid synchronization issues.


        Spacer(modifier = Modifier.height(24.dp))
        
        // Sensitivity Slider
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(GlassSurface)
                .padding(20.dp)
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(stringResource(R.string.label_sensitivity), color = Color.White, fontWeight = FontWeight.Bold)
                        Text(stringResource(R.string.label_sensitivity_desc), color = Color.Gray, fontSize = 11.sp)
                    }
                    Text("${(scanSensitivity * 100).toInt()}%", color = GoldPremium, fontWeight = FontWeight.Black)
                }
                Slider(
                    value = scanSensitivity,
                    onValueChange = { 
                        scanSensitivity = it 
                        sharedPrefs.edit().putFloat("scan_sensitivity", it).apply()
                    },
                    colors = SliderDefaults.colors(thumbColor = GoldPremium, activeTrackColor = GoldPremium)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Offline Database Update
        val launcher = androidx.activity.compose.rememberLauncherForActivityResult(
            contract = androidx.activity.result.contract.ActivityResultContracts.GetContent()
        ) { uri: android.net.Uri? ->
            uri?.let { viewModel.importOfflineDatabase(it) }
        }

        Text(stringResource(R.string.label_database), color = GoldPremium, fontSize = 11.sp, fontWeight = FontWeight.Black)
        Spacer(modifier = Modifier.height(12.dp))
        
        Button(
            onClick = { launcher.launch("application/json") },
            modifier = Modifier.fillMaxWidth().height(48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = GlassSurface, contentColor = GoldPremium),
            shape = RoundedCornerShape(24.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, GoldPremium.copy(alpha=0.3f))
        ) {
            Icon(Icons.Default.UploadFile, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(stringResource(R.string.btn_import_db), fontWeight = FontWeight.Bold)
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Engine Info
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(Color.White.copy(alpha=0.03f))
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text("Engine: Neural Guard v4.2 | Build: 2026.02", color = Color.Gray, fontSize = 10.sp)
        }
    }
}

@Composable
fun SettingsRow(title: String, subtitle: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(GlassSurface)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(title, color = Color.White, fontWeight = FontWeight.Bold)
            Text(subtitle, color = Color.Gray, fontSize = 11.sp)
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(checkedThumbColor = GoldPremium, checkedTrackColor = GoldMuted)
        )
    }
}

@Composable
fun LanguageBtn(text: String, isSelected: Boolean, modifier: Modifier, onClick: () -> Unit) {
    Box(
        modifier = modifier
            .height(44.dp)
            .clip(RoundedCornerShape(22.dp))
            .background(if (isSelected) GoldPremium else Color.White.copy(alpha=0.05f))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(text, color = if(isSelected) OnyxBlack else Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
    }
}

private fun updateLocale(context: android.content.Context, lang: String) {
    context.getSharedPreferences("settings", android.content.Context.MODE_PRIVATE)
        .edit().putString("app_lang", lang).apply()
    val appLocale: androidx.core.os.LocaleListCompat = androidx.core.os.LocaleListCompat.forLanguageTags(lang)
    androidx.appcompat.app.AppCompatDelegate.setApplicationLocales(appLocale)
    (context as? android.app.Activity)?.recreate()
}
