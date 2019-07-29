package com.willowtreeapps.common.ui

interface SearchView : LibraryView {
    fun showLoading()
    fun hideLoading()
    fun showError(msg: String)
    fun showResults(books: List<BookListItemViewState>)
    override fun presenter() = searchPresenter
}

interface BottomNavSheet: LibraryView {
    override fun presenter() = presenter<LibraryView> { {

    }}
}
