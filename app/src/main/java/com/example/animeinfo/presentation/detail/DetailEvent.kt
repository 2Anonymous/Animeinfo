package com.example.animeinfo.presentation.detail

sealed class DetailEvent {
    data class LoadAnimeDetails(val animeId: Int) : DetailEvent()
    data object Retry : DetailEvent()
    data object ClearError : DetailEvent()
    data class PlayTrailer(val youtubeId: String) : DetailEvent()
}

