package com.whiz.data.remote.service

import com.whiz.domain.network.EventResult
import com.whiz.data.remote.entity.movies.ListMoviesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesService {

    @GET("pokemon")
    suspend fun listMovies(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): EventResult<ListMoviesResponse?>

}