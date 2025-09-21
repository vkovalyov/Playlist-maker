package com.example.playlistmaker.core.data.network

import com.example.playlistmaker.searchMusic.data.dto.Response

interface NetworkClient {
    suspend fun doRequest(dto: Any): Response

}