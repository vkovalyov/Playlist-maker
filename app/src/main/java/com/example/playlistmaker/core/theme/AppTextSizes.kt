package com.example.playlistmaker.core.theme

import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

data class AppTextSizes(
    val big: TextUnit = 24.sp,
    val medium: TextUnit = 22.sp,
    val mediumYs: TextUnit = 19.sp,
    val mediumYx: TextUnit = 18.sp,
    val regular: TextUnit = 16.sp,
    val regularSys: TextUnit = 14.sp,
    val regularDisplay: TextUnit = 13.sp,
    val regularS: TextUnit = 12.sp,
    val regularYs: TextUnit = 11.sp
)

val DefaultTextSizes = AppTextSizes()