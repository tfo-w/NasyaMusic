package com.example.nasyamusic.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nasyamusic.data.RetrofitClient
import com.example.nasyamusic.data.DeezerTrack
import kotlinx.coroutines.launch

class ApiTracksViewModel : ViewModel() {

    private val _tracks = MutableLiveData<List<DeezerTrack>>()
    val tracks: LiveData<List<DeezerTrack>> get() = _tracks

    fun loadTracks() {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.deezerApiService.getChartTracks()
                if (response.isSuccessful) {
                    _tracks.value = response.body()?.tracks?.data
                }
            } catch (e: Exception) {
                // Обработка ошибок
            }
        }
    }
}