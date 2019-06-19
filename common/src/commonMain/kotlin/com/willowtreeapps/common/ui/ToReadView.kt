package com.willowtreeapps.common.ui

import com.willowtreeapps.common.BookListItemViewState
import com.willowtreeapps.common.repo.Book

interface ToReadView : View<ToReadPresenter> {
    fun showLoading()
    fun hideLoading()
    fun showError(msg: String)
    fun showBooks(toReadBook: List<BookListItemViewState>)
}
