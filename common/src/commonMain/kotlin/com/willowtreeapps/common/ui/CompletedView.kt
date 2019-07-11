package com.willowtreeapps.common.ui


interface CompletedView : LibraryView {
    fun showLoading()
    fun hideLoading()
    fun showError(msg: String)
    fun showBooks(books: List<Any>)
    override fun presenter() = completedPresenter
}
