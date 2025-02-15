package com.example.nasyamusic.domain.models

data class Track(
    val id: Long,              // Уникальный идентификатор трека
    val title: String,        // Название трека
    val artist: String,       // Исполнитель
    val preview: String,      // Ссылка на превью трека (аудио)
    val cover: String         // Ссылка на обложку трека
)