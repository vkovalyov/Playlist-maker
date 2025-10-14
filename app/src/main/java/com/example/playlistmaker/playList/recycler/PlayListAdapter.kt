package com.example.playlistmaker.playList.recycler

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.searchMusic.domain.models.Track
import com.example.playlistmaker.searchMusic.presentation.TrackViewHolder

class PlayListAdapter(
    private val onClick: (Track) -> Unit,
    private val onLongClick: (Track) -> Unit,
) : RecyclerView.Adapter<TrackViewHolder>() {
    private var playList = ArrayList<Track>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        return TrackViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.bind(playList[position])
        holder.itemView.setOnClickListener { onClick(playList[position]) }
        holder.itemView.setOnLongClickListener {
            onLongClick(playList[position])
            true
        }
    }

    override fun getItemCount() = playList.size

    fun updateData(playList: List<Track>) {
        this.playList = playList as ArrayList<Track>
        notifyDataSetChanged()
    }
}