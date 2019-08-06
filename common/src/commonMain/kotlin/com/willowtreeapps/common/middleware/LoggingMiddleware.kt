package com.willowtreeapps.common.middleware

import com.willowtreeapps.common.AppState
import com.willowtreeapps.common.Logger
import org.reduxkotlin.*

val loggerMiddleware = middleware<AppState> { store, next, action ->
    Logger.d("********************************************")
    Logger.d("* DISPATCHED action: $action")
    Logger.d("********************************************")
    next(action)
}
