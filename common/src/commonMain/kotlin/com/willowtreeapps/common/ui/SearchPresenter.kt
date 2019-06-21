package com.willowtreeapps.common.ui

import com.willowtreeapps.common.*
import com.willowtreeapps.common.boundary.toBookListViewState

//a viewupdater typed to our app's AppState for convenience
fun <V: LibraryView> presenter(actions: ViewUpdaterBuilder<AppState,V>): ViewUpdater<View<AppState>> {
    return viewUpdater(actions) as ViewUpdater<View<AppState>>
}

val searchPresenter = presenter<SearchView> {
    {

        on{it.isLoadingItems} + { showLoading() }

        on{ it.isLoadingItems} + {
            if (state.isLoadingItems) {
                showLoading()
            } else {
                hideLoading()
            }
        }


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

        withSingleField({it.searchBooks}) {
            showResults(state.searchBooks.toBookListViewState())
        }
    }
}


