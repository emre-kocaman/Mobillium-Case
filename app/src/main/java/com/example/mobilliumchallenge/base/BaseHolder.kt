package com.example.mobilliumchallenge.base

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun onBind(t: T, position: Int)


    open fun getContext(): Context? {
        return itemView.context
    }


}