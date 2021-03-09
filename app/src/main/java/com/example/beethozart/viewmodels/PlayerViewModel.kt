package com.example.beethozart.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.beethozart.entities.SongList
import com.example.beethozart.entities.Player
import com.example.beethozart.services.MusicPlayerService


class PlayerViewModel(application: Application): AndroidViewModel(application) {

    lateinit var musicPlayerServiceBinder: MusicPlayerService.MusicPlayerServiceBinder

    var player : Player? = null

    val currentPosition: LiveData<Float>
        get() = player!!.currentPosition

    fun playSongList(songList: SongList) {
        player = musicPlayerServiceBinder.playSongList(songList)
    }

    fun onGoNext() {
        player?.goNext()
    }

    fun onGoPrev() {
        player?.goPrev()
    }

    fun onPause() {
        player?.pause()
    }

    fun onSeek(timestamp: Float) {
        player?.seekTo(timestamp)
    }
}