package com.example.playlistmaker.track

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MenuItem
import android.view.View.GONE
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.Group
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.ActivitySearchBinding
import com.example.playlistmaker.databinding.ActivityTrackBinding
import com.example.playlistmaker.databinding.TrackBinding
import com.example.playlistmaker.searchMusic.domain.models.Track
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.util.Locale

class TrackActivity : AppCompatActivity() {
    private lateinit var viewModel: PlayerViewModel
    private lateinit var binding: ActivityTrackBinding
    private val gson = Gson()


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

        val json = intent.getStringExtra("track")
        val track = gson.fromJson(json, Track::class.java)

        Glide.with(binding.trackLogo)
            .load(track.artworkUrl100)
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


        viewModel = ViewModelProvider(
            this,
            PlayerViewModel.getFactory(track.previewUrl.toString())
        )[PlayerViewModel::class.java]

        viewModel.observePlayerState().observe(this) {
            changeButtonText(it == PlayerViewModel.STATE_PLAYING)
        }

        viewModel.observeProgressTime().observe(this) {
            binding.lastDuration.text = it
        }

        binding.play.setOnClickListener {
            viewModel.onPlayButtonClicked()
        }
    }

    private fun changeButtonText(isPlaying: Boolean) {
        if(isPlaying){
            binding.play.setImageResource(R.drawable.pause)
        }else{
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
        viewModel.pausePlayer()
    }
}