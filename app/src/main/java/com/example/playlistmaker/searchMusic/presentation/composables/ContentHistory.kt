package com.example.playlistmaker.searchMusic.presentation.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.playlistmaker.R
import com.example.playlistmaker.core.theme.AppTextStyle
import com.example.playlistmaker.core.theme.LocalAppColors
import com.example.playlistmaker.core.theme.toTextStyle
import com.example.playlistmaker.core.ui.TrackListElement
import com.example.playlistmaker.searchMusic.domain.models.Track
import com.example.playlistmaker.searchMusic.presentation.SearchMusicViewModel

@Composable
fun ContentHistory(tracks: List<Track>, viewModel: SearchMusicViewModel, onClick: (Track) -> Unit) {
    val colors = LocalAppColors.current

    if (tracks.isNotEmpty()) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(24.dp))
            Box(Modifier.height(52.dp)) {
                Text(
                    stringResource(R.string.you_looking_for),
                    style = AppTextStyle.SearchHistoryTitle.toTextStyle()
                )
            }

            LazyColumn {
                items(tracks) { track ->
                    TrackListElement(track, onClick)
                }
            }
            Spacer(Modifier.height(24.dp))
            Button(
                onClick = {
                    viewModel.clearHistory()
                },
                enabled = true,
                shape = RoundedCornerShape(54.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colors.secondaryButtonColor),
                content = {
                    Text(
                        text = stringResource(R.string.clear_history),
                        style = AppTextStyle.Button.toTextStyle()
                    )
                })
        }
    }
}