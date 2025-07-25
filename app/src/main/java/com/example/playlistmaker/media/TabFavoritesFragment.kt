package com.example.playlistmaker.media

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.playlistmaker.databinding.TabFavoritesBinding
import com.example.playlistmaker.media.vm.FavoritesViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class TabFavoritesFragment : Fragment() {
    private val viewModel: FavoritesViewModel by viewModel()

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