package com.example.animeinfo.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "anime")
data class AnimeEntity(
    @PrimaryKey
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
    val genres: String?,
    val studios: String?,
    val airedString: String?,
    val season: String?,
    val year: Int?,
    val lastUpdated: Long = System.currentTimeMillis()
)

