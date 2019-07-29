package com.willowtreeapps.common.middleware

import com.willowtreeapps.common.Actions
import com.willowtreeapps.common.Logger
import org.reduxkotlin.*

val loggerMiddleware = middleware { store, next, action ->
    Logger.d("********************************************")
    Logger.d("* DISPATCHED action: $action")
    Logger.d("********************************************")
    next(action)
}

val splitterMiddleware = middleware { store, next, action ->
    val dispatch = store.dispatch
    when(action) {
        is Actions.NextBook -> dispatch(Actions.NextBook())
    }
    next(action)
}
