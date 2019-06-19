package com.willowtreeapps.common

import com.squareup.sqldelight.db.SqlDriver
import org.reduxkotlin.createStore
import org.reduxkotlin.applyMiddleware
import com.willowtreeapps.common.middleware.*
import com.willowtreeapps.common.middleware.NavigationMiddleware
import com.willowtreeapps.common.repo.*
import com.willowtreeapps.common.ui.Presenter
import com.willowtreeapps.common.ui.PresenterFactory
import com.willowtreeapps.common.ui.View
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.reduxkotlin.thunk
import kotlin.coroutines.CoroutineContext

class LibraryApp(navigator: Navigator,
                 networkContext: CoroutineContext,
                 val uiContext: CoroutineContext,
                 sqlDriver: SqlDriver) {
    private val navigationMiddleware = NavigationMiddleware(navigator)
    private val localStorageRepo = BookDatabaseRepo(createDatabase(sqlDriver))
    private val databaseMiddleware = DatabaseMiddleware(localStorageRepo)
    private val bookRepository: BookRepository by lazy { KtorOpenBookRepository(networkContext) }
    private val presenterFactory by lazy { PresenterFactory(this, bookRepository, networkContext, uiContext) }

    val appStore by lazy {
        createStore(reducer, AppState.INITIAL_STATE, applyMiddleware(thunk,
                databaseMiddleware.middleware,
                navigationMiddleware::dispatch,
                loggerMiddleware))
    }

    init {
        CoroutineScope(uiContext).launch {
            appStore.dispatch(Actions.LoadAllSettingsAction())
        }
    }

    fun dispatch(action: Any) {
        CoroutineScope(uiContext).launch {
            appStore.dispatch(action)
        }
    }

    val state: AppState
        get() = appStore.state as AppState

    fun <T : Presenter<*>?> attachView(view: View<T>) = presenterFactory.attachView(view as View<Presenter<*>>)

    fun detachView(view: View<*>) = presenterFactory.detachView(view)
}
