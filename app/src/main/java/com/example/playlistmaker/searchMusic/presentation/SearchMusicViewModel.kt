package com.example.playlistmaker.searchMusic.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.searchMusic.domain.interactor.MusicInteractor
import com.example.playlistmaker.searchMusic.domain.interactor.SearchHistoryInteractor
import com.example.playlistmaker.searchMusic.domain.models.Track
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class SearchMusicViewModel(
    private val musicInteractor: MusicInteractor,
    private val historyInteractor: SearchHistoryInteractor
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
                musicInteractor
                    .searchMusic(MUSIC_TRACK, latestSearchText.toString())
                    .catch {
                        renderState(SearchMusicState.Error)
                    }
                    .collect { pair ->
                        renderState(SearchMusicState.ContentSearchResult(pair))
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
        renderState(SearchMusicState.ContentHistory(getHistorySearch()))
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