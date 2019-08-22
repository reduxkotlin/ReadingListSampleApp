package org.reduxkotlin.readinglist.common.middleware

import org.reduxkotlin.readinglist.common.Actions
import org.reduxkotlin.readinglist.common.AppState
import org.reduxkotlin.readinglist.common.repo.BookDatabaseRepo
import org.reduxkotlin.middleware

fun databaseMiddleware(bookDatabaseRepo: BookDatabaseRepo) = middleware<AppState> { store, next, action ->
    when (action) {
        is Actions.AddCurrentToCompleted ->
            bookDatabaseRepo.insertCompleted((store.state as AppState).selectedBook!!)
        is Actions.AddCurrentToRead ->
            bookDatabaseRepo.insertToRead((store.state as AppState).selectedBook!!)
        is Actions.LoadToRead ->
            next(Actions.ToReadLoaded(bookDatabaseRepo.loadToRead()))
        is Actions.LoadCompleted ->
            next(Actions.CompletedLoaded(bookDatabaseRepo.loadCompleted()))
    }
    next(action)
}
