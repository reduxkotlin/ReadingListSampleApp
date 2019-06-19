package com.willowtreeapps.common.ui

import com.willowtreeapps.common.BookListItemViewState

interface ToReadView : View<ToReadPresenter?> {
    fun showLoading()
    fun hideLoading()
    fun showError(msg: String)
    fun showBooks(toReadBook: List<BookListItemViewState>)
}
