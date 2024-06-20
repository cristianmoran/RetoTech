package com.whiz.domain.entity.movies

data class ListMovies(
    var count: Int,
    var next: String,
    var previous: String,
    var results: List<Movie>
)

data class Movie(
    val name: String, val url: String
)