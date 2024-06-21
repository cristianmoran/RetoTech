package com.whiz.core.model

import android.os.Parcelable
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import kotlinx.parcelize.Parcelize

@Parcelize
data class MessageDialogModel(
    var message: String,
    @DrawableRes var image: Int,
    @ColorRes var color: Int? = null,
) : Parcelable
