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
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.R
import com.example.playlistmaker.searchMusic.domain.models.Track
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.util.Locale

class TrackActivity : AppCompatActivity() {
    private val gson = Gson()
    private var mediaPlayer = MediaPlayer()
    private lateinit var play: ImageView
    private lateinit var lastDuration: TextView
    private var playerState = MediaPlayerState.STATE_DEFAULT
    private val handler = Handler(Looper.getMainLooper())
    private val updateRunnable = object : Runnable {
        override fun run() {

            handler.postDelayed(this, 300)
            updateTime()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_track)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""

        val json = intent.getStringExtra("track")
        val track = gson.fromJson(json, Track::class.java)

        val trackLabel = findViewById<ImageView>(R.id.track_logo)
        Glide.with(trackLabel)
            .load(track.artworkUrl100)
            .centerInside()
            .centerCrop()
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .transform(RoundedCorners(2))
            .into(trackLabel)


        val trackName = findViewById<TextView>(R.id.track_name)
        trackName.text = track.trackName
        val trackGroup = findViewById<TextView>(R.id.track_group)
        trackGroup.text = track.artistName


        val duration = findViewById<TextView>(R.id.duration_track)
        duration.text = SimpleDateFormat("mm:ss", Locale.getDefault()).format(track.trackTimeMillis)

        lastDuration = findViewById(R.id.last_duration)
        lastDuration.text = "00:00"
        //SimpleDateFormat("mm:ss", Locale.getDefault()).format(track.trackTimeMillis)


        if (track.collectionName == null) {
            val albumGroup = findViewById<Group>(R.id.album_group)
            albumGroup.visibility = GONE
        } else {
            val album = findViewById<TextView>(R.id.album_track)
            album.text = track.collectionName
        }


        if (track.releaseDate == null) {
            val yearGroup = findViewById<Group>(R.id.year_group)
            yearGroup.visibility = GONE
        } else {
            val year = findViewById<TextView>(R.id.year_track)
            val instant = Instant.parse(track.releaseDate)
            val zonedDateTime = instant.atZone(ZoneId.of("UTC"))
            year.text = zonedDateTime.year.toString()
        }

        if (track.primaryGenreName == null) {
            val genreGroup = findViewById<Group>(R.id.genre_group)
            genreGroup.visibility = GONE
        } else {
            val genre = findViewById<TextView>(R.id.genre_track)
            genre.text = track.primaryGenreName
        }


        if (track.country == null) {
            val countryGroup = findViewById<Group>(R.id.country_group)
            countryGroup.visibility = GONE
        } else {
            val country = findViewById<TextView>(R.id.country_track)
            country.text = track.country
        }

        play = findViewById(R.id.play)

        play.setOnClickListener {
            playbackControl()
        }
        preparePlayer(track.previewUrl)
    }

    private fun playbackControl() {
        when (playerState) {
            MediaPlayerState.STATE_PLAYING -> {
                pausePlayer()
            }

            MediaPlayerState.STATE_PREPARED, MediaPlayerState.STATE_PAUSED -> {
                startPlayer()
            }
        }
    }

    private fun startPlayer() {
        mediaPlayer.start()
        playerState = MediaPlayerState.STATE_PLAYING
        play.setImageResource(R.drawable.pause)

        handler.post(updateRunnable)
        mediaPlayer.currentPosition
    }

    private fun pausePlayer() {
        mediaPlayer.pause()
        playerState = MediaPlayerState.STATE_PAUSED
        play.setImageResource(R.drawable.play)
        handler.removeCallbacks(updateRunnable)
    }

    private fun preparePlayer(url: String) {
        mediaPlayer.setDataSource(url)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener {
            playerState = MediaPlayerState.STATE_PREPARED
        }

        mediaPlayer.setOnCompletionListener {
            playerState = MediaPlayerState.STATE_PREPARED
        }

        mediaPlayer.setOnCompletionListener {
            playerState = MediaPlayerState.STATE_PAUSED
            play.setImageResource(R.drawable.play)
            handler.removeCallbacks(updateRunnable)
            lastDuration.text = "00:00"
        }
    }

    private fun updateTime() {
        lastDuration.text =
            SimpleDateFormat("mm:ss", Locale.getDefault()).format(mediaPlayer.currentPosition)
                .toString()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressedDispatcher.onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
        handler.removeCallbacks(updateRunnable)
    }

    override fun onPause() {
        super.onPause()
        pausePlayer()
        handler.removeCallbacks(updateRunnable)
    }
}