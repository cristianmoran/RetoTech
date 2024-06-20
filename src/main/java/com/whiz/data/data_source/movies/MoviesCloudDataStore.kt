package com.whiz.data.data_source.movies

import com.whiz.data.data_source.movies.service.MoviesService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MoviesCloudDataStore @Inject constructor(private val moviesService: MoviesService) {

    companion object {
        const val QUANTITY_MOVIES = 25
    }

    suspend fun listMovies(page: Int) = withContext(Dispatchers.IO) {
        moviesService.listMovies(offset = page, limit = QUANTITY_MOVIES)
    }

}