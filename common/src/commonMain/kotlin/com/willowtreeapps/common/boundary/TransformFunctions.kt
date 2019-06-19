package com.willowtreeapps.common.boundary

import com.willowtreeapps.common.*
import com.willowtreeapps.common.repo.Book

/**
 * Functions for transforming AppState data into ViewState data to be used by Views.
 */

fun Book.toBookListViewState() = BookListItemViewState(title = title,
        author = authorName.firstOrNull() ?: "Unknown",
        coverImageUrl = coverUrl,
        id = openLibraryId)

fun List<Book>.toBookListViewState() = map { it.toBookListViewState() }
