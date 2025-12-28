package com.example.animeinfo.data.remote.dto

import com.google.gson.annotations.SerializedName

data class AnimeDetailResponse(
    @SerializedName("data")
    val data: AnimeDto
)
