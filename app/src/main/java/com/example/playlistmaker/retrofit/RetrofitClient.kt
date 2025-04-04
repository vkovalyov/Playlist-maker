package com.example.playlistmaker.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.create


private val retrofit = Retrofit.Builder()
    .baseUrl("https://itunes.apple.com/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()


val musicApiService = retrofit.create<MusicApiService>()