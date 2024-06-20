package com.whiz.data.local.db.entity.movies

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.whiz.reto.data.remote.entity.movies.MovieResponse

@Entity(tableName = "movies_table")
data class MovieDB(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int ,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "url")
    val url: String
)

fun MovieDB?.toModel() = MovieResponse(
    name = this?.name.orEmpty(),
    url = this?.url.orEmpty()
)