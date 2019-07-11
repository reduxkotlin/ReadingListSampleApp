package com.willowtreeapps.common.ui

import com.willowtreeapps.common.boundary.toBookListViewState

const val READING_LIST_TITLE = "Reading List"

val readingListPresenter = presenter<ReadingListView> {
    {
        +{ state.toReadBook }+ { showBooks(state.toReadBook.toBookListViewState(READING_LIST_TITLE)) }

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
