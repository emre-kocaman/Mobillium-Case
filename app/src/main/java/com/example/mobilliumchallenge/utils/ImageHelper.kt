package com.example.mobilliumchallenge.utils

import android.widget.ImageView
import com.squareup.picasso.Picasso

object ImageHelper {

    //w500
    const val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/original"
    const val BASE_IMAGE_URL_WITHOUT_QUALITY = "https://image.tmdb.org/t/p/"

    fun loadImage(url: String?, imageView:ImageView){
        Picasso.get().load(BASE_IMAGE_URL + url).into(imageView)
    }
    fun loadImage(url: String?, imageView:ImageView,quality:QUALITY){
        Picasso.get().load(BASE_IMAGE_URL_WITHOUT_QUALITY + quality.name + url).into(imageView)
    }
    fun loadImage(id: Int?, imageView:ImageView){
        id?.let { Picasso.get().load(it).into(imageView) }
    }

    enum class QUALITY{
        original,
        w500
    }
}