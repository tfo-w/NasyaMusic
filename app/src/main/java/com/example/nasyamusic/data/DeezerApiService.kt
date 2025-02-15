package com.example.nasyamusic.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DeezerApiService {

    // Поиск треков
    @GET("search/track")
    suspend fun searchTracks(@Query("q") query: String): Response<DeezerSearchResponse>

    // Получение информации о треке по ID
    @GET("track/{id}")
    suspend fun getTrackById(@Path("id") trackId: String): Response<DeezerTrackResponse>

    // Получение популярных треков (чарт)
    @GET("chart/0/tracks")
    suspend fun getChartTracks(): Response<DeezerChartResponse>
}