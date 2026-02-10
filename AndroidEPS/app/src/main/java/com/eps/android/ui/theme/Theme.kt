package com.eps.android.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = ElectricCyan,
    secondary = ShieldGradientEnd,
    tertiary = MatrixGreen,
    background = VoidBlack,
    surface = CyberDark,
    onPrimary = CyberDark,
    onSecondary = TextHolo,
    onBackground = TextHolo,
    onSurface = TextHolo,
    error = LaserRed
)

private val LightColorScheme = androidx.compose.material3.lightColorScheme(
    primary = ElectricCyan,
    secondary = ShieldGradientEnd,
    tertiary = MatrixGreen,
    background = Color(0xFFF0F2F5),
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    error = LaserRed
)

@Composable
fun HACKDEFENDERTheme(
    useDarkTheme: Boolean = true,
    content: @Composable () -> Unit
) {
    val colors = if (useDarkTheme) DarkColorScheme else LightColorScheme
    
    MaterialTheme(
        colorScheme = colors,
        content = content
    )
}
