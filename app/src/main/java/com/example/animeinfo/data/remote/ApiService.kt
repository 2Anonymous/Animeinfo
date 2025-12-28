package com.example.animeinfo.data.remote

import com.example.animeinfo.data.remote.dto.AnimeDetailResponse
import com.example.animeinfo.data.remote.dto.AnimeListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("top/anime")
    suspend fun getTopAnime(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 25
    ): AnimeListResponse

    @GET("anime/{id}")
    suspend fun getAnimeDetails(
        @Path("id") animeId: Int
    ): AnimeDetailResponse
}
