package com.whiz.reto.domain.entity.movies

import com.whiz.core.ID_MOVIE_INVALID
import com.whiz.core.extensions.notNull
import com.whiz.reto.data.local.db.entity.movies.MovieDB

data class ListMovies(
    var count: Int,
    var next: String,
    var previous: String,
    var results: List<Movie?>
)

data class Movie(
    val name: String, val url: String, val isFavorite:Boolean = false
) {
    val id: Int
        get() {
            return try {
                val regex = """.*/pokemon/(\d+)/""".toRegex()
                val matchResult = regex.find(url)
                return matchResult?.groups?.get(1)?.value?.toInt() ?: com.whiz.core.ID_MOVIE_INVALID
            } catch (e: Exception) {
                com.whiz.core.ID_MOVIE_INVALID
            }

        }
}

fun MovieDB?.toModel(): Movie = Movie(
    name = this?.name.orEmpty(),
    url = this?.url.orEmpty()
)

fun Movie?.toModelDB() = MovieDB(
    name = this?.name.orEmpty(),
    url = this?.url.orEmpty(),
    id = this?.id.notNull()
)