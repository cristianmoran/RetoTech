package com.whiz.reto.domain.repository

import com.whiz.reto.domain.entity.movies.DetailMovie
import com.whiz.reto.domain.entity.movies.ListMovies
import com.whiz.reto.core.network.EventResult

interface MoviesRepository {

    suspend fun listMoviesRemote(page: Int, sizePage: Int): EventResult<ListMovies>

    suspend fun listMoviesLocal(limit: Int, offset: Int): EventResult<ListMovies>

    suspend fun detailMovieRemote(id: Int): EventResult<DetailMovie>

    suspend fun detailMovieLocal(id: Int): EventResult<DetailMovie?>


}