package com.willowtreeapps.common.ui

import com.willowtreeapps.common.*
import com.willowtreeapps.common.SelectorSubscriberBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.reduxkotlin.*
import kotlin.coroutines.CoroutineContext
import kotlin.reflect.KClass

interface LibraryView: View<AppState>

fun testMiddleware(store: Store): (Dispatcher) -> Dispatcher {
    return { next: Dispatcher ->
        val presenterMap2 = mutableMapOf<KClass<out LibraryView>, Any>()

        val tmp = mutableMapOf("" to "")
        tmp[""] = ""
        { action: Any ->
            if (action is Actions.AttachView<*>) {

            }

        }
    }
}

val PresenterMiddleware: Middleware = { store ->

    { next ->
        { action ->

        }
    }
}

/**
 * PresenterFactory that creates presenters for all views in the application.
 * Each view must attach/detach itself as it becomes visible/not visible.
 * Attaching returns a presenter to the view.
 * PresenterFactory subscribes to changes in state, and passes state to presenters.
 */
class PresenterFactory(private val libraryApp: LibraryApp,
                       uiContext: CoroutineContext) : CoroutineScope {
    var subscription: StoreSubscription? = null


    private val subscribers = mutableMapOf<LibraryView, StoreSubscriber>()

    override val coroutineContext: CoroutineContext = uiContext + Job()

    fun <T : LibraryView> attachView(view: T) {
        Logger.d("AttachView: $view", Logger.Category.LIFECYCLE)
        view.dispatch = libraryApp.store.dispatch
        if (subscription == null) {
            subscription = libraryApp.store.subscribe(this::onStateChange)
        }
        val tmp = view.viewUpdater()//getPresenterBuilder(viewClass)//(view)(libraryApp.appStore)
        val subscriber = tmp(view)(libraryApp.store)
        //call subscriber to trigger initial view update
        subscriber()
        subscribers[view] = subscriber
    }

    fun detachView(view: LibraryView) {
        Logger.d("DetachView: $view", Logger.Category.LIFECYCLE)
        subscribers.remove(view)

        if (hasAttachedViews()) {
            subscription?.invoke()
            subscription = null
        }
    }

    private fun hasAttachedViews() = subscribers.isNotEmpty()

    private fun onStateChange() {
        launch {
            subscribers.forEach { it.value() }
        }
    }

}

interface LibraryProvider {
    val networkThunks: NetworkThunks
}

/*
 * All views implement this interface.  The PresenterFactory handles settings and removing references
 * to the dispatch() and a selectorBuilder.
 */
interface View<S : Any> {
    var dispatch: Dispatcher
    var selectorBuilder: SelectorSubscriberBuilder<S>
    fun viewUpdater(): ViewUpdater<View<AppState>> = throw NotImplementedError("Must implement this method to provide a presenterBuilder for ${this::class}")

}

//TODO handle config changes on android where view has been destroyed and must be recreated.  Probably
//can be treated as if a new state - wipe out the selector cache and treat as new view?
