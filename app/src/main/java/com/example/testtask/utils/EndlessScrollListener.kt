package com.example.testtask.utils

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


abstract class EndlessScrollListener(layoutManager: GridLayoutManager) :
    RecyclerView.OnScrollListener() {

    private var visibleThreshold = 2
    private var currentPage = 1
    private var previousTotalItemCount = 0
    private var loading = true
    private val mLayoutManager: RecyclerView.LayoutManager

    init {
        mLayoutManager = layoutManager
        visibleThreshold *= layoutManager.spanCount
    }

    override fun onScrolled(view: RecyclerView, dx: Int, dy: Int) {
        val totalItemCount = mLayoutManager.itemCount
        val lastVisibleItemPosition =
            (mLayoutManager as GridLayoutManager).findLastVisibleItemPosition()
        if (loading && totalItemCount > previousTotalItemCount) {
            loading = false
            previousTotalItemCount = totalItemCount
        }
        if (!loading && lastVisibleItemPosition + visibleThreshold > totalItemCount) {
            currentPage++
            onLoadMore(currentPage, totalItemCount, view)
            loading = true
        }
    }

    abstract fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?)
}