package com.example.beethozart.services

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Binder
import android.os.IBinder
import com.example.beethozart.entities.SongList
import com.example.beethozart.notification.MusicPlayerNotificationBuilder
import com.example.beethozart.entities.Player
import timber.log.Timber

class MusicPlayerService: Service() {

    private var player: Player? = null

    private val binder = MusicPlayerServiceBinder()
    private val notificationBroadcastReceiver = NotificationBroadcastReceiver()
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

    inner class NotificationBroadcastReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
//            for (x in intent?.extras!!.keySet())
//                Timber.i("intent received: ${intent.extras!![x]}")

            when (intent!!.extras!!.getString(
                    MusicPlayerNotificationBuilder.NOTIFICATION_ACTION_NAME)
            ) {
                MusicPlayerNotificationBuilder.ACTION_PREV ->
                    this@MusicPlayerService.player!!.goPrev()
                MusicPlayerNotificationBuilder.ACTION_PAUSE ->
                    this@MusicPlayerService.player!!.pause()
                MusicPlayerNotificationBuilder.ACTION_NEXT ->
                    this@MusicPlayerService.player!!.goNext()
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        val intentFilter = IntentFilter(MusicPlayerNotificationBuilder.ACTION_NOTIFICATION_PLAYER)
        registerReceiver(notificationBroadcastReceiver, intentFilter)
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
}