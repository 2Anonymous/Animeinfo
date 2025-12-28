package com.example.animeinfo.presentation.detail

import com.example.animeinfo.domain.model.Anime

data class DetailState(
    val anime: Anime? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isOffline: Boolean = false
)

