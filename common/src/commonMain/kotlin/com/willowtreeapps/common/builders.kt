package com.willowtreeapps.common

import com.willowtreeapps.common.ui.ViewUpdater
import org.reduxkotlin.Store
import org.reduxkotlin.StoreSubscriber

class SelectorSubscriberBuilder<S : Any, View>(val store: Store) {
    val state: S
        get() = store.getState() as S

    var withAnyChangeFun: (() -> Unit)? = null

    fun withAnyChange(f: () -> Unit) {
        withAnyChangeFun = f
    }

    val selectorList = mutableMapOf<Selector<S, Any>, (Any) -> Unit>()
    val updaterList = mutableMapOf<Selector<S, Any>, Any>()

    fun withSingleField(selector: (S) -> Any, action: (Any) -> Unit) {
        val selBuilder = SelectorBuilder<S>()
        val sel = selBuilder.withSingleField(selector)
        selectorList[sel] = action
    }

    fun withSingleField2(selector: (S) -> Any, action: View.()-> Unit) {
        val selBuilder = SelectorBuilder<S>()
        val sel = selBuilder.withSingleField(selector)
        updaterList[sel] = action
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
