package com.example.nasyamusic.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nasyamusic.data.DeezerTrack
import com.example.nasyamusic.databinding.ItemTrackBinding

class ApiTracksAdapter(
    private val onTrackClick: (DeezerTrack) -> Unit // Лямбда для обработки кликов
) : RecyclerView.Adapter<ApiTracksAdapter.ViewHolder>() {

    private var tracks: List<DeezerTrack> = emptyList()

    // Метод для обновления списка треков
    fun submitList(newTracks: List<DeezerTrack>) {
        tracks = newTracks
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTrackBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val track = tracks[position]
        holder.bind(track)
    }

    override fun getItemCount(): Int = tracks.size

    inner class ViewHolder(
        private val binding: ItemTrackBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(track: DeezerTrack) {
            // Установка данных в View
            binding.trackTitle.text = track.title
            binding.trackArtist.text = track.artist.name

            // Обработка клика по элементу
            binding.root.setOnClickListener {
                onTrackClick(track)
            }
        }
    }
}