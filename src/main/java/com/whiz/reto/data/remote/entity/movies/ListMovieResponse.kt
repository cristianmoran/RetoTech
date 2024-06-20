package com.whiz.reto.data.remote.entity.movies

import com.google.gson.annotations.SerializedName
import com.whiz.domain.entity.movies.ListMovies
import com.whiz.domain.entity.movies.Movie

data class ListMoviesResponse(
    @SerializedName("count")
    var count: Int?,
    @SerializedName("next")
    var next: String?,
    @SerializedName("previous")
    var previous: String?,
    @SerializedName("results")
    var results: List<MovieResponse>?
)

data class MovieResponse(
    @SerializedName("name") val name: String?,
    @SerializedName("url") val url: String?
)

fun ListMoviesResponse.toModel() = ListMovies(
    count = this.count ?: 0,
    next = this.next.orEmpty(),
    previous = this.previous.orEmpty(),
    results = this.results?.map { it.toModel() }.orEmpty()
)

fun MovieResponse.toModel() = Movie(
    name = this.name.orEmpty(), url = this.url.orEmpty()
)