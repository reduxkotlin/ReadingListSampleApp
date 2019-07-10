package com.willowtreeapps.common.ui

import com.willowtreeapps.common.BookListItemViewState

interface ReadingListView : LibraryView {
    fun showTitle(title:String)
    fun showLoading()
    fun hideLoading()
    fun showError(msg: String)
    fun showBooks(toReadBook: List<BookListItemViewState>)
    override fun presenter() = readingListPresenter
}
