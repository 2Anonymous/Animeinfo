package com.example.animeinfo.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.animeinfo.data.local.entity.AnimeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AnimeDao {

    @Query("SELECT * FROM anime ORDER BY rank ASC")
    fun getAllAnime(): Flow<List<AnimeEntity>>

    @Query("SELECT * FROM anime WHERE malId = :animeId")
    suspend fun getAnimeById(animeId: Int): AnimeEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnime(anime: AnimeEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllAnime(animeList: List<AnimeEntity>)

    @Query("DELETE FROM anime")
    suspend fun deleteAllAnime()
}
