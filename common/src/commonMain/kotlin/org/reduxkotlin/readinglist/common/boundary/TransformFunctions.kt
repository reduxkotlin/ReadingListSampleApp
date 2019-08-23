package org.reduxkotlin.readinglist.common.boundary

import org.reduxkotlin.readinglist.Books
import org.reduxkotlin.readinglist.common.repo.Book
import org.reduxkotlin.readinglist.common.ui.BookDetailViewState
import org.reduxkotlin.readinglist.common.ui.BookListItemViewState
import org.reduxkotlin.readinglist.common.ui.ListHeader

/**
 * Functions for transforming AppState data into ViewState data to be used by Views.
 */

fun Book.toBookDetailsViewState(): BookDetailViewState = BookDetailViewState(book = BookListItemViewState(title = title,
        author = authorName.firstOrNull() ?: "Unknown",
        coverImageUrl = largeCoverUrl,
        id = openLibraryId,
        book = this),
        hasNext = false,
        hasPrev = false,
        publishedDate = publishDates?.firstOrNull() ?: "",
        publisher = publisher?.firstOrNull() ?: "",
        subjects = subject ?: listOf()
        )

fun Book.toBookListViewState() = BookListItemViewState(title = title,
        author = authorName.firstOrNull() ?: "Unknown",
        coverImageUrl = largeCoverUrl,
        id = openLibraryId,
        book = this)

fun Collection<Book>.toBookListViewState(title: String) =
        listOf<Any>(ListHeader(title)).plus(map { it.toBookListViewState() })

fun Collection<Book>.toBookListViewState() = map { it.toBookListViewState() }

fun Books.toBook() = Book(cover_edition_key = this.openLibraryId,
        authorName = listOf(this.author ?: "unknown"),
        title = this.title!!,
        publisher = if (this.publisher != null) listOf(this.publisher!!) else null,
        publishDates = if (this.firstPublished != null) listOf(this.firstPublished!!) else null,
        subject = if (this.subject != null) listOf(this.subject!!) else null)


fun List<Books>.toBook() = map { it.toBook() }

