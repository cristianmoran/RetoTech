package com.whiz.reto.domain.repository

import com.whiz.reto.domain.entity.movies.DetailMovie
import com.whiz.reto.domain.entity.movies.ListMovies
import com.whiz.core.network.EventResult

interface MoviesRepository {

    suspend fun listMoviesRemote(page: Int, sizePage: Int): com.whiz.core.network.EventResult<ListMovies>

    suspend fun listMoviesLocal(limit: Int, offset: Int): com.whiz.core.network.EventResult<ListMovies>

    suspend fun detailMovieRemote(id: Int): com.whiz.core.network.EventResult<DetailMovie>

    suspend fun detailMovieLocal(id: Int): com.whiz.core.network.EventResult<DetailMovie?>


}