package com.willowtreeapps.common.ui

import com.willowtreeapps.common.boundary.toBookListViewState

val searchP3 = presenterWithViewArg<SearchView> { view ->
    {
        select { state.isLoadingItems } then { view.showLoading() }
    }
}

val searchPresenter = presenter<SearchView> {
    {
        select { state.isLoadingItems } then {
            if (state.isLoadingItems) {
                showLoading()
            } else {
                hideLoading()
            }
        }

        +{ state.searchBooks } + { showResults(state.searchBooks.toBookListViewState()) }
        select { state.searchBooks } then { showResults(state.searchBooks.toBookListViewState()) }
    }
}
