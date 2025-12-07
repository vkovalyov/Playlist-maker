package com.example.playlistmaker.searchMusic.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.playlistmaker.R
import com.example.playlistmaker.core.theme.AppTextStyle
import com.example.playlistmaker.core.theme.toTextStyle

@Composable
fun EmptyResult() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(102.dp))
        Image(
            painter = painterResource(id = R.drawable.music_not_found),
            contentDescription = null,
        )
        Spacer(Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.not_found),
            style = AppTextStyle.WarningText.toTextStyle()
        )
    }
}