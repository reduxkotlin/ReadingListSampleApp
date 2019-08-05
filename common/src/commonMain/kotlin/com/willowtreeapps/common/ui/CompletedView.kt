package com.willowtreeapps.common.ui

import com.willowtreeapps.common.boundary.toBookListViewState
import com.willowtreeapps.common.external.ViewWithProvider


interface CompletedView : ViewWithProvider {
    fun showLoading()
    fun hideLoading()
    fun showError(msg: String)
    fun showBooks(books: List<Any>)
    override fun presenter() = completedPresenter
}

const val COMPLETED_TITLE = "Completed"

val completedPresenter = presenter<CompletedView> {
    {
        +{ state.completedList } + { showBooks(state.completedList.toList().toBookListViewState(COMPLETED_TITLE)) }
        +{ state.isLoadingItems } + {
            if (state.isLoadingItems) {
                showLoading()
            } else {
                hideLoading()
            }
        }

        +{ state.errorLoadingItems } + {
            showError(state.errorMsg)
        }
    }
}
