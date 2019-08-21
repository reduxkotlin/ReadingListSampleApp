package com.willowtreeapps.common.ui

import com.willowtreeapps.common.boundary.toBookDetailsViewState

interface DetailsView: LibraryBaseView {
    fun render(detailsViewState: BookDetailViewState)
    override fun presenter() = detailsPresenter
}

val detailsPresenter = presenter<DetailsView> {
    {
        withAnyChange { render(state.selectedBook!!.toBookDetailsViewState()) }
    }
}