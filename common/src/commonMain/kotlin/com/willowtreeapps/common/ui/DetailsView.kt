package com.willowtreeapps.common.ui

import com.willowtreeapps.common.external.ViewWithProvider

interface DetailsView: ViewWithProvider{
    fun render(detailsViewState: BookDetailViewState)
    override fun presenter() = detailsPresenter
}
