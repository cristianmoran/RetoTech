package com.whiz.reto.data.repository

import com.whiz.reto.data.base.BaseDataRepository
import com.whiz.reto.data.local.db.dao.MoviesDao
import com.whiz.reto.data.remote.data_source.movies.MoviesCloudDataStore
import com.whiz.reto.data.remote.entity.movies.toModel
import com.whiz.reto.entity.movies.ListMovies
import com.whiz.reto.entity.movies.Movie
import com.whiz.reto.entity.movies.toModel
import com.whiz.reto.entity.movies.toModelDB
import com.whiz.reto.network.EventResult
import com.whiz.reto.repository.MoviesRepository
import javax.inject.Inject

class MoviesDataRepository @Inject constructor(
    private val notificationCloudDataStore: MoviesCloudDataStore,
    private val moviesDao: MoviesDao
) : BaseDataRepository(), MoviesRepository {

    override suspend fun listMoviesRemote(offset: Int, sizePage: Int): EventResult<ListMovies> {
        return when (val response = notificationCloudDataStore.listMovies(offset, sizePage)) {
            is EventResult.Success -> {
                val dataResponse = response.data?.toModel()
                insertMoviesLocal(dataResponse?.results?.filterNotNull().orEmpty())
                EventResult.Success(dataResponse)
            }

            is EventResult.Failure -> generateResponseError(response)
        }
    }

    override suspend fun listMoviesLocal(limit: Int, offset: Int): EventResult<ListMovies> {
        val listMovies = moviesDao.getAllMovies(limit, offset)
        val data = ListMovies(
            count=175,
            next = "", previous = "", results = listMovies?.map { it.toModel() }.orEmpty()
        )
        return  EventResult.Success(data)

    }

    override suspend fun insertMoviesLocal(movies: List<Movie>) {
        moviesDao.insertMovies(movies.map { it.toModelDB() })
    }

}