package com.willowtreeapps.common.boundary

import com.jackson.openlibrary.Books
import com.willowtreeapps.common.*
import com.willowtreeapps.common.repo.Book
import com.willowtreeapps.common.ui.ListHeader

/**
 * Functions for transforming AppState data into ViewState data to be used by Views.
 */

fun Book.toBookListViewState() = BookListItemViewState(title = title,
        author = authorName.firstOrNull() ?: "Unknown",
        coverImageUrl = largeCoverUrl,
        id = openLibraryId,
        book = this
        )

fun Collection<Book>.toBookListViewState(title: String) =
        listOf<Any>(ListHeader(title)).plus(map { it.toBookListViewState() })

fun Collection<Book>.toBookListViewState() = map { it.toBookListViewState() }

fun Books.toBook() = Book(cover_edition_key = this.openLibraryId, authorName = listOf(this.author ?: "unknown"), title = this.title!!)

fun List<Books>.toBook() = map { it.toBook() }

