package com.example.beethozart.viewmodels

import android.app.Application
import android.os.Handler
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.beethozart.entities.SongList
import com.example.beethozart.services.MusicPlayerService
import com.google.android.exoplayer2.SimpleExoPlayer


class PlayerViewModel(application: Application): AndroidViewModel(application) {

    lateinit var musicPlayerServiceBinder: MusicPlayerService.MusicPlayerServiceBinder
    private lateinit var runnable:Runnable
    private var handler: Handler = Handler()

    private val _currentPosition = MutableLiveData<Float>()
    val currentPosition: LiveData<Float>
      get() = _currentPosition

    private val _isAttachedToPlayerFragment = MutableLiveData<Boolean>()
    val isAttachedToPlayerFragment: LiveData<Boolean>
      get() = _isAttachedToPlayerFragment

    var isPlaying = false

    var player : SimpleExoPlayer? = null

    fun attachToPlayerFragment() {
        _isAttachedToPlayerFragment.value = true
    }

    fun detachToPlayerFragment() {
        _isAttachedToPlayerFragment.value = false
    }

    var songList: SongList? = null

    fun playSongList(songList: SongList) {
        this.songList = songList

        player = musicPlayerServiceBinder.playSongList(songList)
        isPlaying = true
        runnable = Runnable {
            _currentPosition.value = player!!.currentPosition.toFloat() / player!!.duration
            handler.postDelayed(runnable, 100)
        }
        handler.postDelayed(runnable, 100)
    }

    fun onGoNext() {
        player?.next()
    }

    fun onGoPrev() {
        player?.previous()
    }

    fun onPause() {
        player?.playWhenReady = !player!!.isPlaying
    }

    fun onSeek(timestamp: Float) {
        player?.seekTo((timestamp * player!!.duration).toLong())
    }
}