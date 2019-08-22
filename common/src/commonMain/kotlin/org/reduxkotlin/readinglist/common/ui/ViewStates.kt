package org.reduxkotlin.readinglist.common.ui

import org.reduxkotlin.readinglist.common.repo.Book

/**
 * ViewStates are data in format needed to display.
 */

data class BookListItemViewState(
        val title: String,
        val author: String,
        val coverImageUrl: String,
        val id: String,
        val book: Book)

data class BookDetailViewState(
        val book: BookListItemViewState,
        val hasPrev: Boolean,
        val hasNext: Boolean,
        val publisher: String,
        val publishedDate: String,
        val subjects: List<String>
)
