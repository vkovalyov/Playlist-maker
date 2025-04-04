package com.example.playlistmaker.retrofit

import com.example.playlistmaker.models.ITunesResponse
import retrofit2.Call;
import retrofit2.http.GET
import retrofit2.http.Query

interface MusicApiService {

    @GET("search")
    fun searchMusic(
        @Query("term") name: String,
        @Query("entity") entity: String,
    ): Call<ITunesResponse>


}




