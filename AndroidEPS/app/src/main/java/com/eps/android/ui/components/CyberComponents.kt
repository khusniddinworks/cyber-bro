package com.eps.android.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eps.android.ui.theme.*

@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    borderColor: Color = ElectricCyan,
    onClick: () -> Unit = {},
    content: @Composable ColumnScope.() -> Unit
) {
    val cyberShape = GenericShape { size, _ ->
        val corner = 30f
        moveTo(corner, 0f)
        lineTo(size.width - corner, 0f)
        lineTo(size.width, corner)
        lineTo(size.width, size.height - corner)
        lineTo(size.width - corner, size.height)
        lineTo(corner, size.height)
        lineTo(0f, size.height - corner)
        lineTo(0f, corner)
        close()
    }

    Box(
        modifier = modifier
            .clip(cyberShape)
            .background(Brush.linearGradient(
                colors = listOf(GlassSurface, PanelGlass),
                start = Offset(0f, 0f),
                end = Offset(100f, 100f)
            ))
            .border(1.dp, Brush.linearGradient(
                colors = listOf(borderColor.copy(alpha=0.6f), Color.Transparent, borderColor.copy(alpha=0.3f))
            ), cyberShape)
            .clickable(onClick = onClick)
            .padding(1.dp) // Inner border padding
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            content = content
        )
        
        // Tech Decor Corners
        Canvas(modifier = Modifier.fillMaxSize()) {
            val bracketLen = 30f
            val stroke = 3f
            
            val paintColor = borderColor.copy(alpha = 0.8f)

            // Top Left
            drawLine(paintColor, Offset(0f, 10f), Offset(10f, 0f), stroke) 
            drawLine(paintColor, Offset(0f, 0f), Offset(bracketLen, 0f), stroke)
            drawLine(paintColor, Offset(0f, 0f), Offset(0f, bracketLen), stroke)
            
            // Bottom Right
            drawLine(paintColor, Offset(size.width, size.height), Offset(size.width - bracketLen, size.height), stroke)
            drawLine(paintColor, Offset(size.width, size.height), Offset(size.width, size.height - bracketLen), stroke)
        }
    }
}

@Composable
fun HolographicShield(
    modifier: Modifier = Modifier,
    size: Dp = 240.dp,
    securityLevel: Int = 98,
    statusText: String = "SYSTEM SECURE"
) {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f, targetValue = 360f,
        animationSpec = infiniteRepeatable(tween(8000, easing = LinearEasing)), label = "rot"
    )
    val pulse by infiniteTransition.animateFloat(
        initialValue = 0.8f, targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(1500, easing = FastOutSlowInEasing), RepeatMode.Reverse), label = "pulse"
    )

    val shieldColor = if(securityLevel > 80) ElectricCyan else LaserRed

    Box(contentAlignment = Alignment.Center, modifier = modifier.size(size)) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val center = Offset(size.toPx()/2, size.toPx()/2)
            val radius = size.toPx()/2
            
            // 1. Rotating Cyber Ring
            rotate(rotation, center) {
                drawCircle(
                    brush = Brush.sweepGradient(listOf(Color.Transparent, shieldColor, Color.Transparent)),
                    radius = radius - 10f,
                    style = Stroke(width = 4f, cap = StrokeCap.Round)
                )
            }

            // 2. Static HUD Circle
            drawCircle(
                color = shieldColor.copy(alpha = 0.1f),
                radius = radius - 30f
            )

            // 3. Dynamic Arc (Security Level)
            drawArc(
                brush = Brush.verticalGradient(listOf(ShieldGradientStart, ShieldGradientEnd)),
                startAngle = -90f,
                sweepAngle = (3.6f * securityLevel),
                useCenter = false,
                style = Stroke(width = 15f, cap = StrokeCap.Round),
                size = Size(radius*2 - 60f, radius*2 - 60f),
                topLeft = Offset(30f, 30f)
            )
        }

        // 4. Center Hologram Text
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "$securityLevel%",
                color = TextHolo,
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                style = androidx.compose.ui.text.TextStyle(
                    shadow = androidx.compose.ui.graphics.Shadow(shieldColor, blurRadius = 20f)
                )
            )
            Text(
                text = "SECURITY",
                color = shieldColor,
                fontSize = 12.sp,
                letterSpacing = 2.sp
            )
        }
    }
}

@Composable
fun CyberSecurityOrb(
    modifier: Modifier = Modifier,
    score: Int,
    isResumed: Boolean
) {
    val infiniteTransition = rememberInfiniteTransition(label = "orb")
    
    // Complex rotations
    val rotationInner by if (isResumed) {
        infiniteTransition.animateFloat(
            initialValue = 0f, targetValue = 360f,
            animationSpec = infiniteRepeatable(tween(12000, easing = LinearEasing)), label = ""
        )
    } else remember { mutableStateOf(0f) }

    val rotationOuter by if (isResumed) {
        infiniteTransition.animateFloat(
            initialValue = 360f, targetValue = 0f,
            animationSpec = infiniteRepeatable(tween(20000, easing = LinearEasing)), label = ""
        )
    } else remember { mutableStateOf(0f) }

    val pulseScale by if (isResumed) {
        infiniteTransition.animateFloat(
            initialValue = 0.95f, targetValue = 1.05f,
            animationSpec = infiniteRepeatable(tween(2000, easing = FastOutSlowInEasing), RepeatMode.Reverse), label = ""
        )
    } else remember { mutableStateOf(1f) }

    val orbColor = when {
        score >= 90 -> GoldPremium
        score >= 70 -> ElectricCyan
        else -> LaserRed
    }

    Box(contentAlignment = Alignment.Center, modifier = modifier.size(280.dp)) {
        // Ambient Glow
        Box(
            modifier = Modifier
                .size(220.dp)
                .background(orbColor.copy(alpha = 0.1f * pulseScale), CircleShape)
                .blur(50.dp)
        )

        // Outer Tech Rings
        Canvas(modifier = Modifier.size(260.dp)) {
            val strokeWidth = 1.5.dp.toPx()
            rotate(rotationOuter) {
                // Dashed outer ring
                drawArc(
                    color = orbColor.copy(alpha = 0.2f),
                    startAngle = 0f,
                    sweepAngle = 360f,
                    useCenter = false,
                    style = Stroke(
                        width = strokeWidth,
                        cap = StrokeCap.Round,
                        pathEffect = androidx.compose.ui.graphics.PathEffect.dashPathEffect(floatArrayOf(10f, 20f), 0f)
                    )
                )
                
                // Segments
                for (i in 0 until 4) {
                    drawArc(
                        color = orbColor.copy(alpha = 0.5f),
                        startAngle = i * 90f + 15f,
                        sweepAngle = 60f,
                        useCenter = false,
                        style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
                    )
                }
            }
        }

        // Inner HUD Rings
        Canvas(modifier = Modifier.size(210.dp)) {
            rotate(rotationInner) {
                drawArc(
                    color = orbColor.copy(alpha = 0.4f),
                    startAngle = 45f,
                    sweepAngle = 270f,
                    useCenter = false,
                    style = Stroke(width = 1.dp.toPx())
                )
                
                // Tech Bits
                val radius = size.minDimension / 2
                for (i in 0 until 8) {
                    val angle = i * 45f
                    val x = center.x + radius * kotlin.math.cos(Math.toRadians(angle.toDouble())).toFloat()
                    val y = center.y + radius * kotlin.math.sin(Math.toRadians(angle.toDouble())).toFloat()
                    drawCircle(orbColor.copy(alpha = 0.6f), radius = 3.dp.toPx(), center = Offset(x, y))
                }
            }
        }

        // Central Core
        Surface(
            modifier = Modifier.size(170.dp),
            shape = CircleShape,
            color = Color(0xFF030508).copy(alpha = 0.9f),
            border = androidx.compose.foundation.BorderStroke(2.dp, Brush.linearGradient(listOf(orbColor, Color.Transparent, orbColor)))
        ) {
            Box(contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "$score%",
                        fontSize = 58.sp,
                        color = orbColor,
                        fontWeight = FontWeight.ExtraLight,
                        style = MaterialTheme.typography.displayLarge
                    )
                    Text(
                        text = if (score >= 90) "SECURE" else if (score >= 70) "PROTECTED" else "DANGER",
                        fontSize = 10.sp,
                        color = orbColor.copy(alpha = 0.7f),
                        letterSpacing = 4.sp,
                        fontWeight = FontWeight.Black
                    )
                }
            }
        }
    }
}
