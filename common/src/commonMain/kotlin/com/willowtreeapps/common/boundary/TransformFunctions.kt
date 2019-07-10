package com.willowtreeapps.common.boundary

import com.jackson.openlibrary.Books
import com.willowtreeapps.common.*
import com.willowtreeapps.common.repo.Book

/**
 * Functions for transforming AppState data into ViewState data to be used by Views.
 */

fun Book.toBookListViewState() = BookListItemViewState(title = title,
        author = authorName.firstOrNull() ?: "Unknown",
        coverImageUrl = coverUrl,
        id = openLibraryId)

fun Collection<Book>.toBookListViewState() = map { it.toBookListViewState() }

fun Books.toBook() = Book(cover_edition_key = this.openLibraryId, authorName = listOf(this.author ?: "unknown"), title = this.title!!)

fun List<Books>.toBook() = map { it.toBook() }
