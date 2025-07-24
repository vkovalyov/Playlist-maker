package com.example.playlistmaker.searchMusic.presentation

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.searchMusic.domain.models.Track

class TrackAdapter(
    private val onItemClick: (Track) -> Unit
) : RecyclerView.Adapter<TrackViewHolder>() {
    private var tracks = ArrayList<Track>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        return TrackViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.bind(tracks[position])
        holder.itemView.setOnClickListener { onItemClick(tracks[position]) }
    }

    override fun getItemCount() = tracks.size

    fun updateData(tracks: List<Track>) {
        this.tracks = tracks as ArrayList<Track>
        notifyDataSetChanged()
    }
}