package com.willowtreeapps.common.ui

import com.willowtreeapps.common.AppState
import com.willowtreeapps.common.external.PresenterProvider


interface ReadingListView : LibraryView, PresenterProvider<AppState> {
    fun showLoading()
    fun hideLoading()
    fun showError(msg: String)
    fun showBooks(toReadBook: List<Any>)
    override fun presenter() = readingListPresenter
}
