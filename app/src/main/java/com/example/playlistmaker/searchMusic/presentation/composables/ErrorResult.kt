package com.example.playlistmaker.searchMusic.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.playlistmaker.R
import com.example.playlistmaker.core.theme.AppTextStyle
import com.example.playlistmaker.core.theme.LocalAppColors
import com.example.playlistmaker.core.theme.toTextStyle
import com.example.playlistmaker.searchMusic.presentation.SearchMusicViewModel

@Composable
fun ErrorResult(viewModel: SearchMusicViewModel, query: String) {
    val colors = LocalAppColors.current
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(102.dp))
        Image(
            painter = painterResource(id = R.drawable.music_error),
            contentDescription = null,
        )
        Spacer(Modifier.height(18.dp))
        Text(
            textAlign = TextAlign.Center,
            text = stringResource(R.string.internet_error),
            style = AppTextStyle.WarningText.toTextStyle()
        )
        Spacer(Modifier.height(24.dp))
        Button(
            onClick = {
                viewModel.search(changedText = query)
            },
            enabled = true,
            shape = RoundedCornerShape(54.dp),
            colors = ButtonDefaults.buttonColors(containerColor = colors.secondaryButtonColor),
            content = {
                Text(
                    text = stringResource(R.string.refresh),
                    style = AppTextStyle.Button.toTextStyle()
                )
            })
    }
}