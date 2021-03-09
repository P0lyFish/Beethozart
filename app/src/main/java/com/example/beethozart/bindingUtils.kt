package com.example.beethozart

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.beethozart.entities.Artist
import com.example.beethozart.entities.Playlist


@BindingAdapter("numTracks")
fun TextView.setNumTracks(item: Artist?) {
    item?.let {
        text = item.getNumSongs().toString()
    }
}


@BindingAdapter("playlistName")
fun TextView.setPlaylistName(item: Playlist?) {
    item?.let {
        text = item.playlistName
    }
}


@BindingAdapter("numTracks")
fun TextView.setNumTracks(item: Playlist?) {
    item?.let {
        text = context.getString(R.string.num_tracks_format, item.getSize())
    }
}
