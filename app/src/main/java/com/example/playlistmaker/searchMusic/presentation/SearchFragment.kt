package com.example.playlistmaker.searchMusic.presentation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.example.playlistmaker.R
import com.example.playlistmaker.core.theme.AppTheme
import com.example.playlistmaker.core.theme.LocalAppColors
import com.example.playlistmaker.core.ui.CustomTopBar
import com.example.playlistmaker.searchMusic.domain.models.Track
import com.example.playlistmaker.searchMusic.presentation.composables.ContentHistory
import com.example.playlistmaker.searchMusic.presentation.composables.ContentSearchResult
import com.example.playlistmaker.searchMusic.presentation.composables.ErrorResult
import com.example.playlistmaker.searchMusic.presentation.composables.SearchBar
import com.example.playlistmaker.track.TrackActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

const val TRACK = "TRACK_KEY"

class SearchFragment : Fragment() {
    private val viewModel: SearchMusicViewModel by viewModel()

    private fun onClickTrack(track: Track) {
        viewModel.addHistory(track)
        val intent = Intent(requireContext(), TrackActivity::class.java)
        intent.putExtra(TRACK, track)
        startActivity(intent)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {

            setContent {
                AppTheme {
                    Scaffold(
                        topBar = {
                            CustomTopBar(title = stringResource(R.string.search))
                        }) { innerPadding ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = innerPadding.calculateTopPadding())
                        ) {
                            SearchScreen(viewModel, { onClickTrack(it) })
                        }

                    }
                }
            }
        }
    }
}

@Composable
fun SearchScreen(viewModel: SearchMusicViewModel, onClick: (Track) -> Unit) {
    val state by viewModel.observeState().observeAsState(initial = SearchMusicState.Loading)
    val colors = LocalAppColors.current
    var query by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.updateHistory()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
            .padding(16.dp)
    ) {
        SearchBar(viewModel, query, onValueChange = { query = it })

        when (val currentState = state) {
            is SearchMusicState.Loading -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                }
            }

            is SearchMusicState.ContentHistory -> {
                ContentHistory(currentState.tracks, viewModel, onClick)
            }

            is SearchMusicState.ContentSearchResult -> {
                ContentSearchResult(currentState.tracks, onClick)
            }

            is SearchMusicState.Error -> {
                ErrorResult(viewModel, query)
            }
        }
    }
}
