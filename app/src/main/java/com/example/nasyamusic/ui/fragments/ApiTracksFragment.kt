package com.example.nasyamusic.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nasyamusic.R
import com.example.nasyamusic.ui.adapters.ApiTracksAdapter
import com.example.nasyamusic.ui.viewmodels.ApiTracksViewModel

class ApiTracksFragment : Fragment(R.layout.fragment_api_tracks) {

    private lateinit var adapter: ApiTracksAdapter
    private lateinit var recyclerView: RecyclerView
    private val viewModel: ApiTracksViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Инициализация RecyclerView
        recyclerView = view.findViewById(R.id.apiTracksRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Инициализация адаптера с обработкой кликов
        adapter = ApiTracksAdapter { track ->
            // Переход на PlayerFragment с передачей данных о треке
            val bundle = Bundle().apply {
                putString("TRACK_PREVIEW_URL", track.preview)
                putString("TRACK_TITLE", track.title)
                putString("TRACK_ARTIST", track.artist.name)
                putString("TRACK_COVER", track.album.cover_medium)
            }

            // Открываем PlayerFragment
            val playerFragment = PlayerFragment()
            playerFragment.arguments = bundle

            // Используем FragmentManager для перехода
            parentFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment, playerFragment)
                .addToBackStack(null)
                .commit()
        }
        recyclerView.adapter = adapter

        // Наблюдение за данными из ViewModel
        viewModel.tracks.observe(viewLifecycleOwner, { tracks ->
            adapter.submitList(tracks)
        })

        // Загрузка данных из API
        viewModel.loadTracks()
    }
}