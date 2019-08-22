package org.reduxkotlin.readinglist.common

import org.reduxkotlin.readinglist.common.external.coroutineDispatcher
import org.reduxkotlin.*
import org.reduxkotlin.readinglist.common.middleware.*
import org.reduxkotlin.readinglist.common.middleware.navigationMiddleware
import org.reduxkotlin.readinglist.common.repo.BookDatabaseRepo
import org.reduxkotlin.readinglist.common.repo.BookRepository
import org.reduxkotlin.readinglist.common.repo.KtorOpenBookRepository
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
                presenterEnhancer(uiContext),
                applyMiddleware(
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
        get() = store.state
}


