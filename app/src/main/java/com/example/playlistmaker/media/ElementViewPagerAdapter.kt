package com.example.playlistmaker.media

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.playlistmaker.media.favorite.TabFavoritesFragment
import com.example.playlistmaker.media.playlist.TabPlaylistFragment

class NumbersViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> TabFavoritesFragment.newInstance()
            else -> TabPlaylistFragment.newInstance()
        }
    }
}