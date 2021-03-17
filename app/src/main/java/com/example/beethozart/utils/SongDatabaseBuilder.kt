package com.example.beethozart

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import com.example.beethozart.databases.SongDatabase
import com.example.beethozart.entities.Song
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber


class SongDatabaseBuilder(private val context: Context) {
    suspend fun build() {
        withContext(Dispatchers.IO) {
            val database = SongDatabase.getInstance(context).songDatabaseDao
            database.clear()

            val projection = arrayOf(
                    MediaStore.Audio.Media._ID,
                    MediaStore.Audio.AudioColumns.TITLE,
                    MediaStore.Audio.Albums.ALBUM,
                    MediaStore.Audio.ArtistColumns.ARTIST,
            )

            val selection = MediaStore.Audio.Media.IS_MUSIC + " != 0"
            val uriExternal = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
            val sortOrder = MediaStore.Audio.Media.DATE_ADDED + " DESC"
            val audioCursor = context.contentResolver.query(
                    uriExternal, projection, selection, null, sortOrder
            )
            Timber.i(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI.toString())
            if (audioCursor != null && audioCursor.moveToFirst()) {
                while (audioCursor.moveToNext()) {
                    val idIndex = audioCursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
                    val titleIndex =
                            audioCursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.TITLE)
                    val albumIndex =
                            audioCursor.getColumnIndexOrThrow(MediaStore.Audio.Albums.ALBUM)
                    val artistIndex =
                            audioCursor.getColumnIndexOrThrow(MediaStore.Audio.ArtistColumns.ARTIST)

                    val id = audioCursor.getString(idIndex)

                    val song = Song(
                            id.toLong(),
                            audioCursor.getString(titleIndex),
                            audioCursor.getString(artistIndex),
                            audioCursor.getString(albumIndex),
                            Uri.withAppendedPath(uriExternal, "" + id).toString(),
                    )

                    database.insert(song)
                }

                audioCursor.close()
            }
        }
    }
}