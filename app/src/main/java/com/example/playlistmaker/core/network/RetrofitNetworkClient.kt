package com.example.playlistmaker.core.network

import com.example.playlistmaker.core.NetworkClient
import com.example.playlistmaker.searchMusic.data.dto.MusicSearchRequest
import com.example.playlistmaker.searchMusic.data.dto.Response
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

    override fun doRequest(dto: Any): Response {
        if (dto is MusicSearchRequest) {

            val resp = musicService.searchMusic(dto.searchText, dto.type).execute()
            val body = resp.body() ?: Response()
            return body.apply { resultCode = resp.code() }
        } else {
            return Response().apply { resultCode = 400 }
        }
    }
}