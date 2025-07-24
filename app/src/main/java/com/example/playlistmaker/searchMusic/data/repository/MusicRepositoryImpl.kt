package com.example.playlistmaker.searchMusic.data.repository

import com.example.playlistmaker.core.data.network.NetworkClient
import com.example.playlistmaker.searchMusic.domain.models.Track
import com.example.playlistmaker.searchMusic.domain.repository.MusicRepository
import com.example.playlistmaker.searchMusic.data.dto.MusicSearchRequest
import com.example.playlistmaker.searchMusic.data.dto.TrackSearchResponse

class MusicRepositoryImpl(private val networkClient: NetworkClient) : MusicRepository {

    override fun searchMusic(searchType: String, searchText: String): List<Track> {
        val response = networkClient.doRequest(MusicSearchRequest(searchText, searchType))
        if (response.resultCode == 200) {
            return (response as TrackSearchResponse).results.map {
                Track(
                    it.trackName,
                    it.artistName,
                    it.trackTimeMillis,
                    it.artworkUrl100,
                    it.collectionName,
                    it.releaseDate,
                    it.primaryGenreName,
                    it.country,
                    it.previewUrl,
                )
            }
        } else {
            return emptyList()
        }
    }

}