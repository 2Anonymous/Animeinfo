package com.example.animeinfo.presentation.home

sealed class HomeEvent {
    data object LoadAnime : HomeEvent()
    data object LoadMoreAnime : HomeEvent()
    data object Refresh : HomeEvent()
    data object ClearError : HomeEvent()
}

