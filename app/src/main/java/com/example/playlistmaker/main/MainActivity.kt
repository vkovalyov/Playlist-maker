package com.example.playlistmaker.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.playlistmaker.Creator
import com.example.playlistmaker.media.MediaActivity
import com.example.playlistmaker.R
import com.example.playlistmaker.searchMusic.presentation.SearchActivity
import com.example.playlistmaker.settings.presentation.SettingsActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

       Creator.provideThemeInteractor(context = this).initAppTheme()
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnSearch = findViewById<Button>(R.id.btn_search)
        val btnMedia = findViewById<Button>(R.id.btn_media)
        val btnSetting = findViewById<Button>(R.id.btn_setting)

        btnSearch.setOnClickListener(this@MainActivity)
        btnMedia.setOnClickListener(this@MainActivity)
        btnSetting.setOnClickListener(this@MainActivity)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_search -> {
                val displayIntent = Intent(this, SearchActivity::class.java)
                startActivity(displayIntent)
            }

            R.id.btn_media -> {
                val displayIntent = Intent(this, MediaActivity::class.java)
                startActivity(displayIntent)
            }

            R.id.btn_setting -> {
                val displayIntent = Intent(this, SettingsActivity::class.java)
                startActivity(displayIntent)
            }
        }
    }
}