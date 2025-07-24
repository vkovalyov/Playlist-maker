package com.example.playlistmaker.core.data.cache

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import java.lang.reflect.Type

private const val PRACTICUM_EXAMPLE_PREFERENCES = "playlist_maker"

class PrefsStorageClient<T>(
    private val gson: Gson,
    private val context: Context,
    private val dataKey: String,
    private val type: Type
) : StorageClient<T> {

    private val prefs: SharedPreferences =
        context.getSharedPreferences(PRACTICUM_EXAMPLE_PREFERENCES, Context.MODE_PRIVATE)

    override fun storeData(data: T) {
        prefs.edit().putString(dataKey, gson.toJson(data, type)).apply()
    }

    override fun getData(): T? {
        val dataJson = prefs.getString(dataKey, null)
        if (dataJson == null) {
            return null
        } else {
            return gson.fromJson(dataJson, type)
        }
    }

    override fun clear() {
        prefs.edit().remove(dataKey).apply()
    }
}