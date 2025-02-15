package com.example.nasyamusic.data


data class DeezerSearchResponse(
    val data: List<DeezerTrack>,
    val total: Int
)

data class DeezerTrack(
    val id: Long,
    val title: String,
    val artist: DeezerArtist,
    val album: DeezerAlbum,
    val preview: String // Ссылка на превью трека
)

data class DeezerArtist(
    val id: Long,
    val name: String
)

data class DeezerAlbum(
    val id: Long,
    val title: String,
    val cover_medium: String // Ссылка на обложку альбома
)

data class DeezerChartResponse(
    val tracks: DeezerTracksData
)

data class DeezerTracksData(
    val data: List<DeezerTrack>
)


data class DeezerTrackResponse(
    val id: Long,
    val title: String,
    val link: String,
    val duration: Int,
    val preview: String,
    val artist: DeezerArtist,
    val album: DeezerAlbum
)