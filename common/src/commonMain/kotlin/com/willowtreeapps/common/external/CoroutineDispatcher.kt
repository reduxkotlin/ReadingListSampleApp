package com.willowtreeapps.common.external

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.reduxkotlin.middleware
import kotlin.coroutines.CoroutineContext

/*
 * Middleware that moves rest of the middleware/reducer chain to a coroutine using the given context.
 */
fun coroutineDispatcher(context: CoroutineContext) = middleware { store, next, action ->
    CoroutineScope(context).launch {
        next(action)
    }
}