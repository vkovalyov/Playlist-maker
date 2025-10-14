package com.example.playlistmaker.playList

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.playlistmaker.R
import com.example.playlistmaker.create_playlist.CreatePlaylistActivity
import com.example.playlistmaker.create_playlist.PLAYLIST_KEY
import com.example.playlistmaker.databinding.ActivityPlayListBinding
import com.example.playlistmaker.playList.recycler.PlayListAdapter
import com.example.playlistmaker.playList.vm.PlayListState
import com.example.playlistmaker.playList.vm.PlayListViewModel
import com.example.playlistmaker.searchMusic.domain.models.Track
import com.example.playlistmaker.searchMusic.presentation.TRACK
import com.example.playlistmaker.track.TrackActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.text.SimpleDateFormat
import java.util.Locale

const val PLAY_LIST_ID_KEY = "PLAY_LIST_ID_KEY"

class PlayListActivity : AppCompatActivity() {
    private lateinit var dialogBuilder: MaterialAlertDialogBuilder
    private val viewModel: PlayListViewModel by viewModel {
        parametersOf(id)
    }

    private val adapter = PlayListAdapter(onClick = { track ->
        onClick(track)
    },
        onLongClick = { track ->
            onLongClick(track)
        })

    private fun onLongClick(track: Track) {
        dialogBuilder
            .setTitle(this.getString(R.string.delete_track))
            .setNegativeButton(this.getString(R.string.no).uppercase()) { _, _ -> }
            .setPositiveButton(this.getString(R.string.yes).uppercase()) { dialog, which ->
                viewModel.deleteTrack(track.id.toLong())
            }
            .show()
    }

    private fun onClick(track: Track) {
        val intent = Intent(this, TrackActivity::class.java)
        intent.putExtra(TRACK, track)
        startActivity(intent)
    }

    private lateinit var binding: ActivityPlayListBinding

    private
    val id: Long by lazy {
        intent.getLongExtra("PLAY_LIST_ID_KEY", -1L)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPlayListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
        dialogBuilder = MaterialAlertDialogBuilder(this)
        binding.recyclerTracks.adapter = adapter


        val behavior = BottomSheetBehavior.from(binding.bottomSheet)
        behavior.state = BottomSheetBehavior.STATE_COLLAPSED

        binding.menuLayout.post {
            val location = IntArray(2)
            binding.menuLayout.getLocationInWindow(location)
            val menuBottom = location[1] + binding.menuLayout.height

            val screenHeight = resources.displayMetrics.heightPixels
            val peekHeight = screenHeight - menuBottom

            behavior.peekHeight = peekHeight
        }

        val behaviorMenu = BottomSheetBehavior.from(binding.bottomSheetMenu)
        behaviorMenu.state = BottomSheetBehavior.STATE_HIDDEN
        behaviorMenu.isHideable = true
        behaviorMenu.skipCollapsed = true
        behaviorMenu.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        binding.bottomSheetOverlay.visibility = VISIBLE
                    }

                    BottomSheetBehavior.STATE_EXPANDED -> {
                        binding.bottomSheetOverlay.visibility = VISIBLE
                    }

                    BottomSheetBehavior.STATE_DRAGGING -> {
                        binding.bottomSheetOverlay.visibility = GONE
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }
        })

        binding.bottomSheetOverlay.setOnClickListener {
            behaviorMenu.state = BottomSheetBehavior.STATE_HIDDEN
            binding.bottomSheetOverlay.visibility = GONE
        }
        binding.share.setOnClickListener {
            viewModel.share()
        }

        binding.shareText.setOnClickListener {
            viewModel.share()
        }

        binding.deletePlayListText.setOnClickListener {
            viewModel.deletePlayListDialog()
        }

        binding.menu.setOnClickListener {
            viewModel.openMenu()
        }

        binding.editText.setOnClickListener {
            viewModel.editPlaylist()
        }


        viewModel.getPlayList()

        viewModel.observeState().observe(this) {
            when (it) {
                is PlayListState.Content -> {
                    Glide.with(binding.playListImage)
                        .load(it.playlistWithTracks.playlist.url)
                        .centerInside()
                        .centerCrop()
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.placeholder)
                        .into(binding.playListImage)
                    binding.playListName.text = it.playlistWithTracks.playlist.name
                    binding.description.text = it.playlistWithTracks.playlist.description

                    val totalTimeMinutes = it.minutes
                    val trackCount = it.playlistWithTracks.tracksCount
                    val minuteText = getString(R.string.minute)
                    val trackText =
                        this.resources.getQuantityString(
                            R.plurals.tracks_count,
                            trackCount,
                            trackCount
                        )

                    if (it.playlistWithTracks.playlist.description.isEmpty()) {
                        binding.description.visibility = GONE
                    }
                    binding.playListInfo.text = "$totalTimeMinutes $minuteText • $trackText"

                    if (it.playlistWithTracks.tracksCount > 0) {

                        behavior.isHideable = false
                        behavior.skipCollapsed = true
                        behavior.state = BottomSheetBehavior.STATE_COLLAPSED
                        binding.recyclerTracks.visibility = VISIBLE
                        adapter.updateData(it.playlistWithTracks.tracks)
                    } else {
                        behavior.skipCollapsed = false
                        behavior.isHideable = true
                        behavior.state = BottomSheetBehavior.STATE_HIDDEN
                    }
                }

                is PlayListState.EditPlayList -> {
                    behaviorMenu.state = BottomSheetBehavior.STATE_HIDDEN
                    binding.bottomSheetOverlay.visibility = GONE

                    val intent = Intent(this, CreatePlaylistActivity::class.java)
                    intent.putExtra(PLAYLIST_KEY, it.playlistWithTracks.playlist)
                    refreshResultLauncher.launch(intent)
                }

                is PlayListState.OpenMenu -> {
                    binding.bottomSheetOverlay.visibility = VISIBLE
                    binding.playList.name.text = it.playlistWithTracks.playlist.name
                    val trackCount = it.playlistWithTracks.tracksCount
                    val trackText =
                        this.resources.getQuantityString(
                            R.plurals.tracks_count,
                            trackCount,
                            trackCount
                        )
                    binding.playList.tracksCount.text = trackText
                    Glide.with(binding.playList.playlistImg)
                        .load(it.playlistWithTracks.playlist.url)
                        .centerInside()
                        .centerCrop()
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.placeholder)
                        .into(binding.playListImage)

                    behaviorMenu.state = BottomSheetBehavior.STATE_COLLAPSED
                }

                is PlayListState.Share -> {
                    if (it.playlistWithTracks.tracksCount == 0) {
                        Snackbar.make(
                            binding.root,
                            getString(R.string.play_list_not_share),
                            Snackbar.LENGTH_LONG
                        ).show()

                    } else {
                        val trackText =
                            this.resources.getQuantityString(
                                R.plurals.tracks_count,
                                it.playlistWithTracks.tracksCount,
                                it.playlistWithTracks.tracksCount,
                            )
                        var text = ""
                        val playlistWithTracks = it.playlistWithTracks

                        text = "${playlistWithTracks.playlist.name} \n"
                        text += "${playlistWithTracks.playlist.description} \n"
                        text += "$trackText \n"

                        for ((index, track) in it.playlistWithTracks.tracks.withIndex()) {
                            text += "$index. ${track.artistName} - ${track.trackName} (${
                                SimpleDateFormat(
                                    "mm:ss",
                                    Locale.getDefault()
                                ).format(track.trackTimeMillis)
                            })\n"
                        }

                        val shareIntent = Intent(Intent.ACTION_SEND)
                        shareIntent.setType("text/plain")
                        shareIntent.putExtra(Intent.EXTRA_TEXT, text)
                        startActivity(
                            Intent.createChooser(
                                shareIntent,
                                getString(R.string.share_link)
                            )
                        )
                    }
                }

                is PlayListState.DeletePlayList -> {
                    dialogBuilder
                        .setTitle(
                            this.getString(
                                R.string.want_delete_playlist,
                                it.playlistWithTracks.playlist.name
                            )
                        )
                        .setNegativeButton(this.getString(R.string.no).uppercase()) { _, _ -> }
                        .setPositiveButton(
                            this.getString(R.string.yes).uppercase()
                        ) { dialog, which ->
                            viewModel.deletePlayList()
                            onBackPressedDispatcher.onBackPressed()
                        }
                        .show()
                }
            }
        }
    }

    private val refreshResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            viewModel.getPlayList()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressedDispatcher.onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}