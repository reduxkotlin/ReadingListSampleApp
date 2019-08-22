package org.reduxkotlin.readinglist.common.middleware

import org.reduxkotlin.readinglist.common.AppState
import org.reduxkotlin.readinglist.common.util.Logger
import org.reduxkotlin.*

val loggerMiddleware = middleware<AppState> { store, next, action ->
    Logger.d("********************************************")
    Logger.d("* DISPATCHED action: $action")
    Logger.d("********************************************")
    next(action)
}
