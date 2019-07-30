package com.willowtreeapps.common

import com.willowtree.common.LibraryDatabase
import com.willowtreeapps.common.external.coroutineDispatcher
import com.willowtreeapps.common.external.presenterMiddleware
import org.reduxkotlin.createStore
import org.reduxkotlin.applyMiddleware
import com.willowtreeapps.common.middleware.*
import com.willowtreeapps.common.repo.*
import com.willowtreeapps.common.ui.*
import org.reduxkotlin.combineReducers
import kotlin.coroutines.CoroutineContext

class LibraryApp(navigator: Navigator,
                 networkContext: CoroutineContext,
                 private val uiContext: CoroutineContext,
                 libraryDatabase: LibraryDatabase) : LibraryProvider {
    private val bookRepository: BookRepository by lazy { KtorOpenBookRepository(networkContext) }
    override val networkThunks = NetworkThunks(networkContext, bookRepository)

    val store by lazy {
        createStore(combineReducers(reducer, navigationReducer), AppState.INITIAL_STATE, applyMiddleware(
                presenterMiddleware<AppState, LibraryView>(uiContext),
                coroutineDispatcher(uiContext),
                createThunkMiddleware2(),
                uiActionMiddleware(networkThunks),
                databaseMiddleware(BookDatabaseRepo(libraryDatabase)),
                navigationMiddleware(navigator),
                loggerMiddleware))
    }

    init {
        //do any initialization here
        store.dispatch(NavigationActions.GotoScreen(state.currentScreen))
    }

    val state: AppState
        get() = store.state as AppState
}


