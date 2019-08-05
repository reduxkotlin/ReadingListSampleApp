package com.willowtreeapps.common

import com.willowtree.common.LibraryDatabase
import com.willowtreeapps.common.external.ViewWithProvider
import com.willowtreeapps.common.external.coroutineDispatcher
import com.willowtreeapps.common.external.presenterEnhancer
import com.willowtreeapps.common.external.presenterMiddleware
import com.willowtreeapps.common.middleware.*
import com.willowtreeapps.common.repo.*
import org.reduxkotlin.*
import kotlin.coroutines.CoroutineContext

class LibraryApp(navigator: Navigator,
                 networkContext: CoroutineContext,
                 private val uiContext: CoroutineContext,
                 libraryDatabase: LibraryDatabase) : LibraryProvider {
    private val bookRepository: BookRepository by lazy { KtorOpenBookRepository(networkContext) }
    //    private val bookRepository: BookRepository by lazy { MockRepositoryFactory().delayedLoading(3000)}
    override val networkThunks = NetworkThunks(networkContext, bookRepository)

    val store by lazy {
        createStore(combineReducers(reducer, navigationReducer), AppState.INITIAL_STATE, compose(listOf(
                presenterEnhancer,
                applyMiddleware(
                        presenterMiddleware<AppState, ViewWithProvider>(uiContext),
                        coroutineDispatcher(uiContext),
                        loggerMiddleware,
                        createThunkMiddleware(),
                        uiActionMiddleware(networkThunks),
                        databaseMiddleware(BookDatabaseRepo(libraryDatabase)),
                        navigationMiddleware(navigator)
                ))))
    }

    init {
        //do any initialization here
        store.dispatch(NavigationActions.GotoScreen(state.currentScreen))
    }

    val state: AppState
        get() = store.state as AppState
}


