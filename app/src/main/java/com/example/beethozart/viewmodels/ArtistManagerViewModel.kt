package com.example.beethozart.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.beethozart.entities.Artist
import com.example.beethozart.entities.Song

class ArtistViewModel(application: Application): AndroidViewModel(application) {
    private val _artistList = MutableLiveData<MutableList<Artist>>()
    val artistList: LiveData<MutableList<Artist>>
        get() = _artistList


    init {
        val song = Song(1L, "Seebiggirl", "Phong")
        val artist = Artist("Solo")
        artist.addSong(song)

        _artistList.value = mutableListOf(artist)

        val artist2 = Artist("Team")
        artist2.addSong(song)
        artist2.addSong(Song(2L, "Sky", "Phong"))
        _artistList.value?.add(artist)
    }
}