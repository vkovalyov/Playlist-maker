package com.example.playlistmaker.searchMusic.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.core.data.db.domain.interactor.FavoriteMusicInteractor
import com.example.playlistmaker.searchMusic.domain.interactor.MusicInteractor
import com.example.playlistmaker.searchMusic.domain.interactor.SearchHistoryInteractor
import com.example.playlistmaker.searchMusic.domain.models.Track
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SearchMusicViewModel(
    private val musicInteractor: MusicInteractor,
    private val historyInteractor: SearchHistoryInteractor,
    private val interactor: FavoriteMusicInteractor
) : ViewModel() {
    private var searchJob: Job? = null
    private val stateLiveData = MutableLiveData<SearchMusicState>()
    fun observeState(): LiveData<SearchMusicState> = stateLiveData

    private var latestSearchText: String? = null


    fun search(changedText: String) {
        if (latestSearchText == changedText) {
            return
        }
        searchRequest(changedText)

    }

    fun searchDebounce(changedText: String) {
        if (latestSearchText == changedText) {
            return
        }

        this.latestSearchText = changedText
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(SEARCH_DEBOUNCE_DELAY)
            searchRequest(changedText)
        }

    }


    private fun searchRequest(newSearchText: String) {
        if (newSearchText.isNotEmpty()) {
            renderState(SearchMusicState.Loading)

            viewModelScope.launch {


                interactor.favoriteMusic()
                    .collect { tracks ->
                        val favoritesId: List<Int> = tracks.map { it.id }
                        musicInteractor
                            .searchMusic(MUSIC_TRACK, latestSearchText.toString())
                            .catch {
                                renderState(SearchMusicState.Error)
                            }
                            .map {
                                it.map { entity ->
                                    if (favoritesId.isNotEmpty()) {
                                        if (favoritesId.contains(entity.id)) {
                                            entity.isFavorite = true
                                        }
                                    }
                                    entity
                                }
                            }
                            .collect { pair ->
                                renderState(SearchMusicState.ContentSearchResult(pair))
                            }
                    }


            }

        } else {
            updateHistory()
        }
    }

    private fun renderState(state: SearchMusicState) {
        stateLiveData.postValue(state)
    }

    private fun getHistorySearch(): List<Track> {
        return historyInteractor.getHistory()
    }

    fun addHistory(track: Track) {
        historyInteractor.saveToHistory(track)
    }

    fun updateHistory() {
        var favoritesId: List<Int> = mutableListOf()

        viewModelScope.launch {
            interactor.favoriteMusic()
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5000),
                    initialValue = emptyList()
                )
                .collect { tracks ->
                    favoritesId = tracks.map { it.id }
                    val historyList = getHistorySearch().map { entity ->
                        entity.isFavorite = favoritesId.contains(entity.id)
                        entity

                    }

                    renderState(SearchMusicState.ContentHistory(historyList))
                }
        }


    }

    fun clearHistory() {
        historyInteractor.clearHistory()
        updateHistory()
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
        private const val MUSIC_TRACK = "musicTrack"
    }
}