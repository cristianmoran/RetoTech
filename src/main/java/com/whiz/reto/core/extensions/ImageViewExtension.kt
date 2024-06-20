package com.whiz.reto.core.extensions

import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat


fun ImageView.updateImageDrawable(@DrawableRes image: Int) {
    this.setImageDrawable(ContextCompat.getDrawable(this.context, image))
}

//fun ImageView.updateImageFromGlide(url: String) {
//    Glide.with(this.context)
//        .load(url)
//        .skipMemoryCache(true)
//        .thumbnail(Glide.with(this.context).load(R.drawable.loading))
//        .diskCacheStrategy(DiskCacheStrategy.NONE)
//        .into(this)
//}

fun ImageView.updateColorTint(@ColorRes color: Int) {
    this.setColorFilter(ContextCompat.getColor(this.context, color))
}
