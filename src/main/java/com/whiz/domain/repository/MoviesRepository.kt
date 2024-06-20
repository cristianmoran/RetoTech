package com.whiz.domain.repository

import com.whiz.domain.network.EventResult
import com.whiz.domain.entity.movies.ListMovies

interface MoviesRepository {

    suspend fun listMoviesRemote(page: Int): EventResult<ListMovies>

}