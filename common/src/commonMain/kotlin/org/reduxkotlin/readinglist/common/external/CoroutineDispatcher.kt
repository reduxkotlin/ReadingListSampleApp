package org.reduxkotlin.readinglist.common.external

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.reduxkotlin.Middleware
import org.reduxkotlin.middleware
import kotlin.coroutines.CoroutineContext

/*
 * Middleware that moves rest of the middleware/reducer chain to a coroutine using the given context.
 */
fun <State> coroutineDispatcher(context: CoroutineContext): Middleware<State> {
    val scope = CoroutineScope(context)
    return middleware { store, next, action ->
        scope.launch {
            next(action)
        }
    }
}