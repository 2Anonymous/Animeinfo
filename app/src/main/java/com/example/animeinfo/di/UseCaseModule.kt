package com.example.animeinfo.di

import com.example.animeinfo.domain.repository.AnimeRepository
import com.example.animeinfo.domain.usecase.GetAnimeDetailsUseCase
import com.example.animeinfo.domain.usecase.GetTopAnimeUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    @ViewModelScoped
    fun provideGetTopAnimeUseCase(
        repository: AnimeRepository
    ): GetTopAnimeUseCase {
        return GetTopAnimeUseCase(repository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetAnimeDetailsUseCase(
        repository: AnimeRepository
    ): GetAnimeDetailsUseCase {
        return GetAnimeDetailsUseCase(repository)
    }
}

