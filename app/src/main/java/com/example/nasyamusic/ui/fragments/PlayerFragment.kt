package com.example.nasyamusic.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import coil.load
import com.example.nasyamusic.MusicService
import com.example.nasyamusic.R
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem

class PlayerFragment : Fragment(R.layout.fragment_player) {

    private lateinit var exoPlayer: ExoPlayer
    private lateinit var playPauseButton: Button
    private lateinit var seekBar: SeekBar
    private lateinit var handler: Handler
    private lateinit var updateSeekBar: Runnable

    companion object {
        private const val ARG_TRACK_URL = "track_url"
        private const val ARG_TRACK_COVER = "track_cover"
        private const val ARG_TRACK_TITLE = "track_title"
        private const val ARG_TRACK_ARTIST = "track_artist"

        fun newInstance(trackUrl: String, trackCover: String, trackTitle: String, trackArtist: String): PlayerFragment {
            val fragment = PlayerFragment()
            val args = Bundle()
            args.putString(ARG_TRACK_URL, trackUrl)
            args.putString(ARG_TRACK_COVER, trackCover)
            args.putString(ARG_TRACK_TITLE, trackTitle)
            args.putString(ARG_TRACK_ARTIST, trackArtist)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Получаем данные о треке
        val trackUrl = arguments?.getString(ARG_TRACK_URL) ?: ""
        val trackCover = arguments?.getString(ARG_TRACK_COVER) ?: ""
        val trackTitle = arguments?.getString(ARG_TRACK_TITLE) ?: ""
        val trackArtist = arguments?.getString(ARG_TRACK_ARTIST) ?: ""

        // Инициализация ExoPlayer
        exoPlayer = ExoPlayer.Builder(requireContext()).build()

        // Инициализация элементов UI
        playPauseButton = view.findViewById(R.id.playPauseButton)
        seekBar = view.findViewById(R.id.seekBar)

        // Загрузка обложки
        val coverImageView = view.findViewById<ImageView>(R.id.trackCover)
        coverImageView.load(trackCover) {
            placeholder(R.drawable.ic_music_note)
        }

        // Установка названия и исполнителя
        val titleTextView = view.findViewById<TextView>(R.id.trackTitle)
        titleTextView.text = trackTitle

        val artistTextView = view.findViewById<TextView>(R.id.trackArtist)
        artistTextView.text = trackArtist

        // Логика воспроизведения
        playPauseButton.setOnClickListener {
            if (exoPlayer.isPlaying) {
                exoPlayer.pause()
                playPauseButton.text = "Play"
            } else {
                exoPlayer.play()
                playPauseButton.text = "Pause"

                // Запуск MusicService
                val intent = Intent(requireContext(), MusicService::class.java)
                intent.putExtra("track_url", trackUrl) // Используем trackUrl, определённую выше
                requireContext().startService(intent)
            }
        }

        // Загрузка трека
        val mediaItem = MediaItem.fromUri(trackUrl)
        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.prepare()

        // Настройка SeekBar
        seekBar.max = exoPlayer.duration.toInt()

        // Инициализация handler и updateSeekBar
        handler = Handler(Looper.getMainLooper())
        updateSeekBar = object : Runnable {
            override fun run() {
                seekBar.progress = exoPlayer.currentPosition.toInt()
                handler.postDelayed(this, 1000) // Обновлять каждую секунду
            }
        }
        handler.post(updateSeekBar)

        // Обработка перемотки
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    exoPlayer.seekTo(progress.toLong()) // Перемотка
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacks(updateSeekBar) // Остановить обновление SeekBar
        exoPlayer.release() // Освободить ресурсы
    }
}