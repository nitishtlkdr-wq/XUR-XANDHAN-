package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LibraryMusic
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.AdminPanelSettings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LibraryMusic
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.local.AppDatabase
import com.example.data.repository.AppRepository
import com.example.ui.components.AdminLoginDialog
import com.example.ui.components.AiAssistantSheet
import com.example.ui.components.GamusaHeader
import com.example.ui.components.InstrumentDetailSheet
import com.example.ui.components.InstrumentFormDialog
import com.example.ui.components.MiniPlayerBar
import com.example.ui.components.NowPlayingDialog
import com.example.ui.components.ThemeCustomizerDialog
import com.example.ui.components.TrackFormDialog
import com.example.ui.screens.AdminStudioScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.PlaylistScreen
import com.example.ui.screens.SearchScreen
import com.example.ui.theme.GamusaRed
import com.example.ui.theme.SpotifyDarkElevated
import com.example.ui.theme.XurXandhanTheme
import com.example.ui.theme.parseHexColor
import com.example.ui.viewmodel.MainViewModel
import com.example.ui.viewmodel.MainViewModelFactory

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val scope = rememberCoroutineScope()
            val database = remember { AppDatabase.getDatabase(applicationContext, scope) }
            val repository = remember {
                AppRepository(
                    instrumentDao = database.instrumentDao(),
                    trackDao = database.trackDao(),
                    siteConfigDao = database.siteConfigDao()
                )
            }

            val viewModel: MainViewModel = viewModel(
                factory = MainViewModelFactory(application, repository)
            )

            XurXandhanApp(viewModel = viewModel)
        }
    }
}

@Composable
fun XurXandhanApp(viewModel: MainViewModel) {
    val siteConfig by viewModel.siteConfig.collectAsStateWithLifecycle()
    val instruments by viewModel.allInstruments.collectAsStateWithLifecycle()
    val tracks by viewModel.allTracks.collectAsStateWithLifecycle()
    val playbackState by viewModel.playbackState.collectAsStateWithLifecycle()
    val currentTab by viewModel.currentTab.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val selectedCategory by viewModel.selectedCategory.collectAsStateWithLifecycle()

    val selectedInstrumentForDetail by viewModel.selectedInstrumentForDetail.collectAsStateWithLifecycle()
    val isNowPlayingExpanded by viewModel.isNowPlayingExpanded.collectAsStateWithLifecycle()
    val isAiAssistantOpen by viewModel.isAiAssistantOpen.collectAsStateWithLifecycle()
    val aiMessages by viewModel.aiMessages.collectAsStateWithLifecycle()
    val isAiThinking by viewModel.isAiThinking.collectAsStateWithLifecycle()

    val isAdminLoggedIn by viewModel.isAdminLoggedIn.collectAsStateWithLifecycle()
    val showAdminLoginDialog by viewModel.showAdminLoginDialog.collectAsStateWithLifecycle()
    val showThemeCustomizer by viewModel.showThemeCustomizer.collectAsStateWithLifecycle()
    val showInstrumentFormDialog by viewModel.showInstrumentFormDialog.collectAsStateWithLifecycle()
    val showTrackFormDialog by viewModel.showTrackFormDialog.collectAsStateWithLifecycle()
    val statusMessage by viewModel.statusMessage.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(statusMessage) {
        statusMessage?.let { msg ->
            snackbarHostState.showSnackbar(msg)
            viewModel.clearStatusMessage()
        }
    }

    val primaryAccent = parseHexColor(siteConfig.accentColorHex, Color(0xFF1DB954))
    val borderAccent = parseHexColor(siteConfig.borderAccentHex, GamusaRed)

    XurXandhanTheme(
        accentColor = primaryAccent,
        borderAccent = borderAccent,
        fontStyle = siteConfig.fontStyle,
        themeMode = siteConfig.themeMode
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                GamusaHeader(
                    title = siteConfig.siteTitle,
                    subtitle = siteConfig.siteSubtitle,
                    isAdminLoggedIn = isAdminLoggedIn,
                    onAdminClick = {
                        if (!isAdminLoggedIn) {
                            viewModel.setShowAdminLoginDialog(true)
                        } else {
                            viewModel.setTab("admin")
                        }
                    },
                    onCustomizeClick = {
                        viewModel.setShowThemeCustomizer(true)
                    },
                    onAiAssistantClick = {
                        viewModel.setAiAssistantOpen(true)
                    }
                )
            },
            bottomBar = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .windowInsetsPadding(WindowInsets.navigationBars)
                ) {
                    // Floating Spotify Mini Player Bar
                    if (playbackState.currentTrackId != null || playbackState.isPlaying) {
                        MiniPlayerBar(
                            state = playbackState,
                            onTogglePlayPause = { viewModel.togglePlayPause() },
                            onNext = { viewModel.nextTrack() },
                            onClick = { viewModel.setNowPlayingExpanded(true) }
                        )
                    }

                    // Spotify-style Bottom Navigation Bar
                    NavigationBar(
                        containerColor = SpotifyDarkElevated,
                        contentColor = Color.White
                    ) {
                        NavigationBarItem(
                            selected = currentTab == "explore",
                            onClick = { viewModel.setTab("explore") },
                            icon = {
                                Icon(
                                    imageVector = if (currentTab == "explore") Icons.Default.Home else Icons.Outlined.Home,
                                    contentDescription = "Explore"
                                )
                            },
                            label = { Text("Explore", fontSize = 11.sp, fontWeight = FontWeight.SemiBold) },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = MaterialTheme.colorScheme.primary,
                                selectedTextColor = MaterialTheme.colorScheme.primary,
                                indicatorColor = Color.Transparent,
                                unselectedIconColor = Color.LightGray,
                                unselectedTextColor = Color.LightGray
                            ),
                            modifier = Modifier.testTag("nav_explore")
                        )

                        NavigationBarItem(
                            selected = currentTab == "search",
                            onClick = { viewModel.setTab("search") },
                            icon = {
                                Icon(
                                    imageVector = if (currentTab == "search") Icons.Default.Search else Icons.Outlined.Search,
                                    contentDescription = "Search"
                                )
                            },
                            label = { Text("Search", fontSize = 11.sp, fontWeight = FontWeight.SemiBold) },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = MaterialTheme.colorScheme.primary,
                                selectedTextColor = MaterialTheme.colorScheme.primary,
                                indicatorColor = Color.Transparent,
                                unselectedIconColor = Color.LightGray,
                                unselectedTextColor = Color.LightGray
                            ),
                            modifier = Modifier.testTag("nav_search")
                        )

                        NavigationBarItem(
                            selected = currentTab == "playlist",
                            onClick = { viewModel.setTab("playlist") },
                            icon = {
                                Icon(
                                    imageVector = if (currentTab == "playlist") Icons.Default.LibraryMusic else Icons.Outlined.LibraryMusic,
                                    contentDescription = "Folk Playlist"
                                )
                            },
                            label = { Text("Playlist", fontSize = 11.sp, fontWeight = FontWeight.SemiBold) },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = MaterialTheme.colorScheme.primary,
                                selectedTextColor = MaterialTheme.colorScheme.primary,
                                indicatorColor = Color.Transparent,
                                unselectedIconColor = Color.LightGray,
                                unselectedTextColor = Color.LightGray
                            ),
                            modifier = Modifier.testTag("nav_playlist")
                        )

                        NavigationBarItem(
                            selected = currentTab == "admin",
                            onClick = { viewModel.setTab("admin") },
                            icon = {
                                Icon(
                                    imageVector = if (currentTab == "admin") Icons.Default.AdminPanelSettings else Icons.Outlined.AdminPanelSettings,
                                    contentDescription = "Admin Studio"
                                )
                            },
                            label = { Text("Studio", fontSize = 11.sp, fontWeight = FontWeight.SemiBold) },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = MaterialTheme.colorScheme.primary,
                                selectedTextColor = MaterialTheme.colorScheme.primary,
                                indicatorColor = Color.Transparent,
                                unselectedIconColor = Color.LightGray,
                                unselectedTextColor = Color.LightGray
                            ),
                            modifier = Modifier.testTag("nav_admin")
                        )
                    }
                }
            },
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState) { data ->
                    Snackbar(
                        snackbarData = data,
                        containerColor = SpotifyDarkElevated,
                        contentColor = Color.White,
                        actionColor = MaterialTheme.colorScheme.primary
                    )
                }
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(MaterialTheme.colorScheme.background)
            ) {
                when (currentTab) {
                    "explore" -> HomeScreen(
                        siteConfig = siteConfig,
                        instruments = instruments,
                        tracks = tracks,
                        onSelectInstrument = { viewModel.selectInstrumentForDetail(it) },
                        onPlayTrack = { viewModel.playTrackItem(it) },
                        onPlaySynth = { viewModel.playInstrumentAcoustic(it) },
                        onOpenSearch = { viewModel.setTab("search") },
                        onOpenAiAssistant = { viewModel.setAiAssistantOpen(true) }
                    )

                    "search" -> SearchScreen(
                        instruments = instruments,
                        searchQuery = searchQuery,
                        selectedCategory = selectedCategory,
                        onSearchChange = { viewModel.setSearchQuery(it) },
                        onCategoryChange = { viewModel.setSelectedCategory(it) },
                        onSelectInstrument = { viewModel.selectInstrumentForDetail(it) },
                        onPlaySynth = { viewModel.playInstrumentAcoustic(it) },
                        onOpenAiAssistant = { viewModel.setAiAssistantOpen(true) }
                    )

                    "playlist" -> PlaylistScreen(
                        tracks = tracks,
                        playbackState = playbackState,
                        onPlayTrack = { viewModel.playTrackItem(it) },
                        onTogglePlayPause = { viewModel.togglePlayPause() },
                        onToggleShuffle = { viewModel.toggleShuffle() },
                        onPlaySynth = { viewModel.playInstrumentAcoustic(it) }
                    )

                    "admin" -> AdminStudioScreen(
                        siteConfig = siteConfig,
                        instruments = instruments,
                        tracks = tracks,
                        isAdminLoggedIn = isAdminLoggedIn,
                        onLoginClick = { viewModel.setShowAdminLoginDialog(true) },
                        onLogoutClick = { viewModel.logoutAdmin() },
                        onOpenThemeCustomizer = { viewModel.setShowThemeCustomizer(true) },
                        onAddInstrument = { viewModel.openInstrumentForm(null) },
                        onEditInstrument = { viewModel.openInstrumentForm(it) },
                        onDeleteInstrument = { viewModel.deleteInstrument(it) },
                        onAddTrack = { viewModel.openTrackForm(null) },
                        onEditTrack = { viewModel.openTrackForm(it) },
                        onDeleteTrack = { viewModel.deleteTrack(it) },
                        onUpdateCredentials = { newId, newPass -> viewModel.updateAdminCredentials(newId, newPass) },
                        onResetDefaults = { viewModel.resetAllData() }
                    )
                }
            }

            // MODALS & EXPANDED SHEETS

            // Expanded Spotify Now Playing Screen
            if (isNowPlayingExpanded) {
                NowPlayingDialog(
                    state = playbackState,
                    onDismiss = { viewModel.setNowPlayingExpanded(false) },
                    onTogglePlayPause = { viewModel.togglePlayPause() },
                    onNext = { viewModel.nextTrack() },
                    onPrev = { viewModel.prevTrack() },
                    onSeek = { viewModel.seekTo(it) },
                    onVolumeChange = { viewModel.setVolume(it) },
                    onToggleShuffle = { viewModel.toggleShuffle() },
                    onToggleLoop = { viewModel.toggleLoop() },
                    onPlaySynth = { viewModel.playInstrumentAcoustic(it) }
                )
            }

            // "About the Instrument" Detail Sheet
            selectedInstrumentForDetail?.let { instrument ->
                InstrumentDetailSheet(
                    instrument = instrument,
                    onDismiss = { viewModel.selectInstrumentForDetail(null) },
                    onPlaySound = { viewModel.playInstrumentAcoustic(it) },
                    onPlayAsTrack = { viewModel.playInstrumentAsTrack(it) }
                )
            }

            // AI Voice & Text Assistant Sheet
            if (isAiAssistantOpen) {
                AiAssistantSheet(
                    messages = aiMessages,
                    isThinking = isAiThinking,
                    onDismiss = { viewModel.setAiAssistantOpen(false) },
                    onSendMessage = { viewModel.sendAiQuery(it) },
                    onSpeak = { viewModel.speakAiMessage(it) }
                )
            }

            // Admin Login Modal (ID: 1234, Password: 1234)
            if (showAdminLoginDialog) {
                AdminLoginDialog(
                    onDismiss = { viewModel.setShowAdminLoginDialog(false) },
                    onLogin = { id, pass -> viewModel.loginAdmin(id, pass) }
                )
            }

            // Live Website Color, Font & Branding Customizer
            if (showThemeCustomizer) {
                ThemeCustomizerDialog(
                    currentConfig = siteConfig,
                    onDismiss = { viewModel.setShowThemeCustomizer(false) },
                    onApplyTheme = { title, sub, heading, desc, accent, border, font, mode ->
                        viewModel.updateSiteTheme(title, sub, heading, desc, accent, border, font, mode)
                    }
                )
            }

            // Add/Edit Instrument Modal
            if (showInstrumentFormDialog != null || (viewModel.showInstrumentFormDialog.collectAsStateWithLifecycle().value != null)) {
                InstrumentFormDialog(
                    instrument = showInstrumentFormDialog,
                    onDismiss = { viewModel.closeInstrumentForm() },
                    onSave = { viewModel.saveInstrument(it) }
                )
            }

            // Add/Edit Track Modal
            if (showTrackFormDialog != null) {
                TrackFormDialog(
                    track = showTrackFormDialog,
                    onDismiss = { viewModel.closeTrackForm() },
                    onSave = { viewModel.saveTrack(it) }
                )
            }
        }
    }
}
