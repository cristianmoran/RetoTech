package com.whiz.reto.usecase

import com.whiz.domain.entity.movies.ListMovies
import com.whiz.domain.network.EventResult
import com.whiz.domain.repository.MoviesRepository
import javax.inject.Inject

class ListMoviesUseCase @Inject constructor(private val moviesRepository: MoviesRepository) {

    suspend fun execute(page: Int, isOnline: Boolean): EventResult<ListMovies> {
        return moviesRepository.listMoviesRemote(page)
    }

}