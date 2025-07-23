package com.example.playlistmaker.core.network

import com.example.playlistmaker.searchMusic.data.dto.TrackSearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface MusicApiService {
    @GET("search")
    fun searchMusic(
        @Query("term") name: String,
        @Query("entity") entity: String,
    ): Call<TrackSearchResponse>
}




