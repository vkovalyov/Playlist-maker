package com.example.playlistmaker.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.playlistmaker.core.theme.AppTextStyle
import com.example.playlistmaker.core.theme.LocalAppColors
import com.example.playlistmaker.core.theme.toTextStyle

@Preview(name = "CustomTopBar")
@Composable
fun CustomTopBar(title: String = "") {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(LocalAppColors.current.background)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Text(
            text = title,
            style = AppTextStyle.AppBarTitle.toTextStyle()
        )
    }
}