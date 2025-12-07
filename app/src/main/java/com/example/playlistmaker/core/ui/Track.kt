package com.example.playlistmaker.core.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.playlistmaker.R
import com.example.playlistmaker.core.theme.AppTextStyle
import com.example.playlistmaker.core.theme.LocalAppColors
import com.example.playlistmaker.core.theme.toTextStyle
import com.example.playlistmaker.searchMusic.domain.models.Track
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun TrackListElement(track: Track, onClick: (Track) -> Unit) {
    val colors = LocalAppColors.current
    val trackTime = SimpleDateFormat("mm:ss", Locale.getDefault()).format(track.trackTimeMillis)
    val description = remember { "${track.artistName} • $trackTime" }

    Row(

        modifier = Modifier
            .fillMaxWidth()
            .height(61.dp)
            .clickable(
                onClick = {
                    onClick(track)
                },
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {

        Box(
            modifier = Modifier.clip(RoundedCornerShape(2.dp))
        ) {
            AsyncImage(
                model = track.artworkUrl100,
                contentDescription = null,
                modifier = Modifier.size(45.dp),
                placeholder = painterResource(R.drawable.placeholder),
                error = painterResource(R.drawable.placeholder)
            )
        }

        Spacer(Modifier.width(8.dp))
        Column(
            horizontalAlignment = Alignment.Start,
        ) {
            Text(track.trackName, style = AppTextStyle.TrackListTitle.toTextStyle())
            Spacer(Modifier.heightIn(1.dp))
            Text(
                description,
                style = AppTextStyle.TrackListDescription.toTextStyle()
            )
        }
        Spacer(Modifier.weight(1f))
        Icon(
            painter = painterResource(id = R.drawable.arrow),
            contentDescription = null,
            tint = colors.icon,
        )

    }

}
