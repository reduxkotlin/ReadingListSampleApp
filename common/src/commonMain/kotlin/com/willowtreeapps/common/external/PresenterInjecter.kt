package com.willowtreeapps.common.external

import com.willowtreeapps.common.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.reduxkotlin.*
import kotlin.coroutines.CoroutineContext

data class AttachView(val view: Any)
data class DetachView(val view: Any)
data class ClearView(val view: Any)

/*
 * All views implement this interface.  The PresenterFactory handles setting and removing references
 * to the dispatch() and a selectorBuilder.
 */
interface View {
////    var dispatch: Dispatcher
////    var selectorBuilder: SelectorSubscriberBuilder<S>?
}


interface PresenterProvider {
    fun presenter(): Presenter<View> = throw NotImplementedError("Must implement this method to provide a presenterBuilder for ${this::class}")
}

interface ViewWithProvider: View, PresenterProvider

enum class ViewLifecycle {
    ATTACHED,
    DETACHED
}

data class StoreSubscriberHolder(val lifecycleState: ViewLifecycle, val subscriber: StoreSubscriber)

lateinit var rootDispatch: Dispatcher
val presenterEnhancer: StoreEnhancer = { storeCreator: StoreCreator ->
    { reducer: Reducer, initialState: Any, en: Any? ->
        val store = storeCreator(reducer, initialState, en)
        rootDispatch = store.dispatch
        store
    }
}

/**
 * PresenterMiddleware that attaches presenters with views and calls subscribers (Presenters)
 * to update the view when state changes.
 * Each view must attach/detach itself as it becomes visible/not visible by dispatching AttachView or DetachView
 * Attaching sets the presenter to the view.
 * PresenterFactory subscribes to changes in state, and passes state to presenters.
 */
fun presenterMiddleware(uiContext: CoroutineContext): Middleware = { store ->

    val uiScope = CoroutineScope(uiContext)
    val subscribers = mutableMapOf<ViewWithProvider, StoreSubscriberHolder>()
    var subscription: StoreSubscription? = null
    val coroutineScope = CoroutineScope(uiContext)

    fun hasAttachedViews() = subscribers.isNotEmpty()

    fun onStateChange() {
        coroutineScope.launch {
            subscribers.forEach {
                if (it.value.lifecycleState == ViewLifecycle.ATTACHED) {
                    it.value.subscriber()
                }
            }
        }
    }

    fun attachView(view: ViewWithProvider) {
        Logger.d("AttachView: $view", Logger.Category.LIFECYCLE)
//        view.dispatch = store.dispatch
        //TODO is hanging onto subscription needed?
        if (subscription == null) {
            subscription = store.subscribe(::onStateChange)
        }
        if (subscribers.containsKey(view) && subscribers[view]!!.lifecycleState == ViewLifecycle.DETACHED) {
            //view is reattached and does not need updating unless state has changed
            val subscriber = subscribers[view]!!.subscriber
            subscribers[view] = StoreSubscriberHolder(ViewLifecycle.ATTACHED, subscriber)
            subscriber()
        } else {
            val subscriber = view.presenter()(view, uiScope)(store)
            //call subscriber to trigger initial view update
            subscriber()
            subscribers[view] = StoreSubscriberHolder(ViewLifecycle.ATTACHED, subscriber)
        }
    }

    fun detachView(view: ViewWithProvider) {
        Logger.d("DetachView: $view", Logger.Category.LIFECYCLE)
        subscribers[view] = StoreSubscriberHolder(ViewLifecycle.DETACHED, subscribers[view]!!.subscriber)
    }

    fun clearView(view: ViewWithProvider) {
        Logger.d("ClearView: $view", Logger.Category.LIFECYCLE)
        subscribers.remove(view)

        if (!hasAttachedViews()) {
            subscription?.invoke()
            subscription = null
        }
    }

    { next: Dispatcher ->
        { action: Any ->
            when (action) {
                is AttachView -> attachView(action.view as ViewWithProvider)

                is DetachView -> detachView(action.view as ViewWithProvider)

                is ClearView -> clearView(action.view as ViewWithProvider)

                else -> next(action)
            }
        }
    }
}


/**
 * @param View a view interface that will be passed to the presenter
 * @param CoroutineScope scope on which the reselect action will be executed.  Typically a UI scope.
 */
typealias Presenter<View> = (View, CoroutineScope) -> (Store) -> StoreSubscriber

typealias PresenterBuilder<State, View> = ((View.() -> ((SelectorSubscriberBuilder<State>.() -> Unit))))

typealias PresenterBuilderWithViewArg<State, View> = ((View) -> (((SelectorSubscriberBuilder<State>.() -> Unit))))

/**
 * A convenience function for create a typed presenter builder for your App.
 *
 * usage:
 *        fun <V : LibraryView> presenter(actions: PresenterBuilder<AppState, V>): Presenter<View<AppState>> {
 *             return createGenericPresenter(actions) as Presenter<View<AppState>>
 *        }
 *
 *        val myPresenter = presenter<MyView> {{
 *            select { state.title } then { updateTitle(state.title) }
 *        }}
 *
 * @param actions - a PresenterBuilder describing actions to be taken on state changes.
 * @return a Presenter function
 *
 */
fun <State : Any, V : View> createGenericPresenter(actions: PresenterBuilder<State, V>): Presenter<V> {
    return { view: V, coroutineScope ->
        { store: Store ->
            val actions2 = actions(view)
            val sel = selectorSubscriberFn(store, view, actions2)
            sel
        }
    }
}

/**
 * Helper function that creates a DSL for subscribing to changes in specific state fields and actions to take.
 * Inside the lambda there is access to the current state through the var `state`
 *
 * ex:
 *      val sel = selectorSubscriberFn {
 *          withSingleField({it.foo}, { actionWhenFooChanges() }
 *
 *          withAnyChange {
 *              //called whenever any change happens to state
 *              view.setMessage(state.barMsg) //state is current state
 *          }
 *      }
 */
val selectorSubscriberMap: MutableMap<Any, SelectorSubscriberBuilder<*>> = mutableMapOf()

fun <State : Any, V : Any> selectorSubscriberFn(store: Store, view: V, selectorSubscriberBuilderInit: SelectorSubscriberBuilder<State>.() -> Unit): StoreSubscriber {
    val subscriberBuilder: SelectorSubscriberBuilder<State> = SelectorSubscriberBuilder(store)
    subscriberBuilder.selectorSubscriberBuilderInit()
    selectorSubscriberMap[view] = subscriberBuilder
    /*
    view.selectorBuilder = SelectorSubscriberBuilder(store)
    view.selectorBuilder!!.selectorSubscriberBuilderInit()
     */
    return {
        selectorSubscriberMap[view]!!.selectorList.forEach { entry ->
            (entry.key as Selector<State, *>).onChangeIn(store.getState() as State) { entry.value(store.getState()) }
        }
        selectorSubscriberMap[view]!!.withAnyChangeFun?.invoke()
//        view.selectorBuilder!!.withAnyChangeFun?.invoke()
    }
}
