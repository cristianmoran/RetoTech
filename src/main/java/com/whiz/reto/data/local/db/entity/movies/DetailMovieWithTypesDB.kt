package com.whiz.reto.data.local.db.entity.movies

import androidx.room.Embedded
import androidx.room.Relation
import com.whiz.reto.domain.entity.movies.DetailMovie

data class DetailMovieWithTypesDB(
    @Embedded val detailMovie: DetailMovieDB,
    @Relation(
        parentColumn = "id",
        entityColumn = "movie_id"
    )
    val types: List<DetailMovieDB.Types>
)

