package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class InstrumentCategory(val displayName: String, val assameseName: String) {
    ALL("All Categories", "সকলো"),
    AEROPHONE("Wind (সুষিৰ)", "সুষিৰ বাদ্য"),
    MEMBRANOPHONE("Percussion / Drum (আৱনদ্ধ)", "আৱনদ্ধ বাদ্য"),
    CHORDOPHONE("String (তত)", "তত বাদ্য"),
    IDIOPHONE("Solid / Metallic (ঘন)", "ঘন বাদ্য")
}

@Entity(tableName = "instruments")
data class InstrumentEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val assameseName: String,
    val category: String,
    val tagline: String,
    val about: String,
    val materials: String,
    val culturalSignificance: String,
    val playingTechnique: String,
    val imageUrl: String,
    val youtubeUrl: String,
    val synthPreset: String = "pepa",
    val isFavorite: Boolean = false,
    val isCustom: Boolean = false
)

@Entity(tableName = "tracks")
data class TrackEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val artist: String,
    val instrumentName: String,
    val category: String,
    val durationSeconds: Int,
    val audioUrl: String = "",
    val synthPreset: String = "pepa",
    val coverImageUrl: String = "",
    val youtubeUrl: String = "",
    val isCustom: Boolean = false
)

@Entity(tableName = "site_config")
data class SiteConfigEntity(
    @PrimaryKey
    val id: Int = 1,
    val siteTitle: String = "XUR-XANDHAN",
    val siteSubtitle: String = "সুৰ সন্ধান • Assamese Folk Heritage Audio & Instrument Portal",
    val heroHeading: String = "Discover the Soulful Folk Instruments of Assam",
    val heroDescription: String = "Immerse in the timeless acoustic heritage of the Brahmaputra valley. Listen to authentic synthesized sounds, explore deep cultural stories, and search with AI.",
    val accentColorHex: String = "#DC2626", // Bento Gamusa Red default, customizable
    val borderAccentHex: String = "#D97706", // Bento Amber
    val fontStyle: String = "Sans", // Sans, Serif, Monospace, Cursive
    val themeMode: String = "Light", // Light (Bento Grid), Dark, OLED, Warm
    val adminId: String = "1234",
    val adminPass: String = "1234"
)
