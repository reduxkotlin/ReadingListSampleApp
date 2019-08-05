package com.willowtreeapps.common.ui

import com.willowtreeapps.common.boundary.toBookListViewState
import com.willowtreeapps.common.external.ViewWithProvider


interface ReadingListView : ViewWithProvider{
    fun showLoading()
    fun hideLoading()
    fun showError(msg: String)
    fun showBooks(toReadBook: List<Any>)
    override fun presenter() = readingListPresenter
}

const val READING_LIST_TITLE = "Reading List"

val readingListPresenter = presenter<ReadingListView> {
    {
        +{ state.readingList } + { showBooks(state.readingList.toBookListViewState(READING_LIST_TITLE)) }

        +{ state.isLoadingItems } + {
            if (state.isLoadingItems) {
                showLoading()
            } else {
                hideLoading()
            }
        }

        +{ state.errorLoadingItems } + { showError(state.errorMsg) }
    }
}