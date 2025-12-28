package com.example.animeinfo.domain.repository

import com.example.animeinfo.domain.model.Anime
import com.example.animeinfo.util.Resource
import kotlinx.coroutines.flow.Flow

interface AnimeRepository {
    
    fun getTopAnime(page: Int = 1, forceRefresh: Boolean = false): Flow<Resource<List<Anime>>>
    
    fun getAnimeDetails(animeId: Int): Flow<Resource<Anime>>
    
    suspend fun isOnline(): Boolean
}
