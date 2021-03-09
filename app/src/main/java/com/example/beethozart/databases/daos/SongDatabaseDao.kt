package com.example.beethozart.databases.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.beethozart.entities.Song

@Dao
interface SongDatabaseDao {
    @Insert
    fun insert(song: Song)

    @Update
    fun update(song: Song)

    @Query("SELECT * from all_song_table WHERE songID = :key")
    fun get(key: Long): Song?

    @Query("SELECT * from all_song_table ORDER BY songId DESC")
    fun getAllSongs(): LiveData<List<Song>>

    @Query("DELETE FROM all_song_table")
    fun clear()
}