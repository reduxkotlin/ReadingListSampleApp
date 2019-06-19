package com.willowtreeapps.common.ui

import com.willowtreeapps.common.BookListItemViewState


interface DetailsView : View<DetailsPresenter?> {
    fun render(book: BookListItemViewState)
}
