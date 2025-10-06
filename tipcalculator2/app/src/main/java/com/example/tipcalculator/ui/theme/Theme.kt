package com.example.tipcalculator.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val MyLightColors = lightColorScheme(
    primary = Color(0xFF2E7D32),
    secondary = Color(0xFFFF6F00),
    background = Color(0xFFF5F5F5),
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black
)

@Composable
fun TipCalculatorTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = MyLightColors,
        typography = androidx.compose.material3.Typography(),
        content = content
    )
}