package com.example.beethozart.entities

import android.media.MediaPlayer
import android.net.Uri
import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.beethozart.notification.MusicPlayerNotificationBuilder
import com.example.beethozart.services.MusicPlayerService
import kotlin.math.min

class Player(
        private val service: MusicPlayerService,
        val songList: SongList,
) {
    private var currentSongId = 0
    private var mediaPlayer = MediaPlayer()
    private lateinit var runnable:Runnable
    private var handler: Handler = Handler()

    private var _isPlaying = MutableLiveData<Boolean>()
    val isPlaying: LiveData<Boolean>
        get() = _isPlaying

    private var _finished = MutableLiveData<Boolean>()
    val finished: LiveData<Boolean>
        get() = _finished

    init {
        _isPlaying.value = false
        _finished.value = false
    }

    private var _currentPosition = MutableLiveData<Float>()
    val currentPosition: LiveData<Float>
        get() = _currentPosition

    private val currentSongUri: Uri
        get() = Uri.parse(songList[currentSongId].uri)

    private val currentSong: Song
        get() = songList[currentSongId]

    val hasNext: Boolean
        get() = (currentSongId + 1 < songList.size)

    fun start() {
        if (_isPlaying.value!!) {
            mediaPlayer.reset()
        }
        mediaPlayer = MediaPlayer()
        playCurrentSong()
    }

    private fun initializeCurrentPosition() {
        runnable = Runnable {
            _currentPosition.value = mediaPlayer.currentPosition.toFloat() / mediaPlayer.duration
            handler.postDelayed(runnable, 100)
        }
        handler.postDelayed(runnable, 100)
    }

    private fun playCurrentSong() {
        _isPlaying.value = false
        mediaPlayer.reset()

        mediaPlayer.setDataSource(service, currentSongUri)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener {
            it.start()
            service.startForeground(
                    MusicPlayerNotificationBuilder.ONGOING_NOTIFICATION_ID,
                    service.musicPlayerNotificationBuilder.build(currentSong)
            )
            _isPlaying.value = true
        }
        mediaPlayer.setOnCompletionListener {
            if (hasNext)
                goNext()
            else
                service.onDestroy()
        }
    }

    fun goNext() {
        currentSongId += 1
        playCurrentSong()
    }

    fun goPrev() {
        currentSongId = min(0, currentSongId - 1)
        playCurrentSong()
    }

    fun pause() {
        _isPlaying.value = if (_isPlaying.value!!) {
            mediaPlayer.pause()
            false
        } else {
            mediaPlayer.start()
            true
        }
    }

    fun seekTo(timestamp: Float) {
        mediaPlayer.seekTo((timestamp * mediaPlayer.duration).toInt())
    }

    fun stop() {
        mediaPlayer.reset()
        mediaPlayer.release()
    }
}