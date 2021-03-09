package com.example.beethozart.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*
import java.util.concurrent.ThreadLocalRandom

enum class PlaybackMode {

}

@Parcelize
data class SongList(
    private val songList: MutableList<Song> = mutableListOf()
): Parcelable {

    val size: Int
      get() = songList.size

    private fun shuffle() {
        val rnd: Random = ThreadLocalRandom.current();

        for (i in songList.indices) {
            val index = rnd.nextInt(i + 1)
            val temp = songList[i]
            songList[i] = songList[index]
            songList[index] = temp
        }
    }

    operator fun get(id: Int): Song {
        return songList[id]
    }
}