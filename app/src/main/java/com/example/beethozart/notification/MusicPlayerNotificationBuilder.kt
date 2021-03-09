package com.example.beethozart.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.beethozart.MainActivity
import com.example.beethozart.R
import com.example.beethozart.entities.Song
import com.example.beethozart.services.MusicPlayerService
import timber.log.Timber


class MusicPlayerNotificationBuilder(
        private val service: MusicPlayerService,
) {
    private var pendingIntent: PendingIntent = Intent(service, MainActivity::class.java).let { notificationIntent ->
        PendingIntent.getActivity(service, 0, notificationIntent, 0)
    }
    private var notificationChannelCreated = false

    fun build(song: Song): Notification {
        if (!notificationChannelCreated)
            createNotificationChannel()

        Timber.i("Title: ${song.title}")
        return NotificationCompat.Builder(service, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_audiotrack_24px)
                .setContentTitle(song.title)
                .setContentText(song.album)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .build()
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // val name = context.getString(R.string.channel_name)
            // val descriptionText = context.getString(R.string.channel_description)
                val name = "name"
            val descriptionText = "desc"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                    service.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
            notificationChannelCreated = true
        }
    }

    companion object {
        const val CHANNEL_ID = "channel"
    }
}