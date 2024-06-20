package com.whiz.reto.domain.usecase

import com.whiz.reto.domain.entity.movies.DetailMovie
import com.whiz.reto.core.network.EventResult
import com.whiz.reto.repository.MoviesRepository
import javax.inject.Inject

class GetMovieDetailUseCase @Inject constructor(private val moviesRepository: MoviesRepository) {

    suspend fun execute(id: Int, isConnected: Boolean): EventResult<DetailMovie?> {
        return if(isConnected){
            moviesRepository.detailMovieRemote(id)
        }else{
            moviesRepository.detailMovieLocal(id)
        }

    }

}