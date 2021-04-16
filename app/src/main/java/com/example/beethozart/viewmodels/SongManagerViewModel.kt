package com.example.beethozart.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.beethozart.entities.SongList
import com.example.beethozart.databases.SongDatabase
import com.example.beethozart.entities.Song
import kotlinx.coroutines.*
import timber.log.Timber


class SongManagerViewModel(application: Application): AndroidViewModel(application) {
    private var songDatabase = SongDatabase.getInstance(application).songDatabaseDao

    var songList: LiveData<List<Song>> = songDatabase.getAllSongs()

    private var viewModelJob = Job()


    private val _currentSong = MutableLiveData<Song>()
    val currentSong: LiveData<Song>
        get() = _currentSong

    init {
        _currentSong.value = null
    }

    fun onSongClicked(song: Song) {
        _currentSong.value = song
    }

    fun onPlayerNavigated() {
        _currentSong.value = null
    }

    fun getSongList(): SongList {
        return SongList(songList.value!!.toMutableList()).beginWith(_currentSong.value)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}