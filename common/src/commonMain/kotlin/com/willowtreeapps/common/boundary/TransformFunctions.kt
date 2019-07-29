package com.willowtreeapps.common.boundary

import com.willowtreeapps.openlibrary.Books
import com.willowtreeapps.common.repo.Book
import com.willowtreeapps.common.ui.BookDetailViewState
import com.willowtreeapps.common.ui.BookListItemViewState
import com.willowtreeapps.common.ui.ListHeader

/**
 * Functions for transforming AppState data into ViewState data to be used by Views.
 */

fun Book.toBookDetailsViewState(): BookDetailViewState = BookDetailViewState(book = BookListItemViewState(title = title,
        author = authorName.firstOrNull() ?: "Unknown",
        coverImageUrl = largeCoverUrl,
        id = openLibraryId,
        book = this),
        hasNext = false,
        hasPrev = false)

fun Book.toBookListViewState() = BookListItemViewState(title = title,
        author = authorName.firstOrNull() ?: "Unknown",
        coverImageUrl = largeCoverUrl,
        id = openLibraryId,
        book = this)

fun Collection<Book>.toBookListViewState(title: String) =
        listOf<Any>(ListHeader(title)).plus(map { it.toBookListViewState() })

fun Collection<Book>.toBookListViewState() = map { it.toBookListViewState() }

fun Books.toBook() = Book(cover_edition_key = this.openLibraryId, authorName = listOf(this.author
        ?: "unknown"), title = this.title!!)

fun List<Books>.toBook() = map { it.toBook() }

