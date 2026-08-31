package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.InstrumentEntity
import com.example.data.model.SiteConfigEntity
import com.example.data.model.TrackEntity
import com.example.ui.theme.GamusaRed
import com.example.ui.theme.SpotifyDarkCard
import com.example.ui.theme.SpotifyDarkElevated
import com.example.ui.theme.parseHexColor

@Composable
fun AdminLoginDialog(
    onDismiss: () -> Unit,
    onLogin: (String, String) -> Boolean
) {
    var adminId by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = SpotifyDarkElevated,
        icon = {
            Icon(
                imageVector = Icons.Default.AdminPanelSettings,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(36.dp)
            )
        },
        title = {
            Text(
                text = "Admin Studio Login",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        },
        text = {
            Column {
                Text(
                    text = "Access catalog management, website live theming, and audio studio controls. (Default ID: 1234, Pass: 1234)",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 13.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = adminId,
                    onValueChange = { adminId = it },
                    label = { Text("Admin ID") },
                    leadingIcon = {
                        Icon(Icons.Default.Person, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = SpotifyDarkCard,
                        unfocusedContainerColor = SpotifyDarkCard,
                        focusedBorderColor = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier.fillMaxWidth().testTag("admin_id_input"),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    leadingIcon = {
                        Icon(Icons.Default.Lock, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                    },
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = SpotifyDarkCard,
                        unfocusedContainerColor = SpotifyDarkCard,
                        focusedBorderColor = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier.fillMaxWidth().testTag("admin_password_input"),
                    singleLine = true
                )

                if (errorMessage != null) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = errorMessage ?: "",
                        color = GamusaRed,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val success = onLogin(adminId, password)
                    if (!success) {
                        errorMessage = "Invalid Admin ID or Password. Default is 1234 / 1234."
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = Color.Black
                ),
                modifier = Modifier.testTag("admin_submit_login_button")
            ) {
                Text("Enter Studio", fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ThemeCustomizerDialog(
    currentConfig: SiteConfigEntity,
    onDismiss: () -> Unit,
    onApplyTheme: (title: String, subtitle: String, heroHeading: String, heroDesc: String, accent: String, border: String, font: String, themeMode: String) -> Unit
) {
    var siteTitle by remember { mutableStateOf(currentConfig.siteTitle) }
    var siteSubtitle by remember { mutableStateOf(currentConfig.siteSubtitle) }
    var heroHeading by remember { mutableStateOf(currentConfig.heroHeading) }
    var heroDescription by remember { mutableStateOf(currentConfig.heroDescription) }
    var accentHex by remember { mutableStateOf(currentConfig.accentColorHex) }
    var borderHex by remember { mutableStateOf(currentConfig.borderAccentHex) }
    var fontStyle by remember { mutableStateOf(currentConfig.fontStyle) }
    var themeMode by remember { mutableStateOf(currentConfig.themeMode) }

    val accentPresets = listOf(
        "#1DB954" to "Spotify Green",
        "#DC2626" to "Gamusa Red",
        "#F59E0B" to "Muga Gold",
        "#6366F1" to "Royal Indigo",
        "#10B981" to "Tea Emerald",
        "#0284C7" to "Brahmaputra",
        "#EC4899" to "Sunset Coral"
    )

    val fontOptions = listOf("Sans", "Serif", "Monospace", "Cursive")
    val themeModes = listOf("Dark", "OLED", "Warm")

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = SpotifyDarkElevated,
        icon = {
            Icon(
                imageVector = Icons.Default.Palette,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(36.dp)
            )
        },
        title = {
            Text(
                text = "Live Theme & Brand Studio",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = "Customize the primary accent color, Gamusa ribbon, font style, canvas mode, and hero branding in real-time.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 12.sp
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Accent Color Presets
                Text(
                    text = "PRIMARY ACCENT COLOR",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(6.dp))
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    accentPresets.forEach { (hex, name) ->
                        val color = parseHexColor(hex)
                        val isSelected = accentHex.equals(hex, true)
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(color)
                                .then(
                                    if (isSelected) Modifier.border(3.dp, Color.White, CircleShape)
                                    else Modifier
                                )
                                .clickable { accentHex = hex },
                            contentAlignment = Alignment.Center
                        ) {}
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Custom Accent Hex input
                OutlinedTextField(
                    value = accentHex,
                    onValueChange = { accentHex = it },
                    label = { Text("Accent Color Hex") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = SpotifyDarkCard,
                        unfocusedContainerColor = SpotifyDarkCard
                    ),
                    modifier = Modifier.fillMaxWidth().testTag("custom_accent_hex_input"),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Font Style Selector
                Text(
                    text = "FONT STYLE",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    fontOptions.forEach { font ->
                        val selected = fontStyle == font
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = if (selected) MaterialTheme.colorScheme.primary else SpotifyDarkCard,
                            modifier = Modifier
                                .weight(1f)
                                .clickable { fontStyle = font }
                        ) {
                            Text(
                                text = font,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = if (selected) Color.Black else Color.White,
                                modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp),
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Canvas Mode Selector
                Text(
                    text = "CANVAS BACKGROUND MODE",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    themeModes.forEach { mode ->
                        val selected = themeMode == mode
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = if (selected) MaterialTheme.colorScheme.primary else SpotifyDarkCard,
                            modifier = Modifier
                                .weight(1f)
                                .clickable { themeMode = mode }
                        ) {
                            Text(
                                text = mode,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = if (selected) Color.Black else Color.White,
                                modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp),
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Brand Titles
                OutlinedTextField(
                    value = siteTitle,
                    onValueChange = { siteTitle = it },
                    label = { Text("Portal Title") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = SpotifyDarkCard,
                        unfocusedContainerColor = SpotifyDarkCard
                    ),
                    modifier = Modifier.fillMaxWidth().testTag("site_title_input"),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = siteSubtitle,
                    onValueChange = { siteSubtitle = it },
                    label = { Text("Portal Subtitle") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = SpotifyDarkCard,
                        unfocusedContainerColor = SpotifyDarkCard
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = heroHeading,
                    onValueChange = { heroHeading = it },
                    label = { Text("Hero Banner Heading") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = SpotifyDarkCard,
                        unfocusedContainerColor = SpotifyDarkCard
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = heroDescription,
                    onValueChange = { heroDescription = it },
                    label = { Text("Hero Banner Description") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = SpotifyDarkCard,
                        unfocusedContainerColor = SpotifyDarkCard
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 3
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onApplyTheme(siteTitle, siteSubtitle, heroHeading, heroDescription, accentHex, borderHex, fontStyle, themeMode)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = Color.Black
                ),
                modifier = Modifier.testTag("apply_theme_button")
            ) {
                Text("Apply Changes", fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InstrumentFormDialog(
    instrument: InstrumentEntity?, // null if new
    onDismiss: () -> Unit,
    onSave: (InstrumentEntity) -> Unit
) {
    var name by remember { mutableStateOf(instrument?.name ?: "") }
    var assameseName by remember { mutableStateOf(instrument?.assameseName ?: "") }
    var category by remember { mutableStateOf(instrument?.category ?: "Wind (সুষিৰ)") }
    var tagline by remember { mutableStateOf(instrument?.tagline ?: "") }
    var about by remember { mutableStateOf(instrument?.about ?: "") }
    var materials by remember { mutableStateOf(instrument?.materials ?: "") }
    var culturalSignificance by remember { mutableStateOf(instrument?.culturalSignificance ?: "") }
    var playingTechnique by remember { mutableStateOf(instrument?.playingTechnique ?: "") }
    var imageUrl by remember { mutableStateOf(instrument?.imageUrl ?: "") }
    var youtubeUrl by remember { mutableStateOf(instrument?.youtubeUrl ?: "") }
    var synthPreset by remember { mutableStateOf(instrument?.synthPreset ?: "pepa") }

    val presetOptions = listOf("pepa", "dhol", "gogona", "tokari", "sutuli", "bhortaal", "bahi")
    val categoryOptions = listOf(
        "Wind (সুষিৰ)",
        "Percussion / Drum (আৱনদ্ধ)",
        "String (তত)",
        "Solid / Metallic (ঘন)"
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = SpotifyDarkElevated,
        title = {
            Text(
                text = if (instrument == null) "Add New Folk Instrument" else "Edit Instrument",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Instrument Name (e.g. Pepa)") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = SpotifyDarkCard,
                        unfocusedContainerColor = SpotifyDarkCard
                    ),
                    modifier = Modifier.fillMaxWidth().testTag("instrument_name_input"),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = assameseName,
                    onValueChange = { assameseName = it },
                    label = { Text("Assamese Name (e.g. পেঁপা)") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = SpotifyDarkCard,
                        unfocusedContainerColor = SpotifyDarkCard
                    ),
                    modifier = Modifier.fillMaxWidth().testTag("instrument_assamese_input"),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = category,
                    onValueChange = { category = it },
                    label = { Text("Category") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = SpotifyDarkCard,
                        unfocusedContainerColor = SpotifyDarkCard
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = tagline,
                    onValueChange = { tagline = it },
                    label = { Text("Short Tagline") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = SpotifyDarkCard,
                        unfocusedContainerColor = SpotifyDarkCard
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = about,
                    onValueChange = { about = it },
                    label = { Text("About the Instrument") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = SpotifyDarkCard,
                        unfocusedContainerColor = SpotifyDarkCard
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 4
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = materials,
                    onValueChange = { materials = it },
                    label = { Text("Crafting Materials") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = SpotifyDarkCard,
                        unfocusedContainerColor = SpotifyDarkCard
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = culturalSignificance,
                    onValueChange = { culturalSignificance = it },
                    label = { Text("Cultural Significance") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = SpotifyDarkCard,
                        unfocusedContainerColor = SpotifyDarkCard
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = playingTechnique,
                    onValueChange = { playingTechnique = it },
                    label = { Text("Playing Technique") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = SpotifyDarkCard,
                        unfocusedContainerColor = SpotifyDarkCard
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = imageUrl,
                    onValueChange = { imageUrl = it },
                    label = { Text("Image URL") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = SpotifyDarkCard,
                        unfocusedContainerColor = SpotifyDarkCard
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = youtubeUrl,
                    onValueChange = { youtubeUrl = it },
                    label = { Text("YouTube Performance Link") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = SpotifyDarkCard,
                        unfocusedContainerColor = SpotifyDarkCard
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = synthPreset,
                    onValueChange = { synthPreset = it },
                    label = { Text("Acoustic Synth Preset (pepa, dhol, gogona, tokari, sutuli, bhortaal, bahi)") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = SpotifyDarkCard,
                        unfocusedContainerColor = SpotifyDarkCard
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val entity = InstrumentEntity(
                        id = instrument?.id ?: 0L,
                        name = name.ifBlank { "New Folk Instrument" },
                        assameseName = assameseName.ifBlank { "বাদ্য" },
                        category = category.ifBlank { "Wind (সুষিৰ)" },
                        tagline = tagline.ifBlank { "Assamese traditional folk instrument" },
                        about = about.ifBlank { "Authentic folk musical instrument of Assam." },
                        materials = materials.ifBlank { "Natural bamboo and organic materials." },
                        culturalSignificance = culturalSignificance.ifBlank { "Celebrated in Assamese folk heritage." },
                        playingTechnique = playingTechnique.ifBlank { "Traditional folk playing technique." },
                        imageUrl = imageUrl.ifBlank { "https://images.unsplash.com/photo-1511192336575-5a79af67a629?w=800" },
                        youtubeUrl = youtubeUrl,
                        synthPreset = synthPreset.ifBlank { "pepa" },
                        isCustom = true
                    )
                    onSave(entity)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = Color.Black
                ),
                modifier = Modifier.testTag("save_instrument_button")
            ) {
                Text("Save Instrument", fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    )
}

@Composable
fun TrackFormDialog(
    track: TrackEntity?,
    onDismiss: () -> Unit,
    onSave: (TrackEntity) -> Unit
) {
    var title by remember { mutableStateOf(track?.title ?: "") }
    var artist by remember { mutableStateOf(track?.artist ?: "") }
    var instrumentName by remember { mutableStateOf(track?.instrumentName ?: "Pepa") }
    var category by remember { mutableStateOf(track?.category ?: "Folk Instrumental") }
    var durationSeconds by remember { mutableStateOf(track?.durationSeconds?.toString() ?: "180") }
    var synthPreset by remember { mutableStateOf(track?.synthPreset ?: "pepa") }
    var coverImageUrl by remember { mutableStateOf(track?.coverImageUrl ?: "") }
    var youtubeUrl by remember { mutableStateOf(track?.youtubeUrl ?: "") }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = SpotifyDarkElevated,
        title = {
            Text(
                text = if (track == null) "Add Track to Playlist" else "Edit Track",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Track Title") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = SpotifyDarkCard,
                        unfocusedContainerColor = SpotifyDarkCard
                    ),
                    modifier = Modifier.fillMaxWidth().testTag("track_title_input"),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = artist,
                    onValueChange = { artist = it },
                    label = { Text("Artist / Troupe") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = SpotifyDarkCard,
                        unfocusedContainerColor = SpotifyDarkCard
                    ),
                    modifier = Modifier.fillMaxWidth().testTag("track_artist_input"),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = instrumentName,
                    onValueChange = { instrumentName = it },
                    label = { Text("Lead Instrument") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = SpotifyDarkCard,
                        unfocusedContainerColor = SpotifyDarkCard
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = durationSeconds,
                    onValueChange = { durationSeconds = it },
                    label = { Text("Duration (Seconds)") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = SpotifyDarkCard,
                        unfocusedContainerColor = SpotifyDarkCard
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = coverImageUrl,
                    onValueChange = { coverImageUrl = it },
                    label = { Text("Cover Image URL") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = SpotifyDarkCard,
                        unfocusedContainerColor = SpotifyDarkCard
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = synthPreset,
                    onValueChange = { synthPreset = it },
                    label = { Text("Synth Preset (pepa, dhol, gogona, tokari, sutuli, bhortaal, bahi)") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = SpotifyDarkCard,
                        unfocusedContainerColor = SpotifyDarkCard
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val entity = TrackEntity(
                        id = track?.id ?: 0L,
                        title = title.ifBlank { "New Folk Track" },
                        artist = artist.ifBlank { "Assam Folk Artists" },
                        instrumentName = instrumentName.ifBlank { "Pepa" },
                        category = category.ifBlank { "Folk Melody" },
                        durationSeconds = durationSeconds.toIntOrNull() ?: 180,
                        synthPreset = synthPreset.ifBlank { "pepa" },
                        coverImageUrl = coverImageUrl.ifBlank { "https://images.unsplash.com/photo-1511192336575-5a79af67a629?w=800" },
                        youtubeUrl = youtubeUrl,
                        isCustom = true
                    )
                    onSave(entity)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = Color.Black
                ),
                modifier = Modifier.testTag("save_track_button")
            ) {
                Text("Save Track", fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    )
}

@Composable
fun ChangeAdminCredentialsDialog(
    currentId: String,
    onDismiss: () -> Unit,
    onSave: (String, String) -> Unit
) {
    var newId by remember { mutableStateOf(currentId) }
    var newPass by remember { mutableStateOf("") }
    var confirmPass by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = SpotifyDarkElevated,
        title = {
            Text(
                text = "Change Admin Credentials",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        },
        text = {
            Column {
                OutlinedTextField(
                    value = newId,
                    onValueChange = { newId = it },
                    label = { Text("New Admin ID") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = SpotifyDarkCard,
                        unfocusedContainerColor = SpotifyDarkCard
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = newPass,
                    onValueChange = { newPass = it },
                    label = { Text("New Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = SpotifyDarkCard,
                        unfocusedContainerColor = SpotifyDarkCard
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = confirmPass,
                    onValueChange = { confirmPass = it },
                    label = { Text("Confirm Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = SpotifyDarkCard,
                        unfocusedContainerColor = SpotifyDarkCard
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                if (error != null) {
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(text = error ?: "", color = GamusaRed, fontSize = 12.sp)
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (newId.isBlank() || newPass.isBlank()) {
                        error = "ID and Password cannot be blank."
                    } else if (newPass != confirmPass) {
                        error = "Passwords do not match."
                    } else {
                        onSave(newId.trim(), newPass.trim())
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = Color.Black
                )
            ) {
                Text("Save Credentials", fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    )
}
