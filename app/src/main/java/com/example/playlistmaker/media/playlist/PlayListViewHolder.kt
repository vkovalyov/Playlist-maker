package com.example.playlistmaker.media.playlist


import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.R
import com.example.playlistmaker.core.data.db.domain.models.PlayList
import com.example.playlistmaker.core.data.db.domain.models.PlaylistWithTracks
import com.example.playlistmaker.databinding.PlayListGridBinding


class PlayListViewHolder(private val binding: PlayListGridBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(model: PlaylistWithTracks) {
        val context = itemView.context

        binding.name.text = model.playlist.name

        val count = context.resources.getQuantityString(R.plurals.tracks_count, model.tracksCount, model.tracksCount)
        binding.tracksCount.text = count

        Glide.with(itemView)
            .load(model.playlist.url)
            .centerInside()
            .centerCrop()
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .transform(RoundedCorners((8 * Resources.getSystem().displayMetrics.density).toInt()))
            .into(binding.playlistImg)

    }

    companion object {
        fun from(parent: ViewGroup): PlayListViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = PlayListGridBinding.inflate(inflater, parent, false)
            return PlayListViewHolder(binding)
        }
    }
}