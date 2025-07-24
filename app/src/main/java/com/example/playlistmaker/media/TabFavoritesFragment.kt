package com.example.playlistmaker.media

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.playlistmaker.databinding.TabFavoritesBinding


class TabFavoritesFragment : Fragment() {

    companion object {
        fun newInstance() = TabFavoritesFragment().apply {}
        }

    private lateinit var binding: TabFavoritesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = TabFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

}