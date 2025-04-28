package com.example.playlistmaker.search

import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.R
import com.example.playlistmaker.models.Track
import com.example.playlistmaker.track.TrackActivity
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.Locale

class TrackViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var artistName: TextView = itemView.findViewById(R.id.artistName)
    private var trackName: TextView = itemView.findViewById(R.id.trackName)
    private var time: TextView = itemView.findViewById(R.id.time)
    private var trackLabel: ImageView = itemView.findViewById(R.id.trackLabel)


    fun bind(model: Track) {
        trackName.text = model.trackName
        artistName.text = model.artistName

        time.text = SimpleDateFormat("mm:ss", Locale.getDefault()).format(model.trackTimeMillis)

        Glide.with(itemView)
            .load(model.artworkUrl100)
            .centerInside()
            .centerCrop()
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .transform(RoundedCorners(8))
            .into(trackLabel)

        itemView.setOnClickListener {
            SearchManager.addTrackToHistory(model)
            val newModel = model.copy(
                collectionName = "sdf slkdf gsdfk24 wer sd cvxxc xcv xcv ghjttrcyctyr tcyrexre txrxtrxy ytrx",
                artworkUrl100 = model.artworkUrl100.replaceAfterLast(
                    '/',
                    "512x512bb.jpg"
                )
            )
            val intent = Intent(itemView.context, TrackActivity::class.java)
            val gson = Gson()
            val json = gson.toJson(newModel)
            intent.putExtra("track", json)
            itemView.context.startActivity(intent)
        }
    }

}