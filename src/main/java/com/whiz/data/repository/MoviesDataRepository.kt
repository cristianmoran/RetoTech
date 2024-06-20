package com.whiz.data.repository

import com.whiz.domain.network.EventResult
import com.whiz.data.base.BaseDataRepository
import com.whiz.data.remote.data_source.movies.MoviesCloudDataStore
import com.whiz.data.remote.entity.movies.toModel
import com.whiz.domain.entity.movies.ListMovies
import com.whiz.domain.repository.MoviesRepository
import javax.inject.Inject

class MoviesDataRepository @Inject constructor(
    private val notificationCloudDataStore: MoviesCloudDataStore,
) : BaseDataRepository(), MoviesRepository {


    override suspend fun listMoviesRemote(page: Int): EventResult<ListMovies> {
        return when (val response = notificationCloudDataStore.listMovies(page)) {
            is EventResult.Success -> {
                val dataResponse = response.data?.toModel()
                EventResult.Success(dataResponse)
            }

            is EventResult.Failure -> generateResponseError(response)
        }
    }

}