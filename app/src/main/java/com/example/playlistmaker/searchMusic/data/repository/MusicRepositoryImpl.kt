package com.example.playlistmaker.searchMusic.data.repository

import com.example.playlistmaker.core.data.network.NetworkClient
import com.example.playlistmaker.searchMusic.domain.models.Track
import com.example.playlistmaker.searchMusic.domain.repository.MusicRepository
import com.example.playlistmaker.searchMusic.data.dto.MusicSearchRequest
import com.example.playlistmaker.searchMusic.data.dto.TrackSearchResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class MusicRepositoryImpl(private val networkClient: NetworkClient) : MusicRepository {

    override fun searchMusic(searchType: String, searchText: String): Flow<List<Track>> = flow {
        val response = networkClient.doRequest(MusicSearchRequest(searchText, searchType))
        if (response.resultCode == 200) {
            with(response as TrackSearchResponse) {
                val data = response.results.map {
                    Track(
                        it.trackId,
                        it.trackName,
                        it.artistName,
                        it.trackTimeMillis,
                        it.artworkUrl100,
                        it.collectionName,
                        it.releaseDate,
                        it.primaryGenreName,
                        it.country,
                        it.previewUrl,
                        false
                    )
                }
                emit(data)
            }
        } else {
            emit(emptyList())
        }
    }

}