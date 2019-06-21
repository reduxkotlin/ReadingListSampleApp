package com.willowtreeapps.common.ui

import com.willowtreeapps.common.BookListItemViewState

interface CompletedView : LibraryView {
    fun showLoading()
    fun hideLoading()
    fun showError(msg: String)
    fun showBooks(books: List<BookListItemViewState>)
    override fun viewUpdater() = completedPresenter
}
