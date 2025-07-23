package com.example.playlistmaker.searchMusic.presentation

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.playlistmaker.Creator
import com.example.playlistmaker.searchMusic.domain.interactor.MusicInteractor
import com.example.playlistmaker.searchMusic.domain.models.Track

class SearchMusicViewModel() : ViewModel() {
    private val musicInteractor = Creator.provideMusicInteractor()
    private val historyInteractor = Creator.provideSearchHistoryInteractor()

    private val stateLiveData = MutableLiveData<SearchMusicState>()
    fun observeState(): LiveData<SearchMusicState> = stateLiveData

    private var latestSearchText: String? = null

    private val handler = Handler(Looper.getMainLooper())

    fun searchDebounce(changedText: String) {
        if (latestSearchText == changedText) {
            return
        }

        this.latestSearchText = changedText
        handler.removeCallbacksAndMessages(null)

        val searchRunnable = Runnable { searchRequest(changedText) }

        handler.postDelayed(searchRunnable, SEARCH_DEBOUNCE_DELAY)

    }

    private fun searchRequest(newSearchText: String) {
        if (newSearchText.isNotEmpty()) {
            renderState(SearchMusicState.Loading)

            try {
                musicInteractor.searchMusic(
                    MUSIC_TRACK,
                    latestSearchText.toString(),
                    object : MusicInteractor.MusicConsumer {
                        override fun consume(foundMusic: List<Track>) {
                            if (foundMusic.isNotEmpty()) {
                                renderState(SearchMusicState.ContentSearchResult(foundMusic))
                            } else {
                                renderState(SearchMusicState.ContentSearchResult(emptyList()))
                            }
                        }
                    })
            } catch (_: Exception) {
                renderState(SearchMusicState.Error(message = ""))
            }
        } else {
            updateHistory()
        }
    }

    private fun renderState(state: SearchMusicState) {
        stateLiveData.postValue(state)
    }

    override fun onCleared() {
        super.onCleared()
        handler.removeCallbacksAndMessages(null)
    }

    private fun getHistorySearch(): List<Track> {
        return historyInteractor.getHistory()
    }

    fun addHistory(track: Track) {
        historyInteractor.addTrackToHistory(track)
    }

    fun updateHistory() {
        renderState(SearchMusicState.ContentHistory(getHistorySearch()))
    }

    fun clearHistory() {
        historyInteractor.clearHistory()
    }


    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
        private const val SEARCH_TEXT = "search_text"
        private const val MUSIC_TRACK = "musicTrack"

//        fun getFactory(historyInteractor: SearchHistoryInteractor): ViewModelProvider.Factory =
//            viewModelFactory {
//                initializer {
//                    SearchMusicViewModel(historyInteractor)
//                }
//            }
//
//        fun getFactory(value: Int): ViewModelProvider.Factory = viewModelFactory {
//            initializer {
//                val app = (this[APPLICATION_KEY] as MoviesApplication)
//                MoviesViewModel(app)
//            }
//        }
    }


}