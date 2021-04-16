package com.example.beethozart.viewmodels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.input
import com.example.beethozart.R
import com.example.beethozart.entities.Playlist
import com.example.beethozart.entities.Song
import com.google.android.material.dialog.MaterialDialogs
import timber.log.Timber

class PlaylistViewModel(application: Application): AndroidViewModel(application) {
    private val _playlistList = MutableLiveData<MutableList<Playlist>>()
    val playlistList: LiveData<MutableList<Playlist>>
        get() = _playlistList

    init {
        val playlist = Playlist("Favorite")
        _playlistList.value = mutableListOf(playlist)
    }

    private fun addPlaylist(playlistName: String) {
        _playlistList.value?.add(Playlist(playlistName))
    }

    fun addToFavorite(song: Song) {
        for (playlist in _playlistList.value!!) {
            if (playlist.playlistName == "favorite") {
                playlist.addTrack(song)
            }
        }
    }

    fun onAddPlaylist(context: Context) {
        val dialog: MaterialDialog = MaterialDialog(context).show {
            title(text = context.getString(R.string.playlist_name_dialog_title))
            input(hintRes = R.string.playlist_name_hint) { _, charSequence ->
                addPlaylist(charSequence.toString())
            }
            positiveButton { R.string.submit }
            negativeButton { R.string.cancel }
        }
    }
}