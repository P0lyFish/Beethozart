package com.example.beethozart.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.beethozart.entities.Playlist
import com.example.beethozart.entities.Song

class PlaylistViewModel(application: Application): AndroidViewModel(application) {
    private val _playlistList = MutableLiveData<MutableList<Playlist>>()
    val playlistList: LiveData<MutableList<Playlist>>
        get() = _playlistList

    init {
        val track = Song()
        val playlist = Playlist("Her")
        playlist.addTrack(track)
        _playlistList.value = mutableListOf(playlist)
        _playlistList.value?.add(playlist)
        _playlistList.value?.add(playlist)
        _playlistList.value?.add(playlist)
        _playlistList.value?.add(playlist)
        _playlistList.value?.add(playlist)
        _playlistList.value?.add(playlist)
        _playlistList.value?.add(playlist)
        _playlistList.value?.add(playlist)
        _playlistList.value?.add(playlist)
        _playlistList.value?.add(playlist)
        _playlistList.value?.add(playlist)
        _playlistList.value?.add(playlist)
        _playlistList.value?.add(playlist)
        _playlistList.value?.add(playlist)
    }
}