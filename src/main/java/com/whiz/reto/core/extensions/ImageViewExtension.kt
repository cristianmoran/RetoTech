package com.whiz.reto.core.extensions

import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat


fun ImageView.updateImageDrawable(@DrawableRes image: Int) {
    this.setImageDrawable(ContextCompat.getDrawable(this.context, image))
}

fun ImageView.updateColorTint(@ColorRes color: Int) {
    this.setColorFilter(ContextCompat.getColor(this.context, color))
}
