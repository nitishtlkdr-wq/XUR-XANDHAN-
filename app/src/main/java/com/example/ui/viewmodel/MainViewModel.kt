package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.ai.AiChatMessage
import com.example.ai.FolkAiAssistant
import com.example.audio.AcousticAudioEngine
import com.example.audio.PlaybackState
import com.example.data.local.AppDatabase
import com.example.data.model.InstrumentCategory
import com.example.data.model.InstrumentEntity
import com.example.data.model.SiteConfigEntity
import com.example.data.model.TrackEntity
import com.example.data.repository.AppRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(
    application: Application,
    private val repository: AppRepository
) : AndroidViewModel(application) {

    val audioEngine = AcousticAudioEngine(viewModelScope)
    val aiAssistant = FolkAiAssistant(application.applicationContext)

    val siteConfig: StateFlow<SiteConfigEntity> = repository.siteConfig
        .combine(MutableStateFlow(SiteConfigEntity())) { config, default ->
            config ?: default
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = SiteConfigEntity()
        )

    val allInstruments: StateFlow<List<InstrumentEntity>> = repository.allInstruments
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val allTracks: StateFlow<List<TrackEntity>> = repository.allTracks
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val playbackState: StateFlow<PlaybackState> = audioEngine.playbackState

    // Navigation & UI Tab
    private val _currentTab = MutableStateFlow("explore") // "explore", "search", "playlist", "admin"
    val currentTab: StateFlow<String> = _currentTab.asStateFlow()

    // Search & Filtering
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedCategory = MutableStateFlow(InstrumentCategory.ALL)
    val selectedCategory: StateFlow<InstrumentCategory> = _selectedCategory.asStateFlow()

    // Modals & Dialogs
    private val _selectedInstrumentForDetail = MutableStateFlow<InstrumentEntity?>(null)
    val selectedInstrumentForDetail: StateFlow<InstrumentEntity?> = _selectedInstrumentForDetail.asStateFlow()

    private val _isNowPlayingExpanded = MutableStateFlow(false)
    val isNowPlayingExpanded: StateFlow<Boolean> = _isNowPlayingExpanded.asStateFlow()

    private val _isAiAssistantOpen = MutableStateFlow(false)
    val isAiAssistantOpen: StateFlow<Boolean> = _isAiAssistantOpen.asStateFlow()

    private val _aiMessages = MutableStateFlow<List<AiChatMessage>>(
        listOf(
            AiChatMessage(
                sender = "assistant",
                message = "Namaskar! Welcome to Xur-Xandhan (সুৰ সন্ধান). I am your Assamese Folk AI Assistant. Ask me anything about Pepa, Dhol, Gogona, Tokari, Sutuli, Bhor Taal, or Bihu musical traditions!"
            )
        )
    )
    val aiMessages: StateFlow<List<AiChatMessage>> = _aiMessages.asStateFlow()

    private val _isAiThinking = MutableStateFlow(false)
    val isAiThinking: StateFlow<Boolean> = _isAiThinking.asStateFlow()

    // Admin State
    private val _isAdminLoggedIn = MutableStateFlow(false)
    val isAdminLoggedIn: StateFlow<Boolean> = _isAdminLoggedIn.asStateFlow()

    private val _showAdminLoginDialog = MutableStateFlow(false)
    val showAdminLoginDialog: StateFlow<Boolean> = _showAdminLoginDialog.asStateFlow()

    private val _showThemeCustomizer = MutableStateFlow(false)
    val showThemeCustomizer: StateFlow<Boolean> = _showThemeCustomizer.asStateFlow()

    private val _showInstrumentFormDialog = MutableStateFlow<InstrumentEntity?>(null) // null for new, entity for edit
    val showInstrumentFormDialog: StateFlow<InstrumentEntity?> = _showInstrumentFormDialog.asStateFlow()

    private val _showTrackFormDialog = MutableStateFlow<TrackEntity?>(null)
    val showTrackFormDialog: StateFlow<TrackEntity?> = _showTrackFormDialog.asStateFlow()

    private val _statusMessage = MutableStateFlow<String?>(null)
    val statusMessage: StateFlow<String?> = _statusMessage.asStateFlow()

    fun setTab(tab: String) {
        _currentTab.value = tab
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setSelectedCategory(category: InstrumentCategory) {
        _selectedCategory.value = category
    }

    fun selectInstrumentForDetail(instrument: InstrumentEntity?) {
        _selectedInstrumentForDetail.value = instrument
    }

    fun setNowPlayingExpanded(expanded: Boolean) {
        _isNowPlayingExpanded.value = expanded
    }

    fun setAiAssistantOpen(open: Boolean) {
        _isAiAssistantOpen.value = open
    }

    fun setShowAdminLoginDialog(show: Boolean) {
        _showAdminLoginDialog.value = show
    }

    fun setShowThemeCustomizer(show: Boolean) {
        _showThemeCustomizer.value = show
    }

    fun openInstrumentForm(instrument: InstrumentEntity? = null) {
        _showInstrumentFormDialog.value = instrument
    }

    fun closeInstrumentForm() {
        _showInstrumentFormDialog.value = null
    }

    fun openTrackForm(track: TrackEntity? = null) {
        _showTrackFormDialog.value = track
    }

    fun closeTrackForm() {
        _showTrackFormDialog.value = null
    }

    fun clearStatusMessage() {
        _statusMessage.value = null
    }

    // Sound & Playback Actions
    fun playInstrumentAcoustic(preset: String) {
        audioEngine.playInstrumentSound(preset)
    }

    fun playTrackItem(track: TrackEntity) {
        audioEngine.playTrack(
            trackId = track.id,
            title = track.title,
            artist = track.artist,
            instrument = track.instrumentName,
            coverUrl = track.coverImageUrl,
            duration = track.durationSeconds,
            preset = track.synthPreset
        )
    }

    fun playInstrumentAsTrack(instrument: InstrumentEntity) {
        audioEngine.playTrack(
            trackId = instrument.id,
            title = "${instrument.name} (${instrument.assameseName}) Solo",
            artist = "Master Assamese Folk Artisans",
            instrument = instrument.name,
            coverUrl = instrument.imageUrl,
            duration = 180,
            preset = instrument.synthPreset
        )
    }

    fun togglePlayPause() = audioEngine.togglePlayPause()
    fun seekTo(sec: Int) = audioEngine.seekTo(sec)
    fun setVolume(vol: Float) = audioEngine.setVolume(vol)
    fun toggleShuffle() = audioEngine.toggleShuffle()
    fun toggleLoop() = audioEngine.toggleLoop()

    fun nextTrack() {
        val tracks = allTracks.value
        if (tracks.isEmpty()) return
        val currentId = playbackState.value.currentTrackId
        val currentIndex = tracks.indexOfFirst { it.id == currentId }
        val nextIndex = if (currentIndex in 0 until tracks.size - 1) currentIndex + 1 else 0
        playTrackItem(tracks[nextIndex])
    }

    fun prevTrack() {
        val tracks = allTracks.value
        if (tracks.isEmpty()) return
        val currentId = playbackState.value.currentTrackId
        val currentIndex = tracks.indexOfFirst { it.id == currentId }
        val prevIndex = if (currentIndex > 0) currentIndex - 1 else tracks.size - 1
        playTrackItem(tracks[prevIndex])
    }

    // AI Assistant Actions
    fun sendAiQuery(query: String) {
        if (query.isBlank()) return
        val userMsg = AiChatMessage(sender = "user", message = query)
        _aiMessages.value = _aiMessages.value + userMsg
        _isAiThinking.value = true

        viewModelScope.launch {
            kotlinx.coroutines.delay(400L) // natural conversational pause
            val answer = aiAssistant.answerQuery(query, allInstruments.value)
            _aiMessages.value = _aiMessages.value + answer
            _isAiThinking.value = false
        }
    }

    fun speakAiMessage(text: String) {
        aiAssistant.speakAloud(text)
    }

    // Admin Operations
    fun loginAdmin(id: String, pass: String): Boolean {
        val config = siteConfig.value
        if (id.trim() == config.adminId && pass.trim() == config.adminPass) {
            _isAdminLoggedIn.value = true
            _showAdminLoginDialog.value = false
            _statusMessage.value = "Admin login successful! Welcome to Studio."
            return true
        }
        return false
    }

    fun logoutAdmin() {
        _isAdminLoggedIn.value = false
        _statusMessage.value = "Logged out from Admin Studio."
    }

    fun saveInstrument(instrument: InstrumentEntity) {
        viewModelScope.launch {
            if (instrument.id == 0L) {
                repository.insertInstrument(instrument.copy(isCustom = true))
                _statusMessage.value = "New instrument '${instrument.name}' added to catalog!"
            } else {
                repository.updateInstrument(instrument)
                _statusMessage.value = "Instrument '${instrument.name}' updated successfully!"
            }
            closeInstrumentForm()
        }
    }

    fun deleteInstrument(id: Long) {
        viewModelScope.launch {
            repository.deleteInstrument(id)
            _statusMessage.value = "Instrument removed from catalog."
        }
    }

    fun saveTrack(track: TrackEntity) {
        viewModelScope.launch {
            if (track.id == 0L) {
                repository.insertTrack(track.copy(isCustom = true))
                _statusMessage.value = "New track '${track.title}' added to Spotify playlist!"
            } else {
                repository.updateTrack(track)
                _statusMessage.value = "Track '${track.title}' updated successfully!"
            }
            closeTrackForm()
        }
    }

    fun deleteTrack(id: Long) {
        viewModelScope.launch {
            repository.deleteTrack(id)
            _statusMessage.value = "Track removed from playlist."
        }
    }

    fun updateSiteTheme(
        title: String,
        subtitle: String,
        heroHeading: String,
        heroDescription: String,
        accentHex: String,
        borderHex: String,
        fontStyle: String,
        themeMode: String
    ) {
        viewModelScope.launch {
            val current = siteConfig.value
            val updated = current.copy(
                siteTitle = title,
                siteSubtitle = subtitle,
                heroHeading = heroHeading,
                heroDescription = heroDescription,
                accentColorHex = accentHex,
                borderAccentHex = borderHex,
                fontStyle = fontStyle,
                themeMode = themeMode
            )
            repository.updateSiteConfig(updated)
            _showThemeCustomizer.value = false
            _statusMessage.value = "Website theme and branding settings applied!"
        }
    }

    fun updateAdminCredentials(newId: String, newPass: String) {
        viewModelScope.launch {
            val current = siteConfig.value
            repository.updateSiteConfig(current.copy(adminId = newId, adminPass = newPass))
            _statusMessage.value = "Admin credentials updated! Remember your new ID and password."
        }
    }

    fun resetAllData() {
        viewModelScope.launch {
            repository.resetDefaults()
            _statusMessage.value = "Catalog and settings reset to original defaults."
        }
    }

    override fun onCleared() {
        super.onCleared()
        audioEngine.stopPlaybackLoop()
        aiAssistant.shutdown()
    }
}

class MainViewModelFactory(
    private val application: Application,
    private val repository: AppRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(application, repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
