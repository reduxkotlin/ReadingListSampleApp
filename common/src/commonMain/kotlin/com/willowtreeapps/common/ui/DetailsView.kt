package com.willowtreeapps.common.ui

import com.willowtreeapps.common.BookListItemViewState

interface DetailsView: LibraryView {
    fun render(book: BookListItemViewState)
    override fun presenter() = detailsPresenter
}
