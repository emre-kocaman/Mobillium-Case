package com.example.mobilliumchallenge.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.mobilliumchallenge.R
import com.example.mobilliumchallenge.base.BaseAdapter
import com.example.mobilliumchallenge.base.BaseHolder
import com.example.mobilliumchallenge.model.entities.common.Result
import com.example.mobilliumchallenge.utils.ImageHelper

class UpcomingMoviesAdapter(context: Context?, list: MutableList<Result?>?) :
    BaseAdapter<Result, UpcomingMoviesHolder>(
        context, list
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpcomingMoviesHolder {
        val v: View = LayoutInflater.from(parent.context).inflate(R.layout.upcoming_movies_cell, parent, false)
        return UpcomingMoviesHolder(v)
    }
}

class UpcomingMoviesHolder(itemView:View) : BaseHolder<Result>(itemView) {
    private val image: ImageView = itemView.findViewById(R.id.image)
    private val title: TextView = itemView.findViewById(R.id.title)
    private val description: TextView = itemView.findViewById(R.id.description)


    override fun onBind(t: Result, position: Int) {
        ImageHelper.loadImage(t.poster_path,image)
        title.text = t.original_title
        description.text = t.title
        itemView.setOnClickListener {

        }
    }

}