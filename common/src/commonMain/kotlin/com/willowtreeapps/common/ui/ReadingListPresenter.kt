package com.willowtreeapps.common.ui

import com.willowtreeapps.common.boundary.toBookListViewState

const val READING_LIST_TITLE = "Reading List"

val readingListPresenter = presenter<ReadingListView> {
    {
        withAnyChange { showTitle(READING_LIST_TITLE) }
        +{ state.toReadBook }+ { showBooks(state.toReadBook.toBookListViewState()) }

        +{ state.isLoadingItems }+ {
            if (state.isLoadingItems) {
                showLoading()
            } else {
                hideLoading()
            }
        }

        +{ state.errorLoadingItems }+ {
            showError(state.errorMsg)
        }
    }
}
