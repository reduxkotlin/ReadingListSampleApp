package com.willowtreeapps.common.ui

import com.willowtreeapps.common.AppState
import com.willowtreeapps.common.external.PresenterProvider


interface CompletedView : LibraryView, PresenterProvider<AppState> {
    fun showLoading()
    fun hideLoading()
    fun showError(msg: String)
    fun showBooks(books: List<Any>)
    override fun presenter() = completedPresenter
}
