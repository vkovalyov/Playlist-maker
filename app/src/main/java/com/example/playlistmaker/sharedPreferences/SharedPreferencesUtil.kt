package com.example.playlistmaker.sharedPreferences

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE


object SharedPreferencesUtil {
    private const val APP_THEME = "app_theme"
    private const val HISTORY = "history"
    private const val PRACTICUM_EXAMPLE_PREFERENCES = "playlist_maker"
    private lateinit var sp: SharedPreferences


    fun init(context: Context) {
        sp = context.getSharedPreferences(PRACTICUM_EXAMPLE_PREFERENCES, MODE_PRIVATE)
    }

    fun setAppTheme(value: Boolean) {
        sp.edit().putBoolean(APP_THEME, value).apply()
    }

    fun getAppTheme(): Boolean {
        return sp.getBoolean(APP_THEME, false)
    }

    fun setHistory(value: String) {
        sp.edit().putString(HISTORY, value).apply()
    }

    fun getHistory(): String? {
        return sp.getString(HISTORY, "")
    }

    fun clearHistory() {
        sp.edit().remove(HISTORY).apply()
    }
}