package com.willowtreeapps.common

import com.willowtreeapps.common.repo.Book


data class BookListItemViewState(
        val title: String,
        val author: String,
        val coverImageUrl: String,
        val id: String,
        val book: Book)

