package com.whiz.reto.domain.entity.movies

import android.os.Parcelable
import com.whiz.reto.data.local.db.entity.movies.DetailMovieDB
import com.whiz.reto.data.local.db.entity.movies.DetailMovieWithTypesDB
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailMovie(
    var id: Int,
    var name: String,
    var sprites: Sprites?,
    var height: Int,
    var weight: Int,
    var types: List<Types>
) : Parcelable {
    @Parcelize
    data class Sprites(
        var backDefault: String
    ) : Parcelable

    @Parcelize
    data class Types(
        var slot: Int,
        var name: String,
        var url: String
    ) : Parcelable
}

fun DetailMovieWithTypesDB?.toModel() = DetailMovie(
    id = this?.detailMovie?.id ?: 0,
    name = this?.detailMovie?.name.orEmpty(),
    sprites = this?.detailMovie?.sprites?.toModel(),
    height = this?.detailMovie?.height?:0,
    weight = this?.detailMovie?.weight?:0,
    types = this?.types?.map { it.toModel() }.orEmpty()
)

fun DetailMovieDB.Sprites?.toModel() = DetailMovie.Sprites(
    backDefault = this?.backDefault.orEmpty()
)

fun DetailMovieDB.Types?.toModel() = DetailMovie.Types(
    slot = this?.slot ?: 0,
    name = this?.name.orEmpty(),
    url = this?.url.orEmpty(),
)

fun DetailMovie?.toModelDB() = DetailMovieDB(
    id = this?.id ?: 0,
    name = this?.name.orEmpty(),
    sprites = this?.sprites?.toModel() ?: DetailMovieDB.Sprites(),
    height = this?.height ?: 0,
    weight = this?.weight ?: 0,
)

fun DetailMovie.Sprites?.toModel(): DetailMovieDB.Sprites = DetailMovieDB.Sprites(
    backDefault = this?.backDefault.orEmpty()
)

fun DetailMovie.Types?.toModelDB(movieId: Int) = DetailMovieDB.Types(
    typeId = 0,
    slot = this?.slot ?: 0,
    name = this?.name.orEmpty(),
    url = this?.url.orEmpty(),
    movieId = movieId
)