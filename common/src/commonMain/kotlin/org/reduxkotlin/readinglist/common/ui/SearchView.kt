package org.reduxkotlin.readinglist.common.ui

import org.reduxkotlin.readinglist.common.boundary.toBookListViewState


interface SearchView : LibraryBaseView {
    fun showLoading()
    fun hideLoading()
    fun showError(msg: String)
    fun showResults(books: List<BookListItemViewState>)
    override fun presenter() = searchPresenter
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

val searchP3 = presenterWithViewArg<SearchView> { view ->
    {
        select { state.isLoadingItems } then { view.showLoading() }
    }
}

interface BottomNavSheet: LibraryBaseView {
    override fun presenter() = presenter<BottomNavSheet> { {
        // no op
    }}
}
