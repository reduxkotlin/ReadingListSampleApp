package com.willowtreeapps.common.ui


interface ReadingListView : LibraryView, PresenterProvider {
    fun showLoading()
    fun hideLoading()
    fun showError(msg: String)
    fun showBooks(toReadBook: List<Any>)
    override fun presenter() = readingListPresenter
}
