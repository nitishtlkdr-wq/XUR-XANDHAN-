package com.example.ui.theme

import androidx.compose.ui.graphics.Color

// Bento Grid Design System Color Palette
val BentoWhite = Color(0xFFFFFFFF)
val BentoSlate50 = Color(0xFFF8FAFC)
val BentoSlate100 = Color(0xFFF1F5F9)
val BentoSlate200 = Color(0xFFE2E8F0)
val BentoSlate300 = Color(0xFFCBD5E1)
val BentoSlate400 = Color(0xFF94A3B8)
val BentoSlate500 = Color(0xFF64748B)
val BentoSlate600 = Color(0xFF475569)
val BentoSlate700 = Color(0xFF334155)
val BentoSlate800 = Color(0xFF1E293B)
val BentoSlate900 = Color(0xFF0F172A)

// Bento Accents & Soft Pastels
val BentoRed50 = Color(0xFFFEF2F2)
val BentoRed100 = Color(0xFFFEE2E2)
val BentoRed600 = Color(0xFFDC2626)
val BentoRed700 = Color(0xFFB91C1C)

val BentoAmber50 = Color(0xFFFFFBEB)
val BentoAmber100 = Color(0xFFFEF3C7)
val BentoAmber500 = Color(0xFFF59E0B)
val BentoAmber600 = Color(0xFFD97706)
val BentoAmber700 = Color(0xFFB45309)

val BentoEmerald50 = Color(0xFFECFDF5)
val BentoEmerald100 = Color(0xFFD1FAE5)
val BentoEmerald600 = Color(0xFF059669)
val BentoEmerald700 = Color(0xFF047857)

val BentoIndigo50 = Color(0xFFEEF2FF)
val BentoIndigo100 = Color(0xFFE0E7FF)
val BentoIndigo600 = Color(0xFF4F46E5)
val BentoIndigo700 = Color(0xFF4338CA)

val BentoTeal50 = Color(0xFFF0FDFA)
val BentoTeal100 = Color(0xFFCCFBF1)
val BentoTeal600 = Color(0xFF0D9488)
val BentoTeal700 = Color(0xFF0F766E)

val BentoOrange50 = Color(0xFFFFF7ED)
val BentoOrange100 = Color(0xFFFFEDD5)
val BentoOrange600 = Color(0xFFEA580C)
val BentoOrange700 = Color(0xFFC2410C)

val BentoSky50 = Color(0xFFF0F9FF)
val BentoSky100 = Color(0xFFE0F2FE)
val BentoSky600 = Color(0xFF0284C7)
val BentoSky700 = Color(0xFF0369A1)

// Legacy / Fallback Dark Tokens
val SpotifyDarkBackground = Color(0xFF121212)
val SpotifyDarkSurface = Color(0xFF181818)
val SpotifyDarkElevated = Color(0xFF242424)
val SpotifyDarkCard = Color(0xFF282828)
val SpotifyLightGray = Color(0xFFB3B3B3)
val SpotifyWhite = Color(0xFFFFFFFF)

// Assamese Cultural Color Accents
val GamusaRed = Color(0xFFDC2626)
val GamusaDarkRed = Color(0xFF991B1B)
val MugaSilkGold = Color(0xFFF59E0B)
val MugaSilkLight = Color(0xFFFEF3C7)
val AssamTeaGreen = Color(0xFF10B981)
val BrahmaputraBlue = Color(0xFF0284C7)

// Dynamic Color parse helper
fun parseHexColor(hexString: String, fallback: Color = BentoRed600): Color {
    return try {
        val clean = hexString.removePrefix("#")
        val colorInt = when (clean.length) {
            6 -> ("FF$clean").toLong(16)
            8 -> clean.toLong(16)
            else -> return fallback
        }
        Color(colorInt)
    } catch (_: Exception) {
        fallback
    }
}
