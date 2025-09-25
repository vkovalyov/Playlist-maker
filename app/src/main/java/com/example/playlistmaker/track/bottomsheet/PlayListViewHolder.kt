package com.example.playlistmaker.track.bottomsheet


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.R
import com.example.playlistmaker.core.data.db.domain.models.PlayList
import com.example.playlistmaker.databinding.PlayListBinding


class PlayListViewHolder(private val binding: PlayListBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(model: PlayList) {
        val context = itemView.context

        binding.name.text = model.name

        var count =
            "${model.tracksCount} ${
                if (model.tracksCount == 1) context.getString(R.string.track) else context.getString(
                    R.string.tracks
                )
            }"
        binding.tracksCount.text = count

        Glide.with(itemView)
            .load(model.url)
            .centerInside()
            .centerCrop()
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .transform(RoundedCorners(8))
            .into(binding.playlistImg)

    }

    companion object {
        fun from(parent: ViewGroup): PlayListViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = PlayListBinding.inflate(inflater, parent, false)
            return PlayListViewHolder(binding)
        }
    }
}