package com.willowtreeapps.common.ui

import com.willowtreeapps.common.boundary.toBookDetailsViewState
import com.willowtreeapps.common.external.ViewWithProvider

interface DetailsView: ViewWithProvider{
    fun render(detailsViewState: BookDetailViewState)
    override fun presenter() = detailsPresenter
}
val detailsPresenter = presenter<DetailsView> {
    {
        withAnyChange { render(state.selectedBook!!.toBookDetailsViewState()) }
    }
}