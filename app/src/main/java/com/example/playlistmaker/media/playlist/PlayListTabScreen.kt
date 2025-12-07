package com.example.playlistmaker.media.playlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.playlistmaker.R
import com.example.playlistmaker.core.data.db.domain.models.PlayList
import com.example.playlistmaker.core.data.db.domain.models.PlaylistWithTracks
import com.example.playlistmaker.core.theme.AppTextStyle
import com.example.playlistmaker.core.theme.LocalAppColors
import com.example.playlistmaker.core.theme.toTextStyle

@Composable
fun PlayListScreen(
    playlistViewModel: PlaylistViewModel, onClickPlaylist: (PlayList) -> Unit,
    onClickCreatePlayList: () -> Unit,
) {
    val playLists by playlistViewModel.observeState().observeAsState(initial = emptyList())
    val colors = LocalAppColors.current

    LaunchedEffect(Unit) {
        playlistViewModel.getPlayList()
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(24.dp))
        Button(
            onClick = onClickCreatePlayList,
            enabled = true,
            shape = RoundedCornerShape(54.dp),
            colors = ButtonDefaults.buttonColors(containerColor = colors.secondaryButtonColor),
            content = {
                Text(
                    text = stringResource(R.string.new_playlists),
                    style = AppTextStyle.Button.toTextStyle()
                )
            })

        if (playLists.isEmpty()) {
            Column() {
                Spacer(Modifier.height(46.dp))
                EmptyPlayList()
            }
        } else {
            Column() {
                Spacer(Modifier.height(16.dp))
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),

                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(playLists) { playList ->
                        Box(Modifier.clickable(onClick = { onClickPlaylist(playList.playlist) })) {
                            TrackGridItem(playList)
                        }
                    }
                }
            }
        }

    }
}

@Composable
fun TrackGridItem(playList: PlaylistWithTracks) {
    val tracksCount = pluralStringResource(
        id = R.plurals.tracks_count,
        count = playList.tracksCount,
        playList.tracksCount
    )
    Column {
        Box(
            modifier = Modifier.clip(RoundedCornerShape(8.dp))
        ) {
            AsyncImage(
                model = playList.playlist.url,
                contentDescription = null,
                modifier = Modifier.size(160.dp),
                placeholder = painterResource(R.drawable.placeholder),
                error = painterResource(R.drawable.placeholder)

            )
        }

        Spacer(Modifier.height(4.dp))
        Text(
            text = playList.playlist.name, style = AppTextStyle.PlayListTitle.toTextStyle()
        )
        Text(
            text = tracksCount, style = AppTextStyle.PlayListTitle.toTextStyle()
        )
    }
}

@Composable
fun EmptyPlayList() {
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
            text = stringResource(R.string.not_created_playlists),
            style = AppTextStyle.WarningText.toTextStyle()
        )
    }
}