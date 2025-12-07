package com.example.playlistmaker.core.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalContext

val LocalAppColors = staticCompositionLocalOf { LightAppColors }
val LocalAppTextSizes = staticCompositionLocalOf { DefaultTextSizes }

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkAppColors else LightAppColors
    val sizes = DefaultTextSizes

    CompositionLocalProvider(
        LocalAppColors provides colors,
        LocalAppTextSizes provides sizes
    ) {
        MaterialTheme(
            content = content
        )
    }
}