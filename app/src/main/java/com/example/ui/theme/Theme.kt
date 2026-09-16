package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = NepalBlue,
    onPrimary = Color.White,
    primaryContainer = NepalBlueContainer,
    onPrimaryContainer = NepalOnBlueContainer,
    secondary = NepalRed,
    onSecondary = Color.White,
    secondaryContainer = NepalRedContainer,
    onSecondaryContainer = NepalOnRedContainer,
    tertiary = NepalBlueLight,
    onTertiary = Color.White,
    background = SlateBackground,
    onBackground = DarkNavyText,
    surface = SlateSurface,
    onSurface = DarkNavyText,
    surfaceVariant = Color(0xFFF1F5F9),
    onSurfaceVariant = SlateMutedText,
    outline = SlateCardBorder,
    outlineVariant = Color(0xFFE2E8F0)
)

private val DarkColorScheme = darkColorScheme(
    primary = NepalBlueLight,
    onPrimary = Color.White,
    primaryContainer = Color(0xFF1E3A8A),
    onPrimaryContainer = Color(0xFFDBEAFE),
    secondary = Color(0xFFEF4444),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFF7F1D1D),
    onSecondaryContainer = Color(0xFFFEE2E2),
    tertiary = Color(0xFF60A5FA),
    onTertiary = Color.White,
    background = Color(0xFF0F172A),
    onBackground = Color(0xFFF8FAFC),
    surface = Color(0xFF1E293B),
    onSurface = Color(0xFFF8FAFC),
    surfaceVariant = Color(0xFF334155),
    onSurfaceVariant = Color(0xFFCBD5E1),
    outline = Color(0xFF475569),
    outlineVariant = Color(0xFF334155)
)


@Composable
fun NepalUtilityHubTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

