package com.example.nasyamusic

import android.annotation.SuppressLint
import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem

class MusicService : Service() {
    private lateinit var exoPlayer: ExoPlayer

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        exoPlayer = ExoPlayer.Builder(this).build()
    }

    @SuppressLint("ForegroundServiceType")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            "play" -> exoPlayer.play()
            "pause" -> exoPlayer.pause()
            else -> {
                val trackUrl = intent?.getStringExtra("track_url") ?: return START_NOT_STICKY
                val mediaItem = MediaItem.fromUri(trackUrl)
                exoPlayer.setMediaItem(mediaItem)
                exoPlayer.prepare()
                exoPlayer.play()
            }
        }
        startForeground(1, createNotification())
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        exoPlayer.release()
    }

    private fun createNotification(): Notification {
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        return NotificationCompat.Builder(this, "music_channel")
            .setContentTitle("Playing")
            .setContentText("Now playing: Track Name")
            .setSmallIcon(R.drawable.ic_music)
            .setContentIntent(pendingIntent)
            .addAction(R.drawable.ic_pause, "Pause", getPendingAction("pause"))
            .addAction(R.drawable.ic_play, "Play", getPendingAction("play"))
            .build()
    }

    private fun getPendingAction(action: String): PendingIntent {
        val intent = Intent(this, MusicService::class.java)
        intent.action = action
        return PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
    }
}