package com.example.mobilliumchallenge.utils

import android.widget.ImageView
import com.squareup.picasso.Picasso

object ImageHelper {

    const val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/original"

    fun loadImage(url: String?, imageView:ImageView){
        Picasso.get().load(BASE_IMAGE_URL + url).into(imageView)
    }
    fun loadImage(id: Int?, imageView:ImageView){
        id?.let { Picasso.get().load(it).into(imageView) }
    }
}