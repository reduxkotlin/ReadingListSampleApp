package com.willowtreeapps.common.ui

import com.willowtreeapps.common.BookListItemViewState

interface ToReadView : LibraryView {
    fun showLoading()
    fun hideLoading()
    fun showError(msg: String)
    fun showBooks(toReadBook: List<BookListItemViewState>)
    override fun viewUpdater() = toReadPresenter
}
