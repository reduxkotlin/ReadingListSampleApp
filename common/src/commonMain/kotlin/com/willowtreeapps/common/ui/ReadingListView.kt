package com.willowtreeapps.common.ui

import com.willowtreeapps.common.external.ViewWithProvider


interface ReadingListView : ViewWithProvider{
    fun showLoading()
    fun hideLoading()
    fun showError(msg: String)
    fun showBooks(toReadBook: List<Any>)
    override fun presenter() = readingListPresenter
}
