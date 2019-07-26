package com.willowtreeapps.common

import com.willowtree.common.LibraryDatabase
import org.reduxkotlin.createStore
import org.reduxkotlin.applyMiddleware
import com.willowtreeapps.common.middleware.*
import com.willowtreeapps.common.middleware.NavigationMiddleware
import com.willowtreeapps.common.repo.*
import com.willowtreeapps.common.ui.LibraryProvider
import com.willowtreeapps.common.ui.LibraryView
import com.willowtreeapps.common.ui.PresenterFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.reduxkotlin.thunk
import kotlin.coroutines.CoroutineContext

class LibraryApp(navigator: Navigator,
                 networkContext: CoroutineContext,
                 private val uiContext: CoroutineContext,
                 libraryDatabase: LibraryDatabase): LibraryProvider {
    private val navigationMiddleware = NavigationMiddleware(navigator)
    private val localStorageRepo = BookDatabaseRepo(libraryDatabase)
    private val databaseMiddleware = DatabaseMiddleware(localStorageRepo)
    private val bookRepository: BookRepository by lazy { KtorOpenBookRepository(networkContext) }
    private val presenterFactory by lazy { PresenterFactory(this, uiContext) }
//    val fetchBooks = fetchBooksThunk(networkContext, bookRepository)
    override val networkThunks = NetworkThunks(networkContext, bookRepository)

    val store by lazy {
        createStore(reducer, AppState.INITIAL_STATE, applyMiddleware(createThunkMiddleware2(),
                databaseMiddleware.middleware,
                navigationMiddleware::dispatch,
                loggerMiddleware))
    }

    init {
        CoroutineScope(uiContext).launch {
            store.dispatch(Actions.LoadAllSettingsAction())
        }
    }

    fun dispatch(action: Any) {
        CoroutineScope(uiContext).launch {
            store.dispatch(action)
        }
    }

    val state: AppState
        get() = store.state as AppState

    fun <V: LibraryView>attachView(view: V) = presenterFactory.attachView(view)

    fun detachView(view: LibraryView) = presenterFactory.detachView(view)

}
