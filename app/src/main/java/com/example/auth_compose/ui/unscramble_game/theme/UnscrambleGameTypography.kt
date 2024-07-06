package com.example.auth_compose.ui.unscramble_game.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.auth_compose.ui.woof.theme.Montserrat

val UnscrambleGameTypography = Typography(
    headlineSmall = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 24.sp,
        letterSpacing = 0.5.sp,
    ),
    headlineMedium = TextStyle(
        fontWeight = FontWeight.W600,
        fontSize = 28.sp,
        letterSpacing = 0.5.sp,
    ),
    labelSmall = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
    ),
    bodyLarge = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
    ),
)