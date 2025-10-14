package com.example.playlistmaker.media.playlist

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.playlistmaker.playList.PlayListActivity
import com.example.playlistmaker.core.data.db.domain.models.PlayList
import com.example.playlistmaker.core.utils.GridSpacingItemDecoration
import com.example.playlistmaker.create_playlist.CreatePlaylistActivity
import com.example.playlistmaker.databinding.TabPlalistsBinding
import com.example.playlistmaker.playList.PLAY_LIST_ID_KEY
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

const val COUNT = 2
const val HORIZONTAL_SPACING = 8
const val VERTICAL_SPACING = 16


class TabPlaylistFragment : Fragment() {
    private val viewModel: PlaylistViewModel by viewModel()

    private val adapter = PlayListAdapter {
        onClick(it)
    }

    private fun onClick(playList: PlayList) {
        val intent = Intent(requireContext(), PlayListActivity::class.java)
        intent.putExtra(PLAY_LIST_ID_KEY, playList.id)
        startActivity(intent)
    }

    private lateinit var binding: TabPlalistsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = TabPlalistsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.createPlaylist.setOnClickListener {
            val intent = Intent(requireContext(), CreatePlaylistActivity::class.java)
            addPlaylistLauncher.launch(intent)
        }

        val density = resources.displayMetrics.density
        val hSpacingPx = (HORIZONTAL_SPACING * density).toInt()
        val vSpacingPx = (VERTICAL_SPACING * density).toInt()

        binding.playList.layoutManager = GridLayoutManager(requireContext(), COUNT)
        binding.playList.addItemDecoration(
            GridSpacingItemDecoration(
                spanCount = COUNT,
                horizontalSpacing = hSpacingPx,
                verticalSpacing = vSpacingPx
            )
        )


        binding.playList.adapter = adapter

        viewModel.getPlayList()
        viewModel.observeState().observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.notFoundImg.visibility = View.VISIBLE
                binding.notFoundText.visibility = View.VISIBLE
                binding.playList.visibility = View.GONE
            } else {
                binding.notFoundImg.visibility = View.GONE
                binding.notFoundText.visibility = View.GONE
                binding.playList.visibility = View.VISIBLE
                adapter.updateData(it)
            }
        }
    }

    private val addPlaylistLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.getStringExtra("RESULT_MESSAGE")?.let { message ->
                viewModel.getPlayList()
                view?.let {
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
            }
        }
    }

    companion object {
        fun newInstance() = TabPlaylistFragment().apply {
        }
    }
}