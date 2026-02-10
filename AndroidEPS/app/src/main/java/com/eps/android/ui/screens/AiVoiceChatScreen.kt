package com.eps.android.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.eps.android.ui.theme.*
import com.eps.android.ui.viewmodel.AiMentorViewModel

@Composable
fun AiVoiceChatScreen(viewModel: AiMentorViewModel = hiltViewModel()) {
    val chatHistory by viewModel.chatHistory.collectAsState()
    var inputText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // --- HEADER ---
        Text(stringResource(R.string.ai_title), color = GoldPremium, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Text(stringResource(R.string.ai_subtitle), color = GoldMuted, fontSize = 12.sp)
        
        Spacer(modifier = Modifier.height(30.dp))

        // --- CHAT HISTORY ---
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 20.dp)
        ) {
            items(chatHistory) { message ->
                ChatBubble(message)
            }
        }

        // --- INPUT AREA ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(32.dp))
                .background(GlassSurface)
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = inputText,
                onValueChange = { inputText = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text(stringResource(R.string.ai_placeholder), color = Color.Gray) },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedTextColor = Color.White
                )
            )
            
            IconButton(
                onClick = { 
                    if (inputText.isNotBlank()) {
                        viewModel.sendMessage(inputText)
                        inputText = ""
                    }
                },
                modifier = Modifier
                    .size(48.dp)
                    .background(GoldPremium, CircleShape)
            ) {
                Icon(Icons.Default.Send, contentDescription = null, tint = OnyxBlack)
            }
        }
    }
}

@Composable
fun ChatBubble(message: AiMentorViewModel.ChatMessage) {
    val isUser = message.isUser
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        contentAlignment = if (isUser) Alignment.CenterEnd else Alignment.CenterStart
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(
                    topStart = 20.dp, 
                    topEnd = 20.dp, 
                    bottomStart = if (isUser) 20.dp else 4.dp,
                    bottomEnd = if (isUser) 4.dp else 20.dp
                ))
                .background(if (isUser) GoldPremium.copy(alpha=0.15f) else Color.White.copy(alpha=0.08f))
                .border(1.dp, if (isUser) GoldPremium.copy(alpha=0.3f) else Color.White.copy(alpha=0.05f), RoundedCornerShape(20.dp))
                .padding(16.dp)
        ) {
            Text(message.text, color = Color.White, fontSize = 14.sp)
        }
    }
}
