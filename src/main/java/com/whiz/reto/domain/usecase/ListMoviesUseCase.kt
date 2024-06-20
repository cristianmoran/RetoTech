package com.whiz.reto.domain.usecase

import com.whiz.reto.domain.entity.movies.ListMovies
import com.whiz.reto.core.network.EventResult
import com.whiz.reto.domain.repository.MoviesRepository
import javax.inject.Inject

class ListMoviesUseCase @Inject constructor(private val moviesRepository: MoviesRepository) {

    suspend fun execute(offset: Int, sizePage: Int, isConnected: Boolean): EventResult<ListMovies> {
        return if(isConnected){
            moviesRepository.listMoviesRemote(offset, sizePage)
        }else{
            moviesRepository.listMoviesLocal(sizePage,offset)
        }

    }

}