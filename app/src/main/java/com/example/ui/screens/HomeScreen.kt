package com.example.ui.screens

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.MusicVideo
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SmartDisplay
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.ai.FolkAiAssistant
import com.example.data.model.InstrumentEntity
import com.example.data.model.SiteConfigEntity
import com.example.data.model.TrackEntity
import com.example.ui.theme.BentoAmber100
import com.example.ui.theme.BentoAmber500
import com.example.ui.theme.BentoAmber700
import com.example.ui.theme.BentoEmerald100
import com.example.ui.theme.BentoEmerald700
import com.example.ui.theme.BentoIndigo100
import com.example.ui.theme.BentoIndigo700
import com.example.ui.theme.BentoOrange100
import com.example.ui.theme.BentoOrange700
import com.example.ui.theme.BentoRed100
import com.example.ui.theme.BentoRed50
import com.example.ui.theme.BentoRed600
import com.example.ui.theme.BentoRed700
import com.example.ui.theme.BentoSky100
import com.example.ui.theme.BentoSky700
import com.example.ui.theme.BentoSlate400
import com.example.ui.theme.BentoSlate600
import com.example.ui.theme.BentoSlate900
import com.example.ui.theme.BentoTeal100
import com.example.ui.theme.BentoTeal700

@Composable
fun HomeScreen(
    siteConfig: SiteConfigEntity,
    instruments: List<InstrumentEntity>,
    tracks: List<TrackEntity>,
    onSelectInstrument: (InstrumentEntity) -> Unit,
    onPlayTrack: (TrackEntity) -> Unit,
    onPlaySynth: (String) -> Unit,
    onOpenSearch: () -> Unit,
    onOpenAiAssistant: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val featuredInstrument = instruments.firstOrNull { it.synthPreset == "pepa" } ?: instruments.firstOrNull()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(bottom = 110.dp)
    ) {
        // Bento Search Bar Trigger
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Surface(
                    shape = RoundedCornerShape(50.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(50.dp))
                        .clickable { onOpenSearch() }
                        .testTag("bento_search_trigger")
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = "Ask Xur-AI about instruments...",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontSize = 14.sp
                            )
                        }

                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .clickable { onOpenAiAssistant() }
                                .testTag("bento_mic_trigger"),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Mic,
                                contentDescription = "AI Voice Search",
                                tint = BentoRed600,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }
        }

        // BENTO GRID MAIN SECTION
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // 1. Bento Large Featured Card (Pepa / Hero Instrument)
                featuredInstrument?.let { featured ->
                    BentoFeaturedCard(
                        instrument = featured,
                        onInspect = { onSelectInstrument(featured) },
                        onListenSound = { onPlaySynth(featured.synthPreset) }
                    )
                }

                // 2. Bento 2-Column Grid of Folk Instruments
                val gridInstruments = instruments.filter { it.id != (featuredInstrument?.id ?: -1L) }.take(6)
                val pairs = gridInstruments.chunked(2)

                pairs.forEach { pair ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        BentoInstrumentGridItem(
                            instrument = pair[0],
                            modifier = Modifier.weight(1f),
                            onClick = { onSelectInstrument(pair[0]) },
                            onPlaySynth = { onPlaySynth(pair[0].synthPreset) }
                        )

                        if (pair.size > 1) {
                            BentoInstrumentGridItem(
                                instrument = pair[1],
                                modifier = Modifier.weight(1f),
                                onClick = { onSelectInstrument(pair[1]) },
                                onPlaySynth = { onPlaySynth(pair[1].synthPreset) }
                            )
                        } else {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }

                // 3. Bento Col-span 2 Dark Slate Folk Playlist Card
                BentoDarkPlaylistCard(
                    tracksCount = tracks.size,
                    onPlayPlaylist = {
                        tracks.firstOrNull()?.let { onPlayTrack(it) }
                    }
                )
            }
        }

        // Quick Acoustic Synthesizer Sound Board
        item {
            Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 20.dp, bottom = 8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Instant Acoustic Tones",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Tap to synthesize",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = BentoRed600
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                val quickSynths = listOf(
                    Triple("Pepa (পেঁপা)", "pepa", BentoRed600),
                    Triple("Dhol (ঢোল)", "dhol", BentoAmber500),
                    Triple("Gogona (গগনা)", "gogona", BentoEmerald700),
                    Triple("Tokari (টোকোৰী)", "tokari", BentoIndigo700),
                    Triple("Sutuli (সুতুলি)", "sutuli", BentoTeal700),
                    Triple("Bhor Taal (ভোৰতাল)", "bhortaal", BentoOrange700),
                    Triple("Bahi (বাঁহী)", "bahi", BentoSky700)
                )

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(quickSynths) { (name, preset, tint) ->
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .clickable { onPlaySynth(preset) }
                                .testTag("quick_synth_$preset")
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(8.dp)
                                        .clip(CircleShape)
                                        .background(tint)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = name,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Icon(
                                    imageVector = Icons.Default.GraphicEq,
                                    contentDescription = null,
                                    tint = tint,
                                    modifier = Modifier.size(13.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        // Curated Track List Section
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Curated Folk Audio Archive",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "${tracks.size} tracks",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                tracks.forEach { track ->
                    BentoTrackRowItem(
                        track = track,
                        onPlay = { onPlayTrack(track) },
                        onWatchYouTube = {
                            val yt = track.youtubeUrl.ifBlank { "${track.title} Assam folk music" }
                            FolkAiAssistant.openYoutube(context, yt)
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }

        // Bento Cultural Masterclasses Card
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)) {
                Surface(
                    shape = RoundedCornerShape(24.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(24.dp))
                        .clickable {
                            FolkAiAssistant.openYoutube(context, "Assam folk musical instruments masterclass documentary")
                        }
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .shadow(2.dp, RoundedCornerShape(14.dp))
                                .clip(RoundedCornerShape(14.dp))
                                .background(BentoRed600),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.SmartDisplay,
                                contentDescription = "YouTube",
                                tint = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(14.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Bihu & Sattriya Video Archives",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "Watch master craftsmen making Pepa, Dhol & singing Tokari Geet",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontSize = 11.sp
                            )
                        }
                    }
                }
            }
        }

        // Signature Footer Mark (MADE BY AETS PVT.LTD)
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "MADE BY AETS PVT.LTD ( DESIGN BY NITISH KUMAR TALUKDAR )",
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = (-0.2).sp,
                    color = BentoSlate400
                )
            }
        }
    }
}

@Composable
fun BentoFeaturedCard(
    instrument: InstrumentEntity,
    onInspect: () -> Unit,
    onListenSound: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = BentoRed50),
        border = androidx.compose.foundation.BorderStroke(1.dp, BentoRed100),
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .clickable { onInspect() }
            .testTag("bento_featured_card")
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            // Background Decorative Watermark Icon
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .size(110.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.MusicVideo,
                    contentDescription = null,
                    tint = BentoRed600.copy(alpha = 0.08f),
                    modifier = Modifier.size(110.dp)
                )
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Featured Tag Pill
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = Color.White.copy(alpha = 0.85f),
                    border = androidx.compose.foundation.BorderStroke(0.5.dp, BentoRed100)
                ) {
                    Text(
                        text = "FEATURED",
                        color = BentoRed600,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 1.2.sp,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }

                Column {
                    Text(
                        text = "${instrument.name} (${instrument.assameseName})",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Black,
                        color = BentoSlate900,
                        lineHeight = 26.sp
                    )
                    Text(
                        text = instrument.tagline.ifBlank { "The buffalo horn trumpet of the Brahmaputra." },
                        style = MaterialTheme.typography.bodySmall,
                        color = BentoSlate600,
                        fontSize = 12.sp,
                        lineHeight = 17.sp,
                        modifier = Modifier.fillMaxWidth(0.75f)
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Listen Sound Bento Pill Button
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = BentoRed600,
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .clickable { onListenSound() }
                        .testTag("bento_listen_sound_button")
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "Play Sound",
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Listen Sound",
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BentoInstrumentGridItem(
    instrument: InstrumentEntity,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    onPlaySynth: () -> Unit
) {
    // Dynamic soft badge theme based on preset
    val (iconBg, iconTint, subtitleText) = when (instrument.synthPreset) {
        "dhol" -> Triple(BentoAmber100, BentoAmber700, "Rhythmic Heartbeat")
        "gogona" -> Triple(BentoEmerald100, BentoEmerald700, "Bamboo Harp")
        "tokari" -> Triple(BentoIndigo100, BentoIndigo700, "Sacred Folk Lute")
        "sutuli" -> Triple(BentoTeal100, BentoTeal700, "Clay Ocarina")
        "bhortaal" -> Triple(BentoOrange100, BentoOrange700, "Resonant Cymbals")
        "bahi" -> Triple(BentoSky100, BentoSky700, "Bamboo Flute")
        else -> Triple(BentoRed100, BentoRed700, instrument.category)
    }

    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
        modifier = modifier
            .clip(RoundedCornerShape(24.dp))
            .clickable { onClick() }
            .testTag("bento_card_${instrument.name.lowercase().replace(" ", "_")}")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                // Soft colored squircle icon container
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(iconBg),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.GraphicEq,
                        contentDescription = instrument.name,
                        tint = iconTint,
                        modifier = Modifier.size(20.dp)
                    )
                }

                // Quick Mini Synth tap button
                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .clip(CircleShape)
                        .background(iconBg.copy(alpha = 0.6f))
                        .clickable { onPlaySynth() }
                        .testTag("bento_synth_${instrument.synthPreset}"),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Synth",
                        tint = iconTint,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            Column {
                Text(
                    text = "${instrument.name} (${instrument.assameseName})",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1
                )
                Text(
                    text = subtitleText,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 11.sp,
                    maxLines = 1
                )
            }
        }
    }
}

@Composable
fun BentoDarkPlaylistCard(
    tracksCount: Int,
    onPlayPlaylist: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = BentoSlate900),
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .clickable { onPlayPlaylist() }
            .testTag("bento_playlist_card")
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = "Folk Playlist",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "By Nitish Kumar Talukdar",
                    fontSize = 11.sp,
                    color = BentoSlate400,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Pulsing Equalizer Indicator Pills
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    PulsingPill(color = BentoRed600, minHeight = 12f, maxHeight = 22f, durationMs = 380)
                    PulsingPill(color = BentoAmber500, minHeight = 18f, maxHeight = 10f, durationMs = 300)
                    PulsingPill(color = Color(0xFFF87171), minHeight = 8f, maxHeight = 24f, durationMs = 450)
                }
            }

            // Big circular white play button
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .clickable { onPlayPlaylist() }
                    .testTag("bento_play_playlist_button"),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Play Playlist",
                    tint = BentoSlate900,
                    modifier = Modifier.size(26.dp)
                )
            }
        }
    }
}

@Composable
fun PulsingPill(
    color: Color,
    minHeight: Float,
    maxHeight: Float,
    durationMs: Int
) {
    val transition = rememberInfiniteTransition(label = "pulse")
    val h by transition.animateFloat(
        initialValue = minHeight,
        targetValue = maxHeight,
        animationSpec = infiniteRepeatable(tween(durationMs, easing = LinearEasing), RepeatMode.Reverse),
        label = "h"
    )

    Box(
        modifier = Modifier
            .width(6.dp)
            .height(h.dp)
            .clip(RoundedCornerShape(3.dp))
            .background(color)
    )
}

@Composable
fun BentoTrackRowItem(
    track: TrackEntity,
    onPlay: () -> Unit,
    onWatchYouTube: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surfaceVariant,
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable { onPlay() }
            .testTag("track_row_${track.id}")
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(BentoSlate900),
                    contentAlignment = Alignment.Center
                ) {
                    if (track.coverImageUrl.isNotBlank()) {
                        AsyncImage(
                            model = track.coverImageUrl,
                            contentDescription = track.title,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.size(44.dp)
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.MusicNote,
                            contentDescription = null,
                            tint = BentoRed600,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = track.title,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 1
                    )
                    Text(
                        text = "${track.artist} • ${track.instrumentName}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 11.sp,
                        maxLines = 1
                    )
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(CircleShape)
                        .background(BentoRed600.copy(alpha = 0.12f))
                        .clickable { onPlay() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Play Track",
                        tint = BentoRed600,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(CircleShape)
                        .background(BentoAmber500.copy(alpha = 0.15f))
                        .clickable { onWatchYouTube() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.SmartDisplay,
                        contentDescription = "Watch on YouTube",
                        tint = BentoAmber700,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}
