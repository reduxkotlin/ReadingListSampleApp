package com.willowtreeapps.common

import com.willowtree.common.LibraryDatabase
import com.willowtreeapps.common.external.coroutineDispatcher
import com.willowtreeapps.common.external.presenterMiddleware
import org.reduxkotlin.createStore
import org.reduxkotlin.applyMiddleware
import com.willowtreeapps.common.middleware.*
import com.willowtreeapps.common.middleware.NavigationMiddleware
import com.willowtreeapps.common.repo.*
import com.willowtreeapps.common.ui.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.reduxkotlin.combineReducers
import kotlin.coroutines.CoroutineContext

class LibraryApp(navigator: Navigator,
                 networkContext: CoroutineContext,
                 private val uiContext: CoroutineContext,
                 libraryDatabase: LibraryDatabase) : LibraryProvider {
    private val navigationMiddleware = NavigationMiddleware(navigator)
    private val localStorageRepo = BookDatabaseRepo(libraryDatabase)
    private val databaseMiddleware = DatabaseMiddleware(localStorageRepo)
    private val bookRepository: BookRepository by lazy { KtorOpenBookRepository(networkContext) }
    override val networkThunks = NetworkThunks(networkContext, bookRepository)

    val store by lazy {
        createStore(combineReducers(reducer, navigationReducer), AppState.INITIAL_STATE, applyMiddleware(
                presenterMiddleware<AppState, LibraryView>(uiContext),
                coroutineDispatcher(uiContext),
                createThunkMiddleware2(),
                uiActionMiddleware(networkThunks),
                databaseMiddleware.middleware,
                navigationMiddleware::dispatch,
                loggerMiddleware))
    }

    init {
        CoroutineScope(uiContext).launch {
            //do any initialization here
            store.dispatch(NavigationActions.GotoScreen(state.currentScreen))
        }
    }

    val state: AppState
        get() = store.state as AppState
}


