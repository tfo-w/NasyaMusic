package com.example.nasyamusic.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nasyamusic.R
import com.example.nasyamusic.domain.models.Track
import com.example.nasyamusic.ui.adapters.LocalTracksAdapter
import com.example.nasyamusic.player.MusicPlayer

class LocalTracksFragment : Fragment(R.layout.fragment_local_tracks) {

    private lateinit var adapter: LocalTracksAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var musicPlayer: MusicPlayer

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Инициализация MusicPlayer
        musicPlayer = MusicPlayer(requireContext())

        // Инициализация RecyclerView
        recyclerView = view.findViewById(R.id.localTracksRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Загрузка данных (заглушка)
        val tracks = listOf(
            Track(1, "Song 1", "Artist 1", "", ""),
            Track(2, "Song 2", "Artist 2", "", "")
        )

        // Установка адаптера
        adapter = LocalTracksAdapter(tracks) { track ->
            // Воспроизведение трека при нажатии
            musicPlayer.play(track.preview)
        }
        recyclerView.adapter = adapter
    }
    override fun onDestroyView() {
        super.onDestroyView()
        // Освобождение ресурсов ExoPlayer
        musicPlayer.release()
    }
}