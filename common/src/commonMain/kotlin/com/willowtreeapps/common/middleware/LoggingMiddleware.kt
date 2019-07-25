package com.willowtreeapps.common.middleware

import com.willowtreeapps.common.Logger
import org.reduxkotlin.*

val loggerMiddleware = middleware { store, next, action ->
    Logger.d("DISPATCH action: $action")
    next(action)
}
