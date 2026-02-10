package com.eps.android.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.eps.android.ui.components.CyberScanningEffect
import com.eps.android.ui.components.GlassCard
import com.eps.android.ui.theme.*
import com.eps.android.ui.viewmodel.FileVaultViewModel
import androidx.compose.ui.res.stringResource
import com.eps.android.R
import java.io.File
import kotlinx.coroutines.delay


@Composable
fun FileVaultScreen(viewModel: FileVaultViewModel = hiltViewModel()) {
    var selectedUris by remember { mutableStateOf<List<Uri>>(emptyList()) }
    var password by remember { mutableStateOf("") }
    // Modes: VIEW_LIST, ENCRYPT_FORM, DECRYPT_DIALOG
    var isEncryptMode by remember { mutableStateOf(false) } 
    var showDecryptDialog by remember { mutableStateOf<File?>(null) }
    
    val isProcessing by viewModel.isProcessing.collectAsState()
    val resultPath by viewModel.resultPath.collectAsState()
    val error by viewModel.error.collectAsState()
    val vaultFiles by viewModel.vaultFiles.collectAsState()
    
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetMultipleContents()) { uris ->
        selectedUris = uris
        if (uris.isNotEmpty()) {
            isEncryptMode = true // Switch to encrypt form when files picked
            password = ""
        }
        viewModel.clearResult()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            // --- HEADER ---
            Row(horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                Column {
                    Text(stringResource(R.string.vault_title), color = GoldPremium, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    Text(stringResource(R.string.vault_subtitle), color = GoldMuted, fontSize = 12.sp)
                }
                // Add Button
                IconButton(
                    onClick = { launcher.launch("*/*") },
                    modifier = Modifier.background(GoldPremium.copy(alpha=0.1f), CircleShape)
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add", tint = GoldPremium)
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            if (isEncryptMode) {
                // --- ENCRYPTION FORM ---
                EncryptionForm(
                    selectedUris = selectedUris,
                    isProcessing = isProcessing,
                    error = error,
                    onCancel = { isEncryptMode = false; selectedUris = emptyList(); viewModel.clearResult() },
                    onEncrypt = { pwd, shred -> 
                        viewModel.encryptFiles(selectedUris, pwd, shred)
                        // Note: We stay here until success or error. 
                        // If success, we show result, then user can go back.
                    },
                    resultPath = resultPath
                )
            } else {
                // --- VAULT LIST ---
                if (vaultFiles.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.Lock, contentDescription = null, tint = Color.Gray.copy(alpha=0.3f), modifier = Modifier.size(64.dp))
                            Spacer(modifier = Modifier.height(16.dp))
                            Text("Ombor bo'sh", color = Color.Gray)
                            Text("Fayl qo'shish uchun + tugmasini bosing", color = Color.Gray, fontSize = 12.sp)
                        }
                    }
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(vaultFiles.size) { index ->
                            val file = vaultFiles[index]
                            VaultFileItem(
                                file = file,
                                onDecrypt = { showDecryptDialog = file },
                                onDelete = { viewModel.deleteFile(file) }
                            )
                        }
                    }
                }
            }
        }
        
        // --- DECRYPT DIALOG ---
        if (showDecryptDialog != null) {
            AlertDialog(
                onDismissRequest = { if(!isProcessing) showDecryptDialog = null },
                containerColor = CyberDark,
                title = { Text("Faylni Ochish", color = GoldPremium) },
                text = {
                    Column {
                        Text("Parolni kiriting:", color = Color.White)
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = password,
                            onValueChange = { password = it },
                            visualTransformation = PasswordVisualTransformation(),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = GoldPremium,
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White
                            )
                        )
                        if (error != null) {
                            Text(error!!, color = Color.Red, fontSize = 12.sp)
                        }
                        if (isProcessing) {
                            LinearProgressIndicator(modifier = Modifier.fillMaxWidth().padding(top=8.dp), color = GoldPremium)
                        }
                    }
                },
                confirmButton = {
                    Button(
                        onClick = { viewModel.decryptFile(showDecryptDialog!!, password) },
                        colors = ButtonDefaults.buttonColors(containerColor = GoldPremium),
                        enabled = !isProcessing
                    ) {
                        Text("OCHISH", color = Color.Black)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDecryptDialog = null; viewModel.clearResult(); password = "" }) {
                        Text("BEKOR QILISH", color = Color.Gray)
                    }
                }
            )
        }
        
        // --- DECRYPTION SUCCESS OVERLAY ---
        if (resultPath != null && !isEncryptMode && showDecryptDialog != null) {
            // Processing finished, success.
            LaunchedEffect(resultPath) {
                showDecryptDialog = null // Close dialog
                // Trigger Open File
                val file = File(resultPath!!)
                val uri = androidx.core.content.FileProvider.getUriForFile(
                    context, "${context.packageName}.fileprovider", file
                )
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    setDataAndType(uri, context.contentResolver.getType(uri) ?: "*/*")
                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                }
                try {
                    context.startActivity(intent)
                } catch (e: Exception) {
                    // Show toast or fallback
                    val share = Intent(Intent.ACTION_SEND).apply {
                        putExtra(Intent.EXTRA_STREAM, uri)
                        type = "*/*"
                        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    }
                    context.startActivity(Intent.createChooser(share, "Faylni ochish..."))
                }
                viewModel.clearResult()
            }
        }
    }
}

@Composable
fun VaultFileItem(file: File, onDecrypt: () -> Unit, onDelete: () -> Unit) {
    var showDeleteConfirm by remember { mutableStateOf(false) }
    val context = LocalContext.current
    
    // Format date
    val date = remember(file.lastModified()) {
        java.text.SimpleDateFormat("dd MMM yyyy HH:mm", java.util.Locale.getDefault()).format(file.lastModified())
    }
    
    // Clean Name (Remove _TIMESTAMP.cbr suffix for display)
    val displayName = remember(file.name) {
        val name = file.name
        if (name.contains("_") && name.endsWith(".cbr")) {
            name.substringBeforeLast("_")
        } else {
            name
        }
    }
    
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(GlassSurface)
            .border(1.dp, Color.White.copy(alpha=0.05f), RoundedCornerShape(16.dp))
            .clickable { onDecrypt() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.size(40.dp).background(GoldPremium.copy(alpha=0.1f), CircleShape), contentAlignment = Alignment.Center) {
            Icon(Icons.Default.Lock, contentDescription = null, tint = GoldPremium, modifier = Modifier.size(20.dp))
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(displayName, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Text(date, color = Color.Gray, fontSize = 10.sp)
            Text("${file.length() / 1024} KB", color = GoldMuted, fontSize = 10.sp)
        }
        
        // Share Button
        IconButton(onClick = { 
            try {
                val uri = androidx.core.content.FileProvider.getUriForFile(
                    context, "${context.packageName}.fileprovider", file
                )
                val shareIntent = Intent(Intent.ACTION_SEND).apply {
                    type = "*/*"
                    putExtra(Intent.EXTRA_STREAM, uri)
                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                }
                context.startActivity(Intent.createChooser(shareIntent, "Share Encrypted File"))
            } catch (e: Exception) { e.printStackTrace() }
        }) {
            Icon(Icons.Default.Share, contentDescription = "Share", tint = GoldPremium)
        }

        IconButton(onClick = { showDeleteConfirm = true }) {
            Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color.Red.copy(alpha=0.6f))
        }
    }
    
    if (showDeleteConfirm) {
        AlertDialog(
            onDismissRequest = { showDeleteConfirm = false },
            title = { Text("O'chirish", color = Color.White) },
            text = { Text("Haqiqatan ham bu faylni o'chirmoqchimisiz? Qayta tiklab bo'lmaydi.", color = Color.Gray) },
            confirmButton = {
                TextButton(onClick = { onDelete(); showDeleteConfirm = false }) { Text("XA", color = Color.Red) }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteConfirm = false }) { Text("YO'Q", color = Color.White) }
            },
            containerColor = CyberDark
        )
    }
}

@Composable
fun EncryptionForm(
    selectedUris: List<Uri>,
    isProcessing: Boolean,
    error: String?,
    onCancel: () -> Unit,
    onEncrypt: (String, Boolean) -> Unit,
    resultPath: String?
) {
    var password by remember { mutableStateOf("") }
    var shredOriginal by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(GlassSurface)
            .padding(24.dp)
    ) {
        Text("Fayllarni Shifrlash", color = GoldPremium, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        
        Text("Tanlangan: ${selectedUris.size} ta fayl", color = Color.Gray)
        Spacer(modifier = Modifier.height(16.dp))
        
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Parol o'ylab toping") },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = GoldPremium,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            ),
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        
        Spacer(modifier = Modifier.height(12.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = shredOriginal, onCheckedChange = { shredOriginal = it }, colors = CheckboxDefaults.colors(checkedColor = GoldPremium))
            Text("Asl faylni o'chirib tashlash", color = Color.Gray, fontSize = 12.sp)
        }
        
        if (error != null) {
            Text(error, color = Color.Red, fontSize = 12.sp, modifier = Modifier.padding(vertical = 8.dp))
        }
        
        if (resultPath != null) {
            Text("Muvaffaqiyatli shifrlandi!", color = GoldPremium, fontSize = 14.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(24.dp))
        
        Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
            TextButton(onClick = onCancel) { Text("Yopish", color = Color.Gray) }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = { onEncrypt(password, shredOriginal) },
                colors = ButtonDefaults.buttonColors(containerColor = GoldPremium),
                enabled = password.isNotEmpty() && !isProcessing && resultPath == null
            ) {
                if(isProcessing) CircularProgressIndicator(modifier = Modifier.size(16.dp), color = Color.Black)
                else Text("BOSHLASH", color = Color.Black)
            }
        }
    }
}


@Composable
fun VaultTabButton(text: String, isSelected: Boolean, modifier: Modifier, onClick: () -> Unit) {
    Box(
        modifier = modifier
            .fillMaxHeight()
            .clip(RoundedCornerShape(24.dp))
            .background(if (isSelected) GoldPremium else Color.Transparent)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = if (isSelected) OnyxBlack else Color.Gray,
            fontSize = 12.sp,
            fontWeight = FontWeight.Black,
            letterSpacing = 1.sp
        )
    }
}
