package com.example.playlistmaker

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import androidx.activity.addCallback

import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class SettingsActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_settings)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.setting)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val btnSearch = findViewById<LinearLayout>(R.id.share)
        btnSearch.setOnClickListener(this@SettingsActivity)

        val btnMedia = findViewById<LinearLayout>(R.id.support)
        btnMedia.setOnClickListener(this@SettingsActivity)

        val btnSetting = findViewById<LinearLayout>(R.id.agreement)
        btnSetting.setOnClickListener(this@SettingsActivity)

        val switch = findViewById<SwitchCompat>(R.id.switch1)
        switch.setChecked(isDarkThemeEnabled());

        switch.setOnCheckedChangeListener { _, isChecked ->
            setAppTheme(isChecked)
        }
    }

    private fun setAppTheme(isDarkModeOn: Boolean) {
        if (isDarkModeOn) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressedDispatcher.onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun isDarkThemeEnabled(): Boolean {
        val nightMode = AppCompatDelegate.getDefaultNightMode()
        return nightMode == AppCompatDelegate.MODE_NIGHT_YES
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.share -> {
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.setType("text/plain")
                val link = getString(R.string.yandex_link)
                shareIntent.putExtra(Intent.EXTRA_TEXT, link)
                startActivity(Intent.createChooser(shareIntent, getString(R.string.share_link)))
            }

            R.id.support -> {
                val recipientEmail = getString(R.string.mail_student)

                val mailUri = Uri.parse("mailto:$recipientEmail")
                    .buildUpon()
                    .appendQueryParameter(
                        getString(R.string.subject),
                        getString(R.string.subject_title)
                    )
                    .appendQueryParameter(
                        getString(R.string.body),
                        getString(R.string.body_title)
                    )
                    .build()

                val emailIntent = Intent(Intent.ACTION_SENDTO, mailUri)
                startActivity(emailIntent)
            }

            R.id.agreement -> {
                val url = getString(R.string.offer)
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(browserIntent)
            }
        }
    }
}