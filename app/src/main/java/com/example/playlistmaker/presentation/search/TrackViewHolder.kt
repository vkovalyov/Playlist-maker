package com.example.playlistmaker.presentation.search

import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.Creator
import com.example.playlistmaker.R
import com.example.playlistmaker.domain.interactor.SearchHistoryInteractor
import com.example.playlistmaker.domain.models.Track
import com.example.playlistmaker.presentation.track.TrackActivity
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.Locale


private const val CLICK_DEBOUNCE_DELAY = 1000L

private var isClickAllowed = true

private val handler = Handler(Looper.getMainLooper())

class TrackViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var artistName: TextView = itemView.findViewById(R.id.artistName)
    private var trackName: TextView = itemView.findViewById(R.id.trackName)
    private var time: TextView = itemView.findViewById(R.id.time)
    private var trackLabel: ImageView = itemView.findViewById(R.id.trackLabel)
    private val historyInteractor: SearchHistoryInteractor =
        Creator.provideSearchHistoryInteractor()


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
            if (isClickAllowed) {
                isClickAllowed = false
                handler.postDelayed({ isClickAllowed = true }, CLICK_DEBOUNCE_DELAY)
            }

            historyInteractor.addTrackToHistory(model)
            val newModel = model.copy(
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