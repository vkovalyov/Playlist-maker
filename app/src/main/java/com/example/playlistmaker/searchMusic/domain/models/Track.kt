package com.example.playlistmaker.searchMusic.domain.models

import android.os.Parcelable
import com.example.playlistmaker.media.vm.favorite.FavoriteState
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Track(
    val id: Int,
    val trackName: String,
    val artistName: String,
    val trackTimeMillis: Int,
    val artworkUrl100: String,
    val collectionName: String?,
    val releaseDate: String?,
    val primaryGenreName: String?,
    val country: String?,
    val previewUrl: String?,
    var isFavorite: Boolean
) : Parcelable
