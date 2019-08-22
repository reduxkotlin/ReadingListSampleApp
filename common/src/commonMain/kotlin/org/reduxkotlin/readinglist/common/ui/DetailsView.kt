package org.reduxkotlin.readinglist.common.ui

import org.reduxkotlin.readinglist.common.boundary.toBookDetailsViewState

interface DetailsView: LibraryBaseView {
    fun render(detailsViewState: BookDetailViewState)
    override fun presenter() = detailsPresenter
}

val detailsPresenter = presenter<DetailsView> {
    {
        withAnyChange { render(state.selectedBook!!.toBookDetailsViewState()) }
    }
}