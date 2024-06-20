package com.whiz.reto.data.repository

import com.whiz.reto.data.base.BaseDataRepository
import com.whiz.reto.data.local.db.dao.MovieDetailDao
import com.whiz.reto.data.local.db.dao.MoviesDao
import com.whiz.reto.data.local.db.entity.movies.DetailMovieDB
import com.whiz.reto.data.remote.data_source.movies.MoviesCloudDataStore
import com.whiz.reto.data.remote.entity.movies.toModel
import com.whiz.reto.domain.entity.movies.DetailMovie
import com.whiz.reto.domain.entity.movies.ListMovies
import com.whiz.reto.domain.entity.movies.Movie
import com.whiz.reto.domain.entity.movies.toModel
import com.whiz.reto.domain.entity.movies.toModelDB
import com.whiz.reto.core.network.EventResult
import com.whiz.reto.domain.repository.MoviesRepository
import javax.inject.Inject

class MoviesDataRepository @Inject constructor(
    private val notificationCloudDataStore: MoviesCloudDataStore,
    private val moviesDao: MoviesDao,
    private val movieDetailDao: MovieDetailDao
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
        val sizeTotal = moviesDao.getSizeTotalMovies()
        val listMovies = moviesDao.getAllMovies(limit, offset)
        val data = ListMovies(
            count = sizeTotal,
            next = String(),
            previous = String(),
            results = listMovies?.map { it.toModel() }.orEmpty()
        )
        return EventResult.Success(data)
    }

    override suspend fun detailMovieRemote(id: Int): EventResult<DetailMovie> {
        return when (val response = notificationCloudDataStore.detailMovie(id)) {
            is EventResult.Success -> {
                val dataResponse = response.data?.toModel()
                insertMovieDetailLocal(dataResponse)
                EventResult.Success(dataResponse)
            }

            is EventResult.Failure -> generateResponseError(response)
        }
    }

    override suspend fun detailMovieLocal(id: Int): EventResult<DetailMovie?> {
        val detailMovie = movieDetailDao.getDetailMovie(id)
        return EventResult.Success(detailMovie.toModel())
    }

    private suspend fun insertMoviesLocal(movies: List<Movie>) {
        moviesDao.insertMovies(movies.map { it.toModelDB() })
    }

    private suspend fun insertMovieDetailLocal(movieDetail: DetailMovie?) {
        val typesDB = movieDetail?.types?.map { it.toModelDB(movieDetail.id) }.orEmpty()
        movieDetailDao.insertDetailMovieWithTypes(
            movieDetail?.toModelDB() ?: DetailMovieDB(),
            typesDB
        )
    }

}