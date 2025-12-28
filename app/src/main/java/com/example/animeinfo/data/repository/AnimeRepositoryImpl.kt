package com.example.animeinfo.data.repository

import com.example.animeinfo.data.local.dao.AnimeDao
import com.example.animeinfo.data.mapper.toDomain
import com.example.animeinfo.data.mapper.toEntity
import com.example.animeinfo.data.remote.ApiService
import com.example.animeinfo.domain.model.Anime
import com.example.animeinfo.domain.repository.AnimeRepository
import com.example.animeinfo.util.NetworkConnectivityObserver
import com.example.animeinfo.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AnimeRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val animeDao: AnimeDao,
    private val networkConnectivityObserver: NetworkConnectivityObserver
) : AnimeRepository {

    override fun getTopAnime(page: Int, forceRefresh: Boolean): Flow<Resource<List<Anime>>> = flow {
        emit(Resource.Loading())
        if (page == 1) {
            val cachedList = animeDao.getAllAnime().first()
            if (cachedList.isNotEmpty() && !forceRefresh) {
                emit(Resource.Success(cachedList.map { it.toDomain() }))
                return@flow
            }
        }
        if (isOnline()) {
            try {
                val response = apiService.getTopAnime(page = page, limit = 25)
                val animeEntities = response.data.map { it.toEntity() }
                if (page == 1) {
                    animeDao.deleteAllAnime()
                }
                animeDao.insertAllAnime(animeEntities)
                emit(Resource.Success(animeEntities.map { it.toDomain() }))
            } catch (e: Exception) {
                val cached = animeDao.getAllAnime().first()
                if (cached.isNotEmpty()) {
                    emit(Resource.Error(
                        message = "Failed to load: ${e.message}",
                        data = cached.map { it.toDomain() }
                    ))
                } else {
                    emit(Resource.Error(message = e.message ?: "Unknown error occurred"))
                }
            }
        } else {
            if (page == 1) {
                val cached = animeDao.getAllAnime().first()
                if (cached.isNotEmpty()) {
                    emit(Resource.Success(cached.map { it.toDomain() }))
                } else {
                    emit(Resource.Error(message = "No internet connection and no cached data available"))
                }
            } else {
                emit(Resource.Error(message = "No internet connection"))
            }
        }
    }

    override fun getAnimeDetails(animeId: Int): Flow<Resource<Anime>> = flow {
        emit(Resource.Loading())
        val cachedAnime = animeDao.getAnimeById(animeId)
        if (cachedAnime != null) {
            emit(Resource.Success(cachedAnime.toDomain()))
        }

        if (isOnline()) {
            try {
                val response = apiService.getAnimeDetails(animeId)
                val animeEntity = response.data.toEntity()
                animeDao.insertAnime(animeEntity)
                emit(Resource.Success(animeEntity.toDomain()))
            } catch (e: Exception) {
                if (cachedAnime != null) {
                    emit(Resource.Error(
                        message = "Failed to refresh: ${e.message}",
                        data = cachedAnime.toDomain()
                    ))
                } else {
                    emit(Resource.Error(message = e.message ?: "Unknown error occurred"))
                }
            }
        } else {
            if (cachedAnime == null) {
                emit(Resource.Error(message = "No internet connection and no cached data available"))
            }
        }
    }

    override suspend fun isOnline(): Boolean {
        return networkConnectivityObserver.isConnected()
    }
}
