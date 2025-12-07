package com.example.playlistmaker.media.favorite

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.playlistmaker.R
import com.example.playlistmaker.core.theme.AppTextStyle
import com.example.playlistmaker.core.theme.toTextStyle
import com.example.playlistmaker.core.ui.TrackListElement
import com.example.playlistmaker.searchMusic.domain.models.Track


@Composable
fun FavoriteScreen(
    viewModel: FavoritesViewModel, onClick: (Track) -> Unit
) {
    val favoriteState by viewModel.observeState().observeAsState(initial = FavoriteState.Loading)

    LaunchedEffect(Unit) {
        viewModel.getFavorites()
    }

    when (val currentState = favoriteState) {
        is FavoriteState.Loading -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
            }
        }

        is FavoriteState.ContentFavorite -> {
            if (currentState.tracks.isEmpty()) {
                Column() {
                    Spacer(Modifier.height(106.dp))
                    EmptyFavorites()
                }
            }
            Column {
                Spacer(Modifier.height(20.dp))
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    items(currentState.tracks) { track ->
                        TrackListElement(track, onClick = onClick)
                    }
                }
            }

        }
    }
}

@Composable
fun EmptyFavorites() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.music_not_found),
            contentDescription = null,
        )
        Spacer(Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.your_media_empty),
            style = AppTextStyle.WarningText.toTextStyle()
        )
    }
}
