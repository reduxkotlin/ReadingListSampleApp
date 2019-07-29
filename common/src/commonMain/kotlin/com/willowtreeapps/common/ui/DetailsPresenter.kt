package com.willowtreeapps.common.ui

import com.willowtreeapps.common.boundary.toBookDetailsViewState

val detailsPresenter = presenter<DetailsView> {
    {
        withAnyChange { render(state.selectedBook!!.toBookDetailsViewState()) }
    }
}