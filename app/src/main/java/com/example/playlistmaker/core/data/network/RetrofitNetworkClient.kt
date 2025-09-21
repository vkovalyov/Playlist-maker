package com.example.playlistmaker.core.data.network

import com.example.playlistmaker.searchMusic.data.dto.MusicSearchRequest
import com.example.playlistmaker.searchMusic.data.dto.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class RetrofitNetworkClient : NetworkClient {

    private val imdbBaseUrl = "https://itunes.apple.com/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(imdbBaseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val musicService = retrofit.create<MusicApiService>()


    override suspend fun doRequest(dto: Any): Response {
        return if (dto is MusicSearchRequest) {
            val response = musicService.searchMusic(dto.searchText, dto.type)
            response.apply { resultCode = 200 }
        } else {
            Response().apply { resultCode = 400 }
        }
    }
}