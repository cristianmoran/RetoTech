package com.whiz.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.whiz.data.local.db.entity.movies.MovieDB

@Dao
interface MoviesDao {


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovies(movies: List<MovieDB>):  List<Long>

    @Query("SELECT * FROM movies_table")
    suspend fun getAllMovies(): List<MovieDB>?

}