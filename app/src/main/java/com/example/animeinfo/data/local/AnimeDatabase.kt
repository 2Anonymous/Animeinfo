package com.example.animeinfo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.animeinfo.data.local.dao.AnimeDao
import com.example.animeinfo.data.local.entity.AnimeEntity

@Database(
    entities = [AnimeEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AnimeDatabase : RoomDatabase() {
    abstract fun animeDao(): AnimeDao
}
