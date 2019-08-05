package com.willowtreeapps.common.ui

import com.willowtreeapps.common.external.PresenterProvider

interface DetailsView: LibraryView{
    fun render(detailsViewState: BookDetailViewState)
    override fun presenter() = detailsPresenter
}
