package com.example.playlistmaker.core.data.cache

interface StorageClient<T> {
    fun storeData(data: T)
    fun getData(): T?
    fun clear()
}