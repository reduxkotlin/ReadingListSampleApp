package com.willowtreeapps.common.ui

import com.willowtreeapps.common.boundary.toBookListViewState

val completedPresenter = presenter<CompletedView> {{
    withSingleField({it.completed}) { showBooks(state.completed.toList().toBookListViewState())}
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
}}

