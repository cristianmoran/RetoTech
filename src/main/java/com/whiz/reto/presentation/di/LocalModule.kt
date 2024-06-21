package com.whiz.reto.presentation.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.whiz.core.DB_NAME
import com.whiz.reto.data.local.db.AppDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {



    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, AppDataBase::class.java, com.whiz.core.DB_NAME).allowMainThreadQueries()
            .setJournalMode(RoomDatabase.JournalMode.TRUNCATE).build()

    @Singleton
    @Provides
    fun provideMoviesDao(db: AppDataBase) = db.moviesDao()

    @Singleton
    @Provides
    fun provideMovieDetailDao(db: AppDataBase) = db.movieDetailDao()

}