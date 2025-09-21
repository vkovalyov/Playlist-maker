package com.example.playlistmaker.track

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View.GONE
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.ActivityTrackBinding
import com.example.playlistmaker.searchMusic.domain.models.Track
import com.example.playlistmaker.searchMusic.presentation.TRACK
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.util.Locale

class TrackActivity : AppCompatActivity() {
    private lateinit var trackViewModel: TrackViewModel
    private lateinit var binding: ActivityTrackBinding
    private val gson = Gson()


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityTrackBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""

        val json = intent.getStringExtra(TRACK)
        val track = gson.fromJson(json, Track::class.java)

        Glide.with(binding.trackLogo)
            .load(
                track.artworkUrl100.replaceAfterLast(
                    '/',
                    "512x512bb.jpg"
                )
            )
            .centerInside()
            .centerCrop()
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .transform(RoundedCorners(2))
            .into(binding.trackLogo)


        binding.trackName.text = track.trackName
        binding.trackGroup.text = track.artistName

        binding.durationTrack.text =
            SimpleDateFormat("mm:ss", Locale.getDefault()).format(track.trackTimeMillis)

        binding.lastDuration.text = "00:00"

        if (track.collectionName == null) {
            binding.albumGroup.visibility = GONE
        } else {
            val album = findViewById<TextView>(R.id.album_track)
            binding.albumTrack.text = track.collectionName
        }

        if (track.releaseDate == null) {
            binding.yearGroup.visibility = GONE
        } else {
            val instant = Instant.parse(track.releaseDate)
            val zonedDateTime = instant.atZone(ZoneId.of("UTC"))
            binding.yearTrack.text = zonedDateTime.year.toString()
        }

        if (track.primaryGenreName == null) {
            binding.genreGroup.visibility = GONE
        } else {
            binding.genreTrack.text = track.primaryGenreName
        }

        if (track.country == null) {
            binding.countryGroup.visibility = GONE
        } else {
            binding.countryTrack.text = track.country
        }

        trackViewModel = ViewModelProvider(
            this,
            TrackViewModel.getFactory(track.previewUrl.toString())
        )[TrackViewModel::class.java]

        trackViewModel.observePlayerState().observe(this) {
            changeButton(it.isPlayButtonEnabled)
            binding.lastDuration.text = it.progress
        }

        binding.play.setOnClickListener {
            trackViewModel.onPlayButtonClicked()
        }
    }

    private fun changeButton(isPlaying: Boolean) {
        if (isPlaying) {
            binding.play.setImageResource(R.drawable.pause)
        } else {
            binding.play.setImageResource(R.drawable.play)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressedDispatcher.onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onPause() {
        super.onPause()
        trackViewModel.pausePlayer()
    }
}