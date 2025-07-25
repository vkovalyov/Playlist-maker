package com.example.playlistmaker.media

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.playlistmaker.databinding.TabPlalistsBinding
import com.example.playlistmaker.media.vm.PlaylistViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class TabPlaylistFragment : Fragment() {
    private val viewModel: PlaylistViewModel by viewModel()

    companion object {

        fun newInstance() = TabPlaylistFragment().apply {

        }
    }

    private lateinit var binding: TabPlalistsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = TabPlalistsBinding.inflate(inflater, container, false)
        return binding.root
    }

}