package com.example.beethozart.viewmodels

import android.app.Application
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Handler
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.beethozart.entities.Song
import com.example.beethozart.entities.SongList
import com.example.beethozart.services.MusicPlayerService
import com.google.android.exoplayer2.SimpleExoPlayer
import kotlin.math.max


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

    private val _currentSong = MutableLiveData<Song>()
    val currentSong: LiveData<Song>
      get() = _currentSong

    val currentTitle: LiveData<String> = Transformations.switchMap(currentSong) {
        MutableLiveData(it.title)
    }

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

        player = musicPlayerServiceBinder.playSongList(songList, this)
        isPlaying = true
        runnable = Runnable {
            _currentPosition.value = max(0.0001f, player!!.currentPosition.toFloat() / player!!.duration)
            handler.postDelayed(runnable, 100)
        }
        handler.postDelayed(runnable, 100)
    }

    fun setCurrentSong(song: Song) {
        _currentSong.value = song
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

    fun onShareSong(context: Context, song: Song?) {
        song?.let {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "audio/*"

            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(song.uri))

            context.startActivity(Intent.createChooser(shareIntent, "Share a song"))
        }
    }
}