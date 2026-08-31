package com.example.data.repository

import com.example.data.local.DefaultData
import com.example.data.local.InstrumentDao
import com.example.data.local.SiteConfigDao
import com.example.data.local.TrackDao
import com.example.data.model.InstrumentEntity
import com.example.data.model.SiteConfigEntity
import com.example.data.model.TrackEntity
import kotlinx.coroutines.flow.Flow

class AppRepository(
    private val instrumentDao: InstrumentDao,
    private val trackDao: TrackDao,
    private val siteConfigDao: SiteConfigDao
) {
    val allInstruments: Flow<List<InstrumentEntity>> = instrumentDao.getAllInstruments()
    val allTracks: Flow<List<TrackEntity>> = trackDao.getAllTracks()
    val siteConfig: Flow<SiteConfigEntity?> = siteConfigDao.getSiteConfig()

    suspend fun insertInstrument(instrument: InstrumentEntity): Long {
        return instrumentDao.insertInstrument(instrument)
    }

    suspend fun updateInstrument(instrument: InstrumentEntity) {
        instrumentDao.updateInstrument(instrument)
    }

    suspend fun deleteInstrument(id: Long) {
        instrumentDao.deleteById(id)
    }

    suspend fun insertTrack(track: TrackEntity): Long {
        return trackDao.insertTrack(track)
    }

    suspend fun updateTrack(track: TrackEntity) {
        trackDao.updateTrack(track)
    }

    suspend fun deleteTrack(id: Long) {
        trackDao.deleteById(id)
    }

    suspend fun updateSiteConfig(config: SiteConfigEntity) {
        siteConfigDao.insertOrUpdate(config)
    }

    suspend fun resetDefaults() {
        instrumentDao.clearAll()
        trackDao.clearAll()
        instrumentDao.insertAll(DefaultData.defaultInstruments)
        trackDao.insertAll(DefaultData.defaultTracks)
        siteConfigDao.insertOrUpdate(SiteConfigEntity())
    }
}
