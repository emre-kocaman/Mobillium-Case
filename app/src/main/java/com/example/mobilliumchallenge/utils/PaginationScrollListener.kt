package com.example.mobilliumchallenge.utils

import androidx.recyclerview.widget.RecyclerView


abstract class PaginationScrollListener : RecyclerView.OnScrollListener() {
    abstract fun isLastPage(): Boolean
    abstract fun isLoading(): Boolean
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (!isLoading() && !isLastPage() && !recyclerView.canScrollVertically(1)) {
            loadMoreItems()
        }
    }


    abstract fun loadMoreItems()
}