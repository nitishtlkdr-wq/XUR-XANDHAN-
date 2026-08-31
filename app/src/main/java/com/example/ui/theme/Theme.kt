package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily

@Composable
fun XurXandhanTheme(
    accentColor: Color = BentoRed600,
    borderAccent: Color = BentoAmber600,
    fontStyle: String = "Sans",
    themeMode: String = "Light",
    content: @Composable () -> Unit
) {
    val isDark = themeMode in listOf("Dark", "OLED", "Warm")

    val colorScheme = if (!isDark) {
        // Bento Grid Crisp Light Theme
        lightColorScheme(
            primary = accentColor,
            onPrimary = Color.White,
            primaryContainer = BentoRed50,
            onPrimaryContainer = BentoRed700,
            secondary = borderAccent,
            onSecondary = Color.White,
            secondaryContainer = BentoAmber100,
            onSecondaryContainer = BentoAmber700,
            tertiary = BentoAmber600,
            onTertiary = Color.White,
            background = BentoWhite,
            onBackground = BentoSlate900,
            surface = BentoWhite,
            onSurface = BentoSlate900,
            surfaceVariant = BentoSlate50,
            onSurfaceVariant = BentoSlate600,
            outline = BentoSlate200,
            outlineVariant = BentoSlate100
        )
    } else {
        val backgroundColor = when (themeMode) {
            "OLED" -> Color(0xFF000000)
            "Warm" -> Color(0xFF1C1412)
            else -> BentoSlate900
        }

        val surfaceColor = when (themeMode) {
            "OLED" -> Color(0xFF0B0F19)
            "Warm" -> Color(0xFF261D19)
            else -> Color(0xFF1E293B)
        }

        darkColorScheme(
            primary = accentColor,
            onPrimary = Color.White,
            primaryContainer = accentColor.copy(alpha = 0.2f),
            onPrimaryContainer = Color.White,
            secondary = borderAccent,
            onSecondary = Color.White,
            secondaryContainer = borderAccent.copy(alpha = 0.2f),
            onSecondaryContainer = Color.White,
            tertiary = BentoAmber500,
            onTertiary = Color.Black,
            background = backgroundColor,
            onBackground = Color.White,
            surface = surfaceColor,
            onSurface = Color.White,
            surfaceVariant = BentoSlate800,
            onSurfaceVariant = BentoSlate400,
            outline = BentoSlate700,
            outlineVariant = BentoSlate800
        )
    }

    val fontFamily = when (fontStyle) {
        "Serif" -> FontFamily.Serif
        "Monospace" -> FontFamily.Monospace
        "Cursive" -> FontFamily.Cursive
        else -> FontFamily.Default
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = getTypographyForFamily(fontFamily),
        content = content
    )
}
