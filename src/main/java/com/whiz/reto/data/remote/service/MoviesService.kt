package com.whiz.reto.data.remote.service

import com.whiz.reto.data.remote.entity.movies.ListMoviesResponse
import com.whiz.reto.network.EventResult
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesService {

    @GET("pokemon")
    suspend fun listMovies(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): EventResult<ListMoviesResponse?>

}