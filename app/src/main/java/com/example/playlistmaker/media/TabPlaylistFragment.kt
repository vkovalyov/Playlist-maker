package com.example.playlistmaker.media

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import com.example.playlistmaker.databinding.TabPlalistsBinding


class TabPlaylistFragment : Fragment() {

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