package com.eps.android.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.eps.android.ui.theme.ElectricCyan
import com.eps.android.ui.theme.MatrixGreen
import kotlin.random.Random

@Composable
fun CyberScanningEffect(isEncryptMode: Boolean) {
    val infiniteTransition = rememberInfiniteTransition(label = "scan")
    val offsetY by infiniteTransition.animateFloat(
        initialValue = 0f, targetValue = 100f,
        animationSpec = infiniteRepeatable(tween(1500, easing = LinearEasing), RepeatMode.Reverse),
        label = "offset"
    )

    Canvas(modifier = Modifier.fillMaxWidth().height(100.dp).padding(vertical = 10.dp)) {
        val scanColor = if (isEncryptMode) ElectricCyan else MatrixGreen
        val lineY = size.height * (offsetY / 100f)
        
        // Draw Scan Line
        drawLine(
            color = scanColor,
            start = Offset(0f, lineY),
            end = Offset(size.width, lineY),
            strokeWidth = 4f
        )
        
        // Draw Random "Data Bits"
        for(i in 0..20) {
            val x = Random.nextFloat() * size.width
            val y = Random.nextFloat() * size.height
            drawCircle(color = scanColor.copy(alpha=0.5f), radius = 2f, center = Offset(x, y))
        }
    }
}
