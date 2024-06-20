package com.whiz.reto.data.local.db.entity.movies

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.whiz.reto.domain.entity.movies.Movie

@Entity(
    tableName = "movies_table",
    indices = [Index(value = ["name"], unique = true)]
)
data class MovieDB(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int? = null,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "url")
    val url: String
)


