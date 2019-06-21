package com.willowtreeapps.common.ui

import com.willowtreeapps.common.boundary.toBookListViewState

val toReadPresenter = presenter<ToReadView> {
    {
        withSingleField({ it.toReadBook }) { showBooks(state.toReadBook.toList().toBookListViewState()) }

        withSingleField({ it.isLoadingItems }) {
            if (state.isLoadingItems) {
                showLoading()
            } else {
                hideLoading()
            }
        }

        withSingleField({ it.errorLoadingItems }) {
            showError(state.errorMsg)
        }
    }
}
