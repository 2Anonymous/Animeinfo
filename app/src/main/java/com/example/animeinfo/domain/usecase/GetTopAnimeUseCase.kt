package com.example.animeinfo.domain.usecase

import com.example.animeinfo.domain.model.Anime
import com.example.animeinfo.domain.repository.AnimeRepository
import com.example.animeinfo.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTopAnimeUseCase @Inject constructor(
    private val repository: AnimeRepository
) {
    operator fun invoke(page: Int = 1, forceRefresh: Boolean = false): Flow<Resource<List<Anime>>> {
        return repository.getTopAnime(page = page, forceRefresh = forceRefresh)
    }
}

