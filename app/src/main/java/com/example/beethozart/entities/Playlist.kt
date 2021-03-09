package com.example.beethozart.entities

data class Playlist(
    val playlistName: String = "Unknown",
    var tracks: MutableList<Song> = mutableListOf()
) {
    fun getSize(): Int {
        return tracks.size
    }

    fun addTrack(track: Song) {
        tracks.add(track)
    }
}