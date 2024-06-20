package com.whiz.reto.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.whiz.reto.data.local.db.entity.movies.DetailMovieDB
import com.whiz.reto.data.local.db.entity.movies.DetailMovieWithTypesDB

@Dao
interface MovieDetailDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDetailMovie(detailMovie: DetailMovieDB)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTypes(types: List<DetailMovieDB.Types>)

    @Transaction
    @Query("SELECT * FROM detail_movie WHERE id = :id")
    suspend fun getDetailMovie(id: Int): DetailMovieWithTypesDB

    @Transaction
    suspend fun insertDetailMovieWithTypes(
        detailMovie: DetailMovieDB,
        types: List<DetailMovieDB.Types>
    ) {
        insertDetailMovie(detailMovie)
        insertTypes(types.map { it.copy(movieId = detailMovie.id) }.orEmpty())
    }

}