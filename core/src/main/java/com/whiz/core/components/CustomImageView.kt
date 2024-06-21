package com.whiz.core.components

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.whiz.core.R
import com.bumptech.glide.request.target.Target

class CustomImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val imageView: ImageView
    private val initialsTextView: TextView
    private var placeholder: Drawable? = null
    private var textColor: Int = 0
    private var backgroundColor: Int = 0

    init {
        LayoutInflater.from(context).inflate(R.layout.custom_image, this, true)
        imageView = findViewById(R.id.imageView)
        initialsTextView = findViewById(R.id.initialsTextView)

        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CustomImageView,
            0, 0
        ).apply {
            try {
                placeholder = getDrawable(R.styleable.CustomImageView_placeholder)
                textColor = getColor(
                    R.styleable.CustomImageView_textColor,
                    ContextCompat.getColor(context, android.R.color.black)
                )
                backgroundColor = getColor(
                    R.styleable.CustomImageView_backgroundColor,
                    ContextCompat.getColor(context, android.R.color.white)
                )
            } finally {
                recycle()
            }
        }

        initialsTextView.setTextColor(textColor)
        initialsTextView.background = ContextCompat.getDrawable(context, R.drawable.circular_background_white)

    }

    fun setImage(url: String?, name: String?) {
        if (!url.isNullOrEmpty()) {
            Glide.with(context)
                .load(url)
                .skipMemoryCache(true)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .apply(RequestOptions().error(placeholder))
                .addListener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        imageView.visibility = GONE
                        initialsTextView.visibility = VISIBLE
                        showInitialsOrPlaceholder(name)
                        return true
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        imageView.visibility = VISIBLE
                        initialsTextView.visibility = GONE
                        return false
                    }
                })
                .into(imageView)
        } else {
            showInitialsOrPlaceholder(name)
        }
    }

    private fun showInitialsOrPlaceholder(name: String?) {
        if (name.isNullOrEmpty()) {
            imageView.setImageDrawable(placeholder)
            imageView.visibility = VISIBLE
            initialsTextView.visibility = GONE
        } else {
            val initials = getInitials(name)
            if (initials.isEmpty()) {
                imageView.setImageDrawable(placeholder)
                imageView.visibility = VISIBLE
                initialsTextView.visibility = GONE
            } else {
                initialsTextView.text = initials
                initialsTextView.visibility = VISIBLE
                imageView.visibility = GONE
            }
        }
    }

    private fun getInitials(name: String): String {
        val words = name.trim().split("\\s+".toRegex())
        return when {
            words.isEmpty() -> ""
            words.size == 1 -> words[0].firstOrNull()?.toUpperCase()?.toString() ?: ""
            else -> {
                val initials = words.take(2).mapNotNull { it.firstOrNull()?.toUpperCase() }
                initials.joinToString("")
            }
        }
    }
}