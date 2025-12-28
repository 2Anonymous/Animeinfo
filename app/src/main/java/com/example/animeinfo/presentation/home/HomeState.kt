package com.example.animeinfo.presentation.home

import com.example.animeinfo.domain.model.Anime

data class HomeState(
    val animeList: List<Anime> = emptyList(),
    val isLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
    val error: String? = null,
    val isRefreshing: Boolean = false,
    val currentPage: Int = 1,
    val hasMorePages: Boolean = true,
    val isOffline: Boolean = false
)

