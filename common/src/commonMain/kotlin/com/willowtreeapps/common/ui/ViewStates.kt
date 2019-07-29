package com.willowtreeapps.common.ui

import com.willowtreeapps.common.repo.Book


data class BookListItemViewState(
        val title: String,
        val author: String,
        val coverImageUrl: String,
        val id: String,
        val book: Book)

data class BookDetailViewState(
        val book: BookListItemViewState,
        val hasPrev: Boolean,
        val hasNext: Boolean
)
