package com.example.playlistmaker.core.data.db.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlayList (
    val id: Long,
    val name: String,
    val description: String,
    val url: String,
    val tracks: String,
    val tracksCount: Int,
): Parcelable