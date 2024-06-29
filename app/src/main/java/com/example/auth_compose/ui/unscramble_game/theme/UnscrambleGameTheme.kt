package com.example.auth_compose.ui.unscramble_game.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun UnscrambleGameTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = if (isDarkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colorScheme,
        shapes = UnscrambleGameShapes,
        typography = UnscrambleGameTypography,
        content = content
    )
}