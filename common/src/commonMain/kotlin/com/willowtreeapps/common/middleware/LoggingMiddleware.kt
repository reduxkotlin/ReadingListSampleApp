package com.willowtreeapps.common.middleware

import com.willowtreeapps.common.Actions
import com.willowtreeapps.common.Logger
import com.willowtreeapps.common.util.TimeUtil
import kotlinx.coroutines.*
import org.reduxkotlin.*
import kotlin.coroutines.CoroutineContext
import kotlin.reflect.KClass

val loggerMiddleware = middleware { store, next, action ->
    Logger.d("DISPATCH action: ${action::class.simpleName}: $action")
    next(action)
}


class ThrottleMiddleware(backgroundContext: CoroutineContext): CoroutineScope {
    override val coroutineContext = backgroundContext + Job()

    private val map = mutableMapOf<KClass<*>, Pair<Job,Long>>()

    val throttleMiddleware = middleware { store, next, action ->
        if (action is Actions.ThrottledAction) {
            val elapsedTime = if (map.containsKey(action::class)) {
                TimeUtil.systemTimeMs() - map[action::class]!!.second
            } else {
                0
            }

            if (elapsedTime > action.waitTimeMs) {
                store.dispatch(action.thunk)
                map.remove(action::class)
            } else {
                val oldJob = map[action::class]?.first
                if (oldJob?.isActive == true) {
                    oldJob.cancel()
                }

                val job = launch {
                    withContext(this.coroutineContext) {
                        delay(action.waitTimeMs - elapsedTime)
                    }
                    store.dispatch(action)
                }
                map[action::class] = Pair(job, TimeUtil.systemTimeMs())
            }
            Unit
        } else {
            next(action)
        }

    }
}
