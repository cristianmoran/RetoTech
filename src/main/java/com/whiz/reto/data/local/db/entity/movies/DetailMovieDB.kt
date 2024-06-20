package com.whiz.reto.data.local.db.entity.movies

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "detail_movie")
data class DetailMovieDB(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,
    @ColumnInfo(name = "name")
    var name: String = String(),
    @Embedded
    var sprites: Sprites = Sprites(),
    @ColumnInfo(name = "height")
    var height: Int = 0,
    @ColumnInfo(name = "weight")
    var weight: Int = 0
) {
    data class Sprites(
        @ColumnInfo(name = "back_default")
        var backDefault: String = String()
    )

    @Entity(tableName = "types")
    data class Types(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "type_id")
        var typeId: Int = 0,
        @ColumnInfo(name = "slot")
        var slot: Int,
        @ColumnInfo(name = "name")
        var name: String,
        @ColumnInfo(name = "url")
        var url: String,
        @ColumnInfo(name = "movie_id")
        var movieId: Int
    )
}



