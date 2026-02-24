package com.eps.android.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eps.android.ui.theme.MatrixGreen
import kotlin.random.Random

@Composable
fun CyberDecodingOverlay(progress: Float, statusText: String) {
    val infiniteTransition = rememberInfiniteTransition(label = "decoding")
    
    // Binary rain effect simulation
    val alphaAnim by infiniteTransition.animateFloat(
        initialValue = 0.2f, targetValue = 0.8f,
        animationSpec = infiniteRepeatable(tween(500), RepeatMode.Reverse), label = ""
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = statusText,
            color = MatrixGreen,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 2.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        
        // Progress Bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .background(Color.White.copy(alpha=0.1f), CircleShape)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(progress.coerceIn(0f, 1f))
                    .fillMaxHeight()
                    .background(MatrixGreen, CircleShape)
            )
        }
        
        Spacer(modifier = Modifier.height(12.dp))
        
        // Binary Stream
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            repeat(15) {
                Text(
                    text = if(Random.nextBoolean()) "1" else "0",
                    color = MatrixGreen.copy(alpha = alphaAnim),
                    fontSize = 8.sp,
                    fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
                )
            }
        }
    }
}
