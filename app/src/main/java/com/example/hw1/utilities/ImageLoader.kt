package com.example.hw1.utilities

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.constraintlayout.widget.Placeholder
import com.bumptech.glide.Glide
import com.example.hw1.R
import java.lang.ref.WeakReference

class ImageLoader private constructor(context: Context) {
    private val contextRef = WeakReference(context)

    fun load(
        source: Drawable,
        imageView: ImageView,
        placeholder: Int = R.drawable.image_18_09_2024_at_1_16
    ) {
        contextRef.get()?.let { context ->
            Glide
                .with(context)
                .load(source)
                .placeholder(placeholder)
                .centerCrop()
                .into(imageView)
        }
    }

    fun load(
        source: String,
        imageView: ImageView,
        placeholder: Int = R.drawable.image_18_09_2024_at_1_16
    ) {
        contextRef.get()?.let { context ->
            Glide
                .with(context)
                .load(source)
                .placeholder(placeholder)
                .centerCrop()
                .into(imageView)
        }
    }

    companion object {

        @Volatile
        private var instance: ImageLoader? = null

        fun init(context: Context): ImageLoader {

            return instance ?: synchronized(this) {
                instance ?: ImageLoader(context).also { instance = it }
            }
        }

        fun getInstance(): ImageLoader {
            return instance
                ?: throw IllegalStateException("ImageLoader must be initialized by calling init(context) before use.")
        }
    }


}