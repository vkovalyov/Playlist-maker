package com.example.playlistmaker.track.bottomsheet


import android.content.Intent
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.playlistmaker.R
import com.example.playlistmaker.create_playlist.CreatePlaylistActivity
import com.example.playlistmaker.databinding.ActivityTrackBinding
import com.example.playlistmaker.searchMusic.domain.models.Track
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class BottomSheetController(
    private val activity: AppCompatActivity,
    private val binding: ActivityTrackBinding,
    private val track: Track

) {
    private val viewModel: BottomSheetViewModel by activity.viewModel()
    private val behavior = BottomSheetBehavior.from(binding.standardBottomSheet)
    private val adapter = PlayListAdapter {
        onClick(it)
    }

    private fun onClick(playListId: Long) {
        viewModel.addToPlayList(playListId, track)
    }

    init {
        setupBottomSheet()
    }

    private fun setupBottomSheet() {
        behavior.state = BottomSheetBehavior.STATE_HIDDEN
        behavior.isHideable = true
        behavior.skipCollapsed = true
        val halfScreenHeight = getScreenHeight() / 2
        behavior.peekHeight = halfScreenHeight

        binding.createPlaylist.setOnClickListener {
            val intent = Intent(activity, CreatePlaylistActivity::class.java)
            activity.startActivity(intent)
        }

        behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        binding.bottomSheetOverlay.visibility = View.VISIBLE
                    }

                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        binding.bottomSheetOverlay.visibility = View.VISIBLE
                    }

                    BottomSheetBehavior.STATE_HIDDEN -> {
                        binding.bottomSheetOverlay.visibility = View.GONE
                    }

                    else -> {}
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {}
        })
        binding.playList.adapter = adapter
        viewModel.getPlayList()
        viewModel.observeState().observe(activity) {

            when (it) {
                is BottomSheetState.ContentPlayList -> {
                    if (it.playList.isEmpty()) {
                        binding.notFoundImg.visibility = View.VISIBLE
                        binding.notFoundText.visibility = View.VISIBLE
                        binding.playList.visibility = View.GONE
                    } else {
                        binding.notFoundImg.visibility = View.GONE
                        binding.notFoundText.visibility = View.GONE
                        binding.playList.visibility = View.VISIBLE
                        adapter.updateData(it.playList)
                    }
                }


                is BottomSheetState.FailedAdd ->
                    showSnackBar(
                        activity.getString(R.string.already_added_in_playlist, it.name)
                    )

                is BottomSheetState.SuccessAdd -> {
                    showSnackBar(
                        activity.getString(R.string.added_in_playlist, it.name),
                    )
                    hide()
                }
            }

        }
    }

    private fun getScreenHeight(): Int {
        val displayMetrics = activity.resources.displayMetrics
        return displayMetrics.heightPixels
    }

    private fun showSnackBar(message: String) {
        val snackbar = Snackbar.make(
            binding.root,
            message,
            Snackbar.LENGTH_LONG
        )
        val textView =
            snackbar.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        textView.textAlignment = View.TEXT_ALIGNMENT_CENTER
        textView.gravity = Gravity.CENTER_HORIZONTAL
        snackbar.show()
    }

    fun show() {
        behavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    private fun hide() {
        behavior.state = BottomSheetBehavior.STATE_HIDDEN
    }
}