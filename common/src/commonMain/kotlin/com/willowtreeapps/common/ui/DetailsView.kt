package com.willowtreeapps.common.ui

interface DetailsView: LibraryView {
    fun render(detailsViewState: BookDetailViewState)
    override fun presenter() = detailsPresenter
}
