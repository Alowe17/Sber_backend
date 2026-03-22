package com.example.myapplication.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val SberColorScheme = lightColorScheme(
    primary = SberGreen,
    secondary = SberBlack,
    background = SberWhite,
    surface = SberWhite,
    onPrimary = SberWhite,
    onSecondary = SberWhite,
    onBackground = SberBlack,
    onSurface = SberBlack
)

@Composable
fun MyApplicationTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = SberColorScheme,
        typography = Typography, // Убедитесь, что файл Typography.kt существует (стандартный для Compose)
        content = content
    )
}