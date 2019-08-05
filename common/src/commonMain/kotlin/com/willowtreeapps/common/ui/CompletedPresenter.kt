package com.willowtreeapps.common.ui

import com.willowtreeapps.common.boundary.toBookListViewState

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

