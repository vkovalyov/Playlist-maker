package com.example.playlistmaker.media.favorite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.playlistmaker.databinding.TabFavoritesBinding
import com.example.playlistmaker.searchMusic.domain.models.Track
import com.example.playlistmaker.searchMusic.presentation.TrackAdapter
import com.example.playlistmaker.track.TrackActivity
import org.koin.androidx.viewmodel.ext.android.viewModel


class TabFavoritesFragment : Fragment() {
    private lateinit var binding: TabFavoritesBinding
    private val viewModel: FavoritesViewModel by viewModel()

    companion object {
        fun newInstance() = TabFavoritesFragment().apply {}
    }

    private val adapter = TrackAdapter {
        onClickTrack(it)
    }

    private fun onClickTrack(track: Track) {
        val intent = Intent(requireContext(), TrackActivity::class.java)
        intent.putExtra("TRACK_KEY", track)
        startActivity(intent)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = TabFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.favoriteList.adapter = adapter
        viewModel.getFavorites()
        viewModel.observeState().observe(viewLifecycleOwner) {
            when (it) {
                is FavoriteState.ContentFavorite -> {
                    binding.progressBar.visibility = View.GONE
                    if (it.tracks.isEmpty()) {
                        binding.favoriteList.visibility = View.GONE
                        binding.favoriteNotFound.root.visibility = View.VISIBLE
                    } else {
                        binding.favoriteNotFound.root.visibility = View.GONE
                        binding.favoriteList.visibility = View.VISIBLE
                        adapter.updateData(it.tracks)
                    }
                }

                FavoriteState.Loading -> binding.progressBar.visibility = View.VISIBLE
            }
        }
    }

}