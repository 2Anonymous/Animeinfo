package com.example.animeinfo.domain.usecase

import com.example.animeinfo.domain.model.Anime
import com.example.animeinfo.domain.repository.AnimeRepository
import com.example.animeinfo.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAnimeDetailsUseCase @Inject constructor(
    private val repository: AnimeRepository
) {
    operator fun invoke(animeId: Int): Flow<Resource<Anime>> {
        return repository.getAnimeDetails(animeId = animeId)
    }
}

