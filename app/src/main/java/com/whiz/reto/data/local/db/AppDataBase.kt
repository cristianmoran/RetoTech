package com.whiz.reto.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.whiz.reto.data.local.db.entity.movies.DetailMovieDB
import com.whiz.reto.data.local.db.entity.movies.MovieDB
import com.whiz.reto.data.local.db.dao.MovieDetailDao
import com.whiz.reto.data.local.db.dao.MoviesDao
import com.whiz.reto.data.local.db.entity.movies.DetailMovieWithTypesDB

@Database(
    entities = [
        MovieDB::class,
//        DetailMovieWithTypesDB::class,
        DetailMovieDB::class,
//        DetailMovieDB.Sprites::class,
        DetailMovieDB.Types::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDataBase : RoomDatabase() {

    abstract fun moviesDao(): MoviesDao

    abstract fun movieDetailDao(): MovieDetailDao

}