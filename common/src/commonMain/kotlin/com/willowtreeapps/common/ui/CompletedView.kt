package com.willowtreeapps.common.ui


interface CompletedView : LibraryView, PresenterProvider {
    fun showLoading()
    fun hideLoading()
    fun showError(msg: String)
    fun showBooks(books: List<Any>)
    override fun presenter() = completedPresenter
}
