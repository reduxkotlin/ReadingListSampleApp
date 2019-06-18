package com.willowtreeapps.common.middleware

import com.willowtreeapps.common.Logger
import org.reduxkotlin.*

fun logMiddleware(store: Store) = { next: Dispatcher ->
    { action: Any ->
        next(action)
    }
}


val loggerMiddleware3 = middleware { store, next, action ->
    Logger.d("DISPATCH action: ${action::class.simpleName}: $action")
    next(action)
}
