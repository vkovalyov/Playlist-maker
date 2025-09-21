package com.example.playlistmaker.core.data.network

import com.example.playlistmaker.searchMusic.data.dto.TrackSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface MusicApiService {
    @GET("search")
    suspend fun searchMusic(
        @Query("term") name: String,
        @Query("entity") entity: String,
    ): TrackSearchResponse
}




