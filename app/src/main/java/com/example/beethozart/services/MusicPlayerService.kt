package com.example.beethozart.services

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.example.beethozart.entities.SongList
import com.example.beethozart.notification.MusicPlayerNotificationBuilder
import com.example.beethozart.entities.Player
import timber.log.Timber

class MusicPlayerService: Service() {

    private var player: Player? = null

    private val binder = MusicPlayerServiceBinder()
    val musicPlayerNotificationBuilder by lazy { MusicPlayerNotificationBuilder(this) }

    inner class MusicPlayerServiceBinder : Binder() {
        fun getPlayer() = player

        fun playSongList(songList: SongList): Player {
            if (player != null) {
                player!!.stop()
            }

            player = Player(this@MusicPlayerService, songList)
            player?.start()

            return player!!
        }
    }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    override fun onUnbind(intent: Intent): Boolean {
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        player?.stop()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    companion object {
        const val ONGOING_NOTIFICATION_ID = 1
    }
}