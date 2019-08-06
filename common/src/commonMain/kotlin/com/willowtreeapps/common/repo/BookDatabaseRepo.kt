package com.willowtreeapps.common.repo

import com.willowtree.common.LibraryDatabase
import com.willowtreeapps.common.boundary.toBook

class BookDatabaseRepo(private val database: LibraryDatabase) {

    fun loadToRead() = database.booksModelQueries.selectAllToRead().executeAsList().toBook()

    fun loadCompleted() = database.booksModelQueries.selectAllCompleted().executeAsList().toBook()

    fun insertToRead(book: Book) {
        database.booksModelQueries.insertItem(book.authorName.firstOrNull(), book.title, book.openLibraryId, 1, 0)
    }

    fun insertCompleted(book: Book) {
        database.booksModelQueries.insertItem(book.authorName.firstOrNull(), book.title, book.openLibraryId, 0, 1)
    }
}