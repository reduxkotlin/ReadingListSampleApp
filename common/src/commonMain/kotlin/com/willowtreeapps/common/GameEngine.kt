package com.willowtreeapps.common

import org.reduxkotlin.createStore
import org.reduxkotlin.applyMiddleware
import com.willowtreeapps.common.middleware.*
import com.willowtreeapps.common.middleware.NavigationMiddleware
import com.willowtreeapps.common.middleware.SettingsMiddleware
import com.willowtreeapps.common.repo.BookRepository
import com.willowtreeapps.common.repo.KtorOpenBookRepository
import com.willowtreeapps.common.repo.LocalStorageSettingsRepository
import com.willowtreeapps.common.repo.userSettings
import com.willowtreeapps.common.ui.Presenter
import com.willowtreeapps.common.ui.PresenterFactory
import com.willowtreeapps.common.ui.View
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.reduxkotlin.thunk
import kotlin.coroutines.CoroutineContext

class GameEngine(navigator: Navigator,
                 application: Any = Any(),
                 networkContext: CoroutineContext,
                 val uiContext: CoroutineContext) {
    private val navigationMiddleware = NavigationMiddleware(navigator)
    private val throttleMiddleware = ThrottleMiddleware(uiContext)
    private val bookRepository: BookRepository by lazy { KtorOpenBookRepository(networkContext) }
    private val presenterFactory by lazy { PresenterFactory(this, bookRepository, networkContext, uiContext) }
    private val settingsMiddleware by lazy { SettingsMiddleware(LocalStorageSettingsRepository(userSettings(application)), networkContext) }

    val appStore by lazy {
        createStore(reducer, AppState.INITIAL_STATE, applyMiddleware(thunk,
                throttleMiddleware.throttleMiddleware,
                navigationMiddleware::dispatch,
                loggerMiddleware,
                settingsMiddleware::dispatch))
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

    fun <T : Presenter<*>> attachView(view: View<T>) = presenterFactory.attachView(view as View<Presenter<*>>)

    fun detachView(view: View<*>) = presenterFactory.detachView(view)
}
