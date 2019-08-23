package org.reduxkotlin.readinglist.common.repo

import org.reduxkotlin.readinglist.common.LibraryDatabase
import org.reduxkotlin.readinglist.common.boundary.toBook

class BookDatabaseRepo(private val database: LibraryDatabase) {

    fun loadToRead() = database.booksModelQueries.selectAllToRead().executeAsList().toBook()

    fun loadCompleted() = database.booksModelQueries.selectAllCompleted().executeAsList().toBook()

    fun insertToRead(book: Book) {
        database.booksModelQueries.insertItem(
                author = book.authorName.firstOrNull(),
                title = book.title,
                openLibraryId = book.openLibraryId,
                firstPublished = book.originalPublishDate?.toString(),
                publisher = book.publisher?.firstOrNull(),
                subject = book.subject?.firstOrNull(),
                toRead = 1,
                completed = 0)
    }

    fun insertCompleted(book: Book) {
        database.booksModelQueries.insertItem(
                author = book.authorName.firstOrNull(),
                title = book.title,
                openLibraryId = book.openLibraryId,
                firstPublished = book.originalPublishDate?.toString(),
                publisher = book.publisher?.firstOrNull(),
                subject = book.subject?.firstOrNull(),
                toRead = 0,
                completed = 1)
    }
}