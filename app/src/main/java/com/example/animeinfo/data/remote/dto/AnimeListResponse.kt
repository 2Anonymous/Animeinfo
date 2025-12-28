package com.example.animeinfo.data.remote.dto

import com.google.gson.annotations.SerializedName

data class AnimeListResponse(
    @SerializedName("data")
    val data: List<AnimeDto>,
    @SerializedName("pagination")
    val pagination: PaginationDto?
)

data class PaginationDto(
    @SerializedName("last_visible_page")
    val lastVisiblePage: Int,
    @SerializedName("has_next_page")
    val hasNextPage: Boolean,
    @SerializedName("current_page")
    val currentPage: Int
)

