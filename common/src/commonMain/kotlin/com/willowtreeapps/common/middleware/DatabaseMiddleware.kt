package com.willowtreeapps.common.middleware

import com.willowtreeapps.common.Actions
import com.willowtreeapps.common.repo.BookDatabaseRepo
import org.reduxkotlin.middleware

class DatabaseMiddleware(val bookDatabaseRepo: BookDatabaseRepo) {
    val middleware = middleware { store, next, action ->
        when (action) {
            is Actions.AddToCompleted ->
                bookDatabaseRepo.insertCompleted(action.book)
            is Actions.AddToRead ->
                bookDatabaseRepo.insertToRead(action.book)
            is Actions.LoadToRead ->
                next(Actions.ToReadLoaded(bookDatabaseRepo.loadToRead()))
            is Actions.LoadCompleted ->
                next(Actions.CompletedLoaded(bookDatabaseRepo.loadCompleted()))
        }
        next(action)
    }
}