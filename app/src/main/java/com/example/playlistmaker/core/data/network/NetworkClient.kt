package com.example.playlistmaker.core.data.network

import com.example.playlistmaker.searchMusic.data.dto.Response

interface NetworkClient {
    fun doRequest(dto: Any): Response

}