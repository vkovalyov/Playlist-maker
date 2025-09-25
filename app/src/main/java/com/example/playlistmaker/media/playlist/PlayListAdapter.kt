package com.example.playlistmaker.media.playlist

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.core.data.db.domain.models.PlayList

class PlayListAdapter(
    private val onItemClick: (PlayList) -> Unit
) : RecyclerView.Adapter<PlayListViewHolder>() {
    private var playList = ArrayList<PlayList>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayListViewHolder {
        return PlayListViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: PlayListViewHolder, position: Int) {
        holder.bind(playList[position])
        holder.itemView.setOnClickListener { onItemClick(playList[position]) }
    }

    override fun getItemCount() = playList.size

    fun updateData(playList: List<PlayList>) {
        this.playList = playList as ArrayList<PlayList>
        notifyDataSetChanged()
    }
}