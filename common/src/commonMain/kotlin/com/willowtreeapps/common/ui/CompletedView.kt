package com.willowtreeapps.common.ui

import com.willowtreeapps.common.external.ViewWithProvider


interface CompletedView : ViewWithProvider {
    fun showLoading()
    fun hideLoading()
    fun showError(msg: String)
    fun showBooks(books: List<Any>)
    override fun presenter() = completedPresenter
}
