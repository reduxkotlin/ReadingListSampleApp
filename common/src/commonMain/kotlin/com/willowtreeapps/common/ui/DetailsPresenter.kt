package com.willowtreeapps.common.ui

import com.willowtreeapps.common.ViewUpdater
import com.willowtreeapps.common.boundary.toBookListViewState

val detailsPresenter = presenter<DetailsView> {
    {
        withAnyChange { render(state.selectedBook!!.toBookListViewState()) }
    }
}