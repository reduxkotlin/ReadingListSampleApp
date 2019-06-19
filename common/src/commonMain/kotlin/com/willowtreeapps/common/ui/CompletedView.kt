package com.willowtreeapps.common.ui

interface CompletedView : View<CompletedPresenter> {
    fun showLoading()
    fun hideLoading()
    fun showError(msg: String)
}
