package com.example.playlistmaker.track

import android.Manifest
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.view.MenuItem
import android.view.View.GONE
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import coil.compose.AsyncImage
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.R
import com.example.playlistmaker.core.service.MusicService
import com.example.playlistmaker.core.theme.AppTextStyle
import com.example.playlistmaker.core.theme.toTextStyle
import com.example.playlistmaker.databinding.ActivityTrackBinding
import com.example.playlistmaker.searchMusic.domain.models.Track
import com.example.playlistmaker.searchMusic.presentation.TRACK
import com.example.playlistmaker.track.bottomsheet.BottomSheetController
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.util.Locale

//
//class TrackActivity : ComponentActivity() {
//    private val trackViewModel: TrackViewModel by viewModel {
//        parametersOf(track)
//    }
//    //private lateinit var bottomSheetController: BottomSheetController
//
//    private val track: Track by lazy {
//        intent.getParcelableExtra(TRACK)!!
//    }
//
//    private val serviceConnection = object : ServiceConnection {
//        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
//            val binder = service as MusicService.MusicServiceBinder
//            trackViewModel.setAudioPlayerControl(binder.getService())
//        }
//
//        override fun onServiceDisconnected(name: ComponentName?) {
//            trackViewModel.removeAudioPlayerControl()
//        }
//    }
//
//    @OptIn(ExperimentalMaterial3Api::class)
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContent {
//
//            Scaffold(
//                topBar = {
//                    TopAppBar(
//
//                        title = { Text("") },
//                        colors = TopAppBarDefaults.topAppBarColors(
//                            containerColor = Color.White,
//                            titleContentColor = Color.White
//                        )
//                    )
//
//                },
//                modifier = Modifier.fillMaxSize(),
//
//                ) { paddingValues ->
//
//                Column(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .padding(paddingValues)
//                        .verticalScroll(rememberScrollState())
//                ) {
//                    TrackContent(track = track)
//                }
//            }
//
//
//        }
//    }
//
//    @Composable
//    private fun TrackContent(track: Track) {
//        val durationTrack =
//            SimpleDateFormat("mm:ss", Locale.getDefault()).format(track.trackTimeMillis)
//
//        Column {
//            // ImageViewer(track.previewUrl)
//            Text(
//                text = track.trackName,
//                style = TextStyle(
//                    fontWeight = FontWeight.W700,
//                    fontSize = 20.sp,
//                    color = Color.Black,
//                )
//            )
//            Spacer(modifier = Modifier.height(12.dp))
//            Text(text = track.artistName)
//            Spacer(modifier = Modifier.height(30.dp))
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.CenterVertically,
//            ) {
//
//                Image(
//                    painter = painterResource(id = R.drawable.add_album),
//                    contentDescription = null,
//                    modifier = Modifier
//                        .clickable {
//
//                        }
//                        .size(51.dp)
//                )
//                Image(
//                    painter = painterResource(id = R.drawable.play),
//                    contentDescription = null,
//                    modifier = Modifier
//                        .clickable {
//
//                        }
//                        .size(83.dp)
//                )
//                Image(
//                    painter = painterResource(id = R.drawable.favorite_disabled),
//                    contentDescription = null,
//                    modifier = Modifier
//                        .clickable {
//
//                        }
//                        .size(51.dp)
//                )
//            }
//            Spacer(modifier = Modifier.height(12.dp))
//            Text(
//                "00:00", textAlign = TextAlign.Center,
//                modifier = Modifier.fillMaxWidth()
//            )
//            Spacer(modifier = Modifier.height(30.dp))
//            TrackInfo(R.string.duration, durationTrack)
//            if (track.collectionName != null) {
//                Spacer(modifier = Modifier.height(24.dp))
//                TrackInfo(R.string.album, track.collectionName)
//            }
//            if (track.releaseDate != null) {
//                val instant = Instant.parse(track.releaseDate)
//                val zonedDateTime = instant.atZone(ZoneId.of("UTC"))
//
//                Spacer(modifier = Modifier.height(24.dp))
//                TrackInfo(R.string.year, zonedDateTime.year.toString())
//            }
//            if (track.primaryGenreName != null) {
//                Spacer(modifier = Modifier.height(24.dp))
//                TrackInfo(R.string.genre, track.primaryGenreName)
//            }
//            if (track.country != null) {
//                Spacer(modifier = Modifier.height(24.dp))
//                TrackInfo(R.string.country, track.country)
//            }
//        }
//    }
//
//    @Composable
//    fun TrackInfo(resId: Int, value: String) {
//        val isDark = isSystemInDarkTheme()
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceBetween
//        ) {
//            Text(stringResource(id = resId),style= TextStyle())
//            Text(value)
//
//        }
//    }
//
//    @Composable
//    fun ImageViewer(imageUrl: String?) {
//        AsyncImage(
//            model = imageUrl,
//            contentDescription = null,
//            modifier = Modifier.fillMaxSize(),
//            contentScale = ContentScale.Fit
//        )
//    }
//}
class TrackActivity : AppCompatActivity() {
    private val trackViewModel: TrackViewModel by viewModel {
        parametersOf(track)
    }
    private lateinit var bottomSheetController: BottomSheetController

    private val track: Track by lazy {
        intent.getParcelableExtra(TRACK)!!
    }

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as MusicService.MusicServiceBinder
            trackViewModel.setAudioPlayerControl(binder.getService())
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            trackViewModel.removeAudioPlayerControl()
        }
    }

    private lateinit var binding: ActivityTrackBinding

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

        trackViewModel.observePlayerState().observe(this) {
            updateButtonAndProgress(it)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        } else {
            bindMusicService()
        }

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


        bottomSheetController = BottomSheetController(this, binding, track)
        binding.addAlbum.setOnClickListener {
            bottomSheetController.show()
        }

        binding.trackName.text = track.trackName
        binding.trackGroup.text = track.artistName

        binding.durationTrack.text =
            SimpleDateFormat("mm:ss", Locale.getDefault()).format(track.trackTimeMillis)

        binding.lastDuration.text = "00:00"

        changeFavoriteButton(track.isFavorite)
        if (track.collectionName == null) {
            binding.albumGroup.visibility = GONE
        } else {
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

        trackViewModel.observeFavoriteState().observe(this) {
            changeFavoriteButton(it)
        }

        binding.favorite.setOnClickListener {
            trackViewModel.onFavoriteClicked(track)
        }

        binding.playButton.onTogglePlayback = { isPlaying ->
            trackViewModel.onPlayerButtonClicked()
        }
    }

    private fun changeFavoriteButton(isFavorite: Boolean) {
        if (isFavorite) {
            binding.favorite.setImageResource(R.drawable.favorite_pressed)
        } else {
            binding.favorite.setImageResource(R.drawable.favorite_disabled)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressedDispatcher.onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onStart() {
        trackViewModel.hiddenNotification()
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
        trackViewModel.showNotification()
    }

    override fun onDestroy() {
        trackViewModel.hiddenNotification()
        unbindMusicService()
        super.onDestroy()
    }


    private fun bindMusicService() {
        val intent = Intent(this, MusicService::class.java).apply {
            putExtra("song_url", track.previewUrl)
            putExtra("trackName", track.trackName)
            putExtra("artist", track.artistName)
        }

        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

    private fun unbindMusicService() {
        unbindService(serviceConnection)
    }

    private fun updateButtonAndProgress(playerState: PlayerState) {
        binding.playButton.setPlaying(playerState.isPlayButtonEnabled)
        binding.lastDuration.text = playerState.progress
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            bindMusicService()
        } else {
            Toast.makeText(this, "Вы не дали разрешений", Toast.LENGTH_LONG).show()
        }
    }
}