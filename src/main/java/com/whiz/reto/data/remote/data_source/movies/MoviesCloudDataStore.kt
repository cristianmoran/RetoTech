package com.whiz.reto.data.remote.data_source.movies

import com.whiz.reto.data.remote.service.MoviesService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MoviesCloudDataStore @Inject constructor(private val moviesService: MoviesService) {

    suspend fun listMovies(offset: Int, sizePage: Int) = withContext(Dispatchers.IO) {
        moviesService.listMovies(offset = offset, limit = sizePage)
    }

    suspend fun detailMovie(id: Int) = withContext(Dispatchers.IO) {
        moviesService.detailMovie(id)
    }

}