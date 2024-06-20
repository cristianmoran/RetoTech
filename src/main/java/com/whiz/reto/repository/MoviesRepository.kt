package com.whiz.reto.repository

import com.whiz.reto.entity.movies.ListMovies
import com.whiz.reto.network.EventResult

interface MoviesRepository {

    suspend fun listMoviesRemote(page: Int): EventResult<ListMovies>

}