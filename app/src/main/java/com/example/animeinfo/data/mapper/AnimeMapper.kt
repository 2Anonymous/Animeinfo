package com.example.animeinfo.data.mapper

import com.example.animeinfo.data.local.entity.AnimeEntity
import com.example.animeinfo.data.remote.dto.AnimeDto
import com.example.animeinfo.data.remote.dto.TrailerDto
import com.example.animeinfo.domain.model.Anime

private fun TrailerDto?.extractYoutubeId(): String? {
    if (this == null) return null
    if (!youtubeId.isNullOrBlank()) return youtubeId
    embedUrl?.let { url ->
        val regex = Regex("/embed/([a-zA-Z0-9_-]+)")
        val match = regex.find(url)
        return match?.groupValues?.getOrNull(1)
    }
    return null
}

fun AnimeDto.toEntity(): AnimeEntity {
    val extractedYoutubeId = trailer.extractYoutubeId()
    return AnimeEntity(
        malId = malId,
        title = title,
        titleEnglish = titleEnglish,
        titleJapanese = titleJapanese,
        synopsis = synopsis,
        episodes = episodes,
        score = score,
        scoredBy = scoredBy,
        rank = rank,
        popularity = popularity,
        status = status,
        rating = rating,
        source = source,
        duration = duration,
        type = type,
        imageUrl = images?.jpg?.imageUrl ?: images?.webp?.imageUrl,
        largeImageUrl = images?.jpg?.largeImageUrl ?: images?.webp?.largeImageUrl,
        trailerUrl = trailer?.url ?: trailer?.embedUrl,
        youtubeId = extractedYoutubeId,
        genres = genres?.joinToString(",") { it.name },
        studios = studios?.joinToString(",") { it.name },
        airedString = aired?.string,
        season = season,
        year = year
    )
}

fun AnimeEntity.toDomain(): Anime {
    return Anime(
        malId = malId,
        title = title,
        titleEnglish = titleEnglish,
        titleJapanese = titleJapanese,
        synopsis = synopsis,
        episodes = episodes,
        score = score,
        scoredBy = scoredBy,
        rank = rank,
        popularity = popularity,
        status = status,
        rating = rating,
        source = source,
        duration = duration,
        type = type,
        imageUrl = imageUrl,
        largeImageUrl = largeImageUrl,
        trailerUrl = trailerUrl,
        youtubeId = youtubeId,
        genres = genres?.split(",")?.filter { it.isNotBlank() } ?: emptyList(),
        studios = studios?.split(",")?.filter { it.isNotBlank() } ?: emptyList(),
        airedString = airedString,
        season = season,
        year = year
    )
}

fun AnimeDto.toDomain(): Anime {
    val extractedYoutubeId = trailer.extractYoutubeId()
    return Anime(
        malId = malId,
        title = title,
        titleEnglish = titleEnglish,
        titleJapanese = titleJapanese,
        synopsis = synopsis,
        episodes = episodes,
        score = score,
        scoredBy = scoredBy,
        rank = rank,
        popularity = popularity,
        status = status,
        rating = rating,
        source = source,
        duration = duration,
        type = type,
        imageUrl = images?.jpg?.imageUrl ?: images?.webp?.imageUrl,
        largeImageUrl = images?.jpg?.largeImageUrl ?: images?.webp?.largeImageUrl,
        trailerUrl = trailer?.url ?: trailer?.embedUrl,
        youtubeId = extractedYoutubeId,
        genres = genres?.map { it.name } ?: emptyList(),
        studios = studios?.map { it.name } ?: emptyList(),
        airedString = aired?.string,
        season = season,
        year = year
    )
}
