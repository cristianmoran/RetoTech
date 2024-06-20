package com.whiz.reto.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.whiz.reto.data.local.db.entity.movies.MovieDB

@Dao
interface MoviesDao {


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovies(movies: List<MovieDB>):  List<Long>

    @Query("SELECT * FROM movies_table LIMIT :limit OFFSET :offset")
    suspend fun getAllMovies(limit: Int, offset: Int): List<MovieDB>?

    @Query("SELECT COUNT(*) FROM movies_table")
    suspend fun getSizeTotalMovies(): Int

}