package com.whiz.reto.entity.movies

import com.whiz.data.local.db.entity.movies.MovieDB

data class ListMovies(
    var count: Int,
    var next: String,
    var previous: String,
    var results: List<Movie?>
)

data class Movie(
    val name: String, val url: String
)

fun MovieDB?.toModel() = Movie(
    name = this?.name.orEmpty(),
    url = this?.url.orEmpty()
)

fun Movie?.toModelDB() = MovieDB(
    name = this?.name.orEmpty(),
    url = this?.url.orEmpty()
)