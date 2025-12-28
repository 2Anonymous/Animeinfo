package com.example.animeinfo.data.remote.dto

import com.google.gson.annotations.SerializedName

data class AnimeDto(
    @SerializedName("mal_id")
    val malId: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("title_english")
    val titleEnglish: String?,
    @SerializedName("title_japanese")
    val titleJapanese: String?,
    @SerializedName("synopsis")
    val synopsis: String?,
    @SerializedName("episodes")
    val episodes: Int?,
    @SerializedName("score")
    val score: Double?,
    @SerializedName("scored_by")
    val scoredBy: Int?,
    @SerializedName("rank")
    val rank: Int?,
    @SerializedName("popularity")
    val popularity: Int?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("rating")
    val rating: String?,
    @SerializedName("source")
    val source: String?,
    @SerializedName("duration")
    val duration: String?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("images")
    val images: ImagesDto?,
    @SerializedName("trailer")
    val trailer: TrailerDto?,
    @SerializedName("genres")
    val genres: List<GenreDto>?,
    @SerializedName("studios")
    val studios: List<StudioDto>?,
    @SerializedName("aired")
    val aired: AiredDto?,
    @SerializedName("season")
    val season: String?,
    @SerializedName("year")
    val year: Int?
)

data class ImagesDto(
    @SerializedName("jpg")
    val jpg: ImageUrlDto?,
    @SerializedName("webp")
    val webp: ImageUrlDto?
)

data class ImageUrlDto(
    @SerializedName("image_url")
    val imageUrl: String?,
    @SerializedName("small_image_url")
    val smallImageUrl: String?,
    @SerializedName("large_image_url")
    val largeImageUrl: String?
)

data class TrailerDto(
    @SerializedName("youtube_id")
    val youtubeId: String?,
    @SerializedName("url")
    val url: String?,
    @SerializedName("embed_url")
    val embedUrl: String?
)

data class GenreDto(
    @SerializedName("mal_id")
    val malId: Int,
    @SerializedName("name")
    val name: String
)

data class StudioDto(
    @SerializedName("mal_id")
    val malId: Int,
    @SerializedName("name")
    val name: String
)

data class AiredDto(
    @SerializedName("from")
    val from: String?,
    @SerializedName("to")
    val to: String?,
    @SerializedName("string")
    val string: String?
)

