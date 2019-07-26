package com.willowtreeapps.common

import com.willowtreeapps.common.ui.View
import org.reduxkotlin.Store
import org.reduxkotlin.StoreSubscriber
import kotlin.reflect.KProperty1

typealias Presenter<View> = (View) -> (Store) -> StoreSubscriber
/**
 *
 */
typealias PresenterBuilder<State, View> = ((View.() -> ((SelectorSubscriberBuilder<State>.() -> Unit))))

class SelectorSubscriberBuilder<S : Any>(val store: Store, val view: View<S>) {
    //available to lambda with receiver in DSL
    val state: S
        get() = store.getState() as S

    var withAnyChangeFun: (() -> Unit)? = null

    fun withAnyChange(f: () -> Unit) {
        withAnyChangeFun = f
    }

    val selectorList = mutableMapOf<Selector<S, Any>, (Any) -> Unit>()

    fun withSingleField(selector: (S) -> Any, action: (Any) -> Unit) {
        val selBuilder = SelectorBuilder<S>()
        val sel = selBuilder.withSingleField(selector)
        selectorList[sel] = action
    }

    infix fun SelectorSubscriberBuilder<S>.on(selector: (S) -> Any): AbstractSelector<S, Any> {
        val selBuilder = SelectorBuilder<S>()
        val sel = selBuilder.withSingleField(selector)
        return sel
    }

    operator fun (()-> Any).unaryPlus():AbstractSelector<S, Any>  {
        val that = this
        val selBuilder = SelectorBuilder<S>()
        val sel = selBuilder.withSingleField{ that() }
        return sel
    }

    infix operator fun AbstractSelector<S, Any>.plus(action: (Any) -> Unit) {
        selectorList[this] = action
    }

    infix operator fun AbstractSelector<S, Any>.invoke(action: (Any) -> Unit) {
        selectorList[this] = action
    }
}

/**
 * Helper function that creates a DSL for subscribing to changes in specific state fields and actions to take.
 * Inside the lambda there is access to the current state through the var `state`
 *
 * ex:
 *      SelectorSubscriberFn {
 *          withSingleField({it.foo}, { actionWhenFooChanges() }
 *
 *          withAnyChange {
 *              //called whenever any change happens to state
 *              view.setMessage(state.barMsg) //state is current state
 *          }
 *      }
 */
fun <S : Any, V: View<S>> SelectorSubscriberFn(store: Store, view: V, selectorSubscriberBuilderInit: SelectorSubscriberBuilder<S>.() -> Unit): StoreSubscriber {



    view.selectorBuilder = SelectorSubscriberBuilder<S>(store, view)
    view.selectorBuilder!!.selectorSubscriberBuilderInit()
    return {
        view.selectorBuilder!!.selectorList.forEach { entry ->
            entry.key.onChangeIn(store.getState() as S) { entry.value(store.getState()) }
        }
        view.selectorBuilder!!.withAnyChangeFun?.invoke()
    }
}


typealias SelectorSubscriber<State, View> = SelectorSubscriberBuilder<State>.() -> View.() -> Unit

//typealias SelectorSubscriberB<State, View> = View.() -> (SelectorSubscriberBuilder<State, View>.() -> Unit)
//typealias SelectorSubscriberC<State, View> = View.() -> (Store) -> (SelectorSubscriberBuilder<State, View>.() -> Unit)
//typealias SelectorSubscriberD<State, View> = View.() -> (Store) -> StoreSubscriber
//
typealias MySelectorSubscriber<View> = SelectorSubscriber<AppState, View>

//experimental selector using KProps
operator fun KProperty1<AppState, Boolean>.get(pos: Int) {

}

/*
val searchPresenterSubscriber: MySelectorSubscriber<SearchView> =
        {
            {
                plus { AppState::isLoadingItems[0] } + { showLoading() }
                plus { it.isLoadingItems } + { showLoading() }
                withSingleField({ it.isLoadingItems }) { showLoading() }
            }
        }

val searchPresenterSubscriberB: SelectorSubscriberB<AppState, SearchView> = {
    {
        plus { AppState::isLoadingItems[0] } + { showLoading() }
        plus { it.isLoadingItems } + { showLoading() }
    }
}


val searchPresenterSubscriberC: SelectorSubscriberC<AppState, SearchView> = {
    { store ->
        {
            plus { AppState::isLoadingItems[0] } + { showLoading() }
            plus { it.isLoadingItems } + { showLoading() }
        }
    }
}


fun <State : Any > selectFun(store: Store, actions: (SelectorSubscriberBuilder<State, View>.() -> Unit)): StoreSubscriber {
    val sel = SelectorSubscriberFn<State, View>(store, actions)
    return sel
}
 */

/**
 * @param actions - a PresenterBuilder describing actions to be taken on state changes.
 *              usage:
 *                 val myPresenter = createGenericPresenter {{
 *                      //TODO
 *                 }}
 * @return a Presenter function
 */
fun <State : Any, V: View<State>> createGenericPresenter(actions: PresenterBuilder<State, V>): Presenter<V> {
    return { view: V ->
        { store: Store ->
            val actions2 = actions(view)//(store)
            val sel = SelectorSubscriberFn(store, view, actions2)
            sel
        }
    }
}
/*

val searchPresenterSubscriberD: SelectorSubscriberD<AppState, SearchView> = {
    { store ->
        SelectorSubscriberFn<AppState, SearchView>(store) {
            plus { AppState::isLoadingItems[0] } + { showLoading() }
            plus { it.isLoadingItems } + { showLoading() }
        }//(store)
    }
}
val searchPresenterSubscriberD2: SelectorSubscriberD<AppState, SearchView> = {
    {
        selectFun<AppState, SearchView>(it) {
            plus { AppState::isLoadingItems[0] } + { showLoading() }
            plus { it.isLoadingItems } + { showLoading() }
        }//(store)
    }
}
val searchPresenterSubscriberD3 = createGenericPresenter<AppState, SearchView> {
    {
        plus { it.isLoadingItems } + { showLoading() }
    }
}


fun test(view: SearchView) {
    val store = createStore()
    searchPresenterSubscriber(SelectorSubscriberBuilder(store))(view)
    searchPresenterSubscriberB(view)(SelectorSubscriberBuilder(store))
    val subscriber = searchPresenterSubscriberC(view)(store)
    val subscriberD = searchPresenterSubscriberD(view)(store)
    val subscriberD2 = searchPresenterSubscriberD2(view)(store)
    val subscriberD3 = searchPresenterSubscriberD3(view)(store)
}
 */
