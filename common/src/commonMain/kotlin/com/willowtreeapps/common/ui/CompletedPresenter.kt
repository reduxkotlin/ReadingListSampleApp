package com.willowtreeapps.common.ui

import com.willowtreeapps.common.boundary.toBookListViewState

const val COMPLETED_TITLE = "Completed"

val completedPresenter = presenter<CompletedView> {
    {
        withAnyChange { showTitle(COMPLETED_TITLE) }
        +{ state.completed } + { showBooks(state.completed.toList().toBookListViewState()) }
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

