package com.willowtreeapps.common.middleware

import com.willowtreeapps.common.Actions
import com.willowtreeapps.common.AppState
import com.willowtreeapps.common.repo.BookDatabaseRepo
import org.reduxkotlin.middleware

fun databaseMiddleware(bookDatabaseRepo: BookDatabaseRepo) = middleware { store, next, action ->
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
