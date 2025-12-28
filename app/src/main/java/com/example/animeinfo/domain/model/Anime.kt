package com.example.animeinfo.domain.model

data class Anime(
    val malId: Int,
    val title: String,
    val titleEnglish: String?,
    val titleJapanese: String?,
    val synopsis: String?,
    val episodes: Int?,
    val score: Double?,
    val scoredBy: Int?,
    val rank: Int?,
    val popularity: Int?,
    val status: String?,
    val rating: String?,
    val source: String?,
    val duration: String?,
    val type: String?,
    val imageUrl: String?,
    val largeImageUrl: String?,
    val trailerUrl: String?,
    val youtubeId: String?,
    val genres: List<String>,
    val studios: List<String>,
    val airedString: String?,
    val season: String?,
    val year: Int?
)
