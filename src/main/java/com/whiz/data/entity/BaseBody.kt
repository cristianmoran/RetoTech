package com.whiz.data.entity

import com.google.gson.annotations.SerializedName

data class BaseBody<T>(

    @SerializedName("successMessage") var successMessage: String,
    @SerializedName("data") var data: T,
    @SerializedName("game") var game: GameBaseResponse? = null

)

data class GameBaseResponse(

    @SerializedName("active")          var active: Boolean = false,
    @SerializedName("transactionCode") var transactionCode: String = ""

)