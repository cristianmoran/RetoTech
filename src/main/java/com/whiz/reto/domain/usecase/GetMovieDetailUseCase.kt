package com.whiz.reto.domain.usecase

import com.whiz.reto.domain.entity.movies.DetailMovie
import com.whiz.core.network.EventResult
import com.whiz.reto.domain.repository.MoviesRepository
import javax.inject.Inject

class GetMovieDetailUseCase @Inject constructor(private val moviesRepository: MoviesRepository) {

    suspend fun execute(id: Int, isConnected: Boolean): com.whiz.core.network.EventResult<DetailMovie?> {
        return if(isConnected){
            moviesRepository.detailMovieRemote(id)
        }else{
            moviesRepository.detailMovieLocal(id)
        }

    }

}