package com.willowtreeapps.common.ui

import com.willowtreeapps.common.boundary.toBookListViewState


val searchPresenter = presenter<SearchView> {
    {
        +{ state.isLoadingItems } + {
            if (state.isLoadingItems) {
                showLoading()
            } else {
                hideLoading()
            }
        }

        +{ state.errorLoadingItems } + { showError(state.errorMsg) }

        +{ state.searchBooks }+{ showResults(state.searchBooks.toBookListViewState()) }
    }
}
