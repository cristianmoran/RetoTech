package com.whiz.reto.data.remote.entity.movies

import com.google.gson.annotations.SerializedName
import com.whiz.reto.domain.entity.movies.DetailMovie


data class DetailMovieResponse(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("sprites") var sprites: Sprites? = Sprites(),
    @SerializedName("height") var height: Int? = null,
    @SerializedName("weight") var weight: Int? = null,
    @SerializedName("types") var types: ArrayList<Types>? = arrayListOf()
) {
    data class Sprites(
        @SerializedName("back_default") var backDefault: String? = null
    )

    data class Types(
        @SerializedName("slot") var slot: Int? = null,
        @SerializedName("type") var type: Type? = Type()
    ) {
        data class Type(
            @SerializedName("name") var name: String? = null,
            @SerializedName("url") var url: String? = null
        )
    }
}


fun DetailMovieResponse.toModel() = DetailMovie(
    id = this.id ?: 0,
    name = this.name.orEmpty(),
    sprites = this.sprites?.toModel(),
    height = this.height ?: 0,
    weight = this.weight ?: 0,
    types = this.types?.map { it.toModel() }.orEmpty()
)

fun DetailMovieResponse.Sprites?.toModel() = DetailMovie.Sprites(
    backDefault = this?.backDefault.orEmpty()
)

fun DetailMovieResponse.Types?.toModel() = DetailMovie.Types(
    slot = this?.slot ?: 0,
    name = this?.type?.name.orEmpty(),
    url = this?.type?.url.orEmpty(),
)
