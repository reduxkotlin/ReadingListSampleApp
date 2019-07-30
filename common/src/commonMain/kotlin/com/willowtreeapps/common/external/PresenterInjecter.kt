package com.willowtreeapps.common.external

import com.willowtreeapps.common.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.reduxkotlin.Dispatcher
import org.reduxkotlin.Middleware
import org.reduxkotlin.StoreSubscriber
import org.reduxkotlin.StoreSubscription
import kotlin.coroutines.CoroutineContext

data class AttachView<S: Any>(val view: View<S>)
data class DetachView<S: Any>(val view: View<S>)

/*
 * All views implement this interface.  The PresenterFactory handles setting and removing references
 * to the dispatch() and a selectorBuilder.
 */
interface View<S : Any> {
    var dispatch: Dispatcher
    var selectorBuilder: SelectorSubscriberBuilder<S>?
}


interface PresenterProvider<S : Any> {
    fun presenter(): Presenter<View<S>> = throw NotImplementedError("Must implement this method to provide a presenterBuilder for ${this::class}")
}

interface ViewWithProvider<S : Any> : View<S>, PresenterProvider<S>

/**
 * PresenterMiddleware that attaches presenters with views and calls subscribers (Presenters)
 * to update the view when state changes.
 * Each view must attach/detach itself as it becomes visible/not visible by dispatching AttachView or DetachView
 * Attaching sets the presenter to the view.
 * PresenterFactory subscribes to changes in state, and passes state to presenters.
 */
//TODO handle config changes on android where view has been destroyed and must be recreated.  Probably
//can be treated as if a new state - wipe out the selector cache and treat as new view?
fun <S : Any, V : ViewWithProvider<S>> presenterMiddleware(uiContext: CoroutineContext): Middleware = { store ->


    val subscribers = mutableMapOf<V, StoreSubscriber>()
    var subscription: StoreSubscription? = null
    val coroutineContext: CoroutineContext = uiContext + Job()

    fun hasAttachedViews() = subscribers.isNotEmpty()

    fun detachView(view: ViewWithProvider<*>) {
        Logger.d("DetachView: $view", Logger.Category.LIFECYCLE)
        subscribers.remove(view)

        if (!hasAttachedViews()) {
            subscription?.invoke()
            subscription = null
        }
    }

    fun onStateChange() {
        CoroutineScope(coroutineContext).launch {
            subscribers.forEach {
                it.value()
            }
        }
    }

    fun attachView(view: V) {
        Logger.d("AttachView: $view", Logger.Category.LIFECYCLE)
        view.dispatch = store.dispatch
        if (subscription == null) {
            subscription = store.subscribe(::onStateChange)
        }
        val tmp = view.presenter()
        val subscriber = tmp(view)(store)
        //call subscriber to trigger initial view update
        subscriber()
        subscribers[view] = subscriber
    }

    { next: Dispatcher ->
        { action: Any ->
            when (action) {
                is AttachView<*> -> {
                    attachView(action.view as V)
                    Unit
                }
                is DetachView<*> -> {
                    detachView(action.view as V)
                    Unit
                }
                else -> next(action)
            }
        }
    }
}
