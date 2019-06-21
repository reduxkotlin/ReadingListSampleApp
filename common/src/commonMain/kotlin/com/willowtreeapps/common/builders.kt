package com.willowtreeapps.common

import com.willowtreeapps.common.ui.SearchView
import com.willowtreeapps.common.ui.View
import com.willowtreeapps.common.ui.ViewUpdater
import com.willowtreeapps.common.ui.ViewUpdaterBuilder
import org.reduxkotlin.Store
import org.reduxkotlin.StoreSubscriber
import org.reduxkotlin.compose
import org.reduxkotlin.createStore
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1

class SelectorSubscriberBuilder<S : Any, View>(val store: Store) {
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

    infix operator fun SelectorSubscriberBuilder<S, View>.plus(selector: (S) -> Any): AbstractSelector<S, Any> {
        val selBuilder = SelectorBuilder<S>()
        val sel = selBuilder.withSingleField(selector)
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
fun <S : Any, View> SelectorSubscriberFn(store: Store, selectorSubscriberBuilderInit: SelectorSubscriberBuilder<S, View>.() -> Unit): StoreSubscriber {

    val subBuilder = SelectorSubscriberBuilder<S, View>(store)
    subBuilder.selectorSubscriberBuilderInit()
    return {
        subBuilder.selectorList.forEach { entry ->
            entry.key.onChangeIn(store.getState() as S) { entry.value(store.getState()) }
        }
        subBuilder.withAnyChangeFun?.invoke()
    }
}

//Class that handles state management of selectors globally.  All Subscribed selectors will share
//a selector map and multiple selectors matching selectors will be updated.  i.e.
//   val selectFoo: SelectorSubscriber = {{ select{it.loading}+{}
//   val selectBar: SelectorSubscriber = {{ select{it.loading}+{}
// This class will notify both
class SharedSelector<S : Any, View> {
    private val selectorList = mutableMapOf<Selector<S, Any>, (Any) -> Unit>()

    fun withSingleField(selector: (S) -> Any, action: (Any) -> Unit) {
        val selBuilder = SelectorBuilder<S>()
        val sel = selBuilder.withSingleField(selector)
        selectorList[sel] = action
    }

    infix operator fun SelectorSubscriberBuilder<S, View>.plus(selector: (S) -> Any): AbstractSelector<S, Any> {
        val selBuilder = SelectorBuilder<S>()
        val sel = selBuilder.withSingleField(selector)
        return sel
    }

    infix operator fun AbstractSelector<S, Any>.plus(action: (Any) -> Unit) {
        selectorList[this] = action
    }

    infix operator fun AbstractSelector<S, Any>.invoke(action: (Any) -> Unit) {
        selectorList[this] = action
    }
}

typealias SelectorSubscriber<State, View> = SelectorSubscriberBuilder<State, View>.() -> View.() -> Unit

typealias SelectorSubscriberB<State, View> = View.() -> (SelectorSubscriberBuilder<State, View>.() -> Unit)
typealias SelectorSubscriberC<State, View> = View.() -> (Store) -> (SelectorSubscriberBuilder<State, View>.() -> Unit)
typealias SelectorSubscriberD<State, View> = View.() -> (Store) -> StoreSubscriber

typealias MySelectorSubscriber<View> = SelectorSubscriber<AppState, View>

//experimental selector using KProps
operator fun KProperty1<AppState, Boolean>.get(pos: Int) {

}

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

fun <State : Any, View> selectFun(store: Store, actions: (SelectorSubscriberBuilder<State, View>.() -> Unit)): StoreSubscriber {
    val sel = SelectorSubscriberFn<State, View>(store, actions)
    return sel
}

fun <State : Any, View> viewUpdater(actions: ViewUpdaterBuilder<State, View>): ViewUpdater<View> {
    return { view: View ->
        { store: Store ->
            val actions2 = actions(view)//(store)
            val sel = SelectorSubscriberFn(store, actions2)
            sel
        }
    }
}

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
val searchPresenterSubscriberD3 = viewUpdater<AppState, SearchView> {
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
