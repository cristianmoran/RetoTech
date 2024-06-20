package com.whiz.reto.presentation.di

import com.whiz.reto.data.repository.MoviesDataRepository
import com.whiz.reto.repository.MoviesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun provideMoviesRepository(dataRepository: MoviesDataRepository): MoviesRepository

}