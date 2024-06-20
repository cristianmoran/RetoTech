package com.whiz.reto.usecase

import com.whiz.reto.entity.movies.ListMovies
import com.whiz.reto.network.EventResult
import com.whiz.reto.repository.MoviesRepository
import javax.inject.Inject

class ListMoviesUseCase @Inject constructor(private val moviesRepository: MoviesRepository) {

    suspend fun execute(page: Int, isOnline: Boolean): EventResult<ListMovies> {
        return moviesRepository.listMoviesRemote(page)
    }

}