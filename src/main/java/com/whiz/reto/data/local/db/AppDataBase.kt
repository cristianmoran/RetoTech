package com.whiz.reto.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.whiz.data.local.db.dao.MoviesDao
import com.whiz.data.local.db.entity.movies.MovieDB

@Database(
    entities = [
        MovieDB::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDataBase : RoomDatabase() {

    abstract fun moviesDao(): MoviesDao

}