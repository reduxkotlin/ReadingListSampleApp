package com.willowtreeapps.common

import com.willowtreeapps.common.repo.*
import kotlinx.coroutines.*
import org.reduxkotlin.Dispatcher
import org.reduxkotlin.GetState
import org.reduxkotlin.Thunk
import org.reduxkotlin.ThunkMiddleware
import kotlin.coroutines.CoroutineContext

/*
fun fetchBooksThunk(networkContext: CoroutineContext, repo: BookRepository): ((String) -> Thunk) = { s: String ->
    { dispatch, getState, extraArgument ->
        {
            Logger.d("Fetching Books and Feed")
        launch {
            dispatch(Actions.FetchingItemsStartedAction())
            val result = repo.search(query)
            if (result.isSuccessful) {
                Logger.d("Success")
                dispatch(Actions.FetchingItemsSuccessAction(result.response!!))
            } else {
                Logger.d("Failure")
                dispatch(Actions.FetchingItemsFailedAction(result.message!!))
            }
        }
        }
    }


}

 */

/**
 * Thunks are functions that are executed by the "ThunkMiddleware".  They are asynchronous and dispatch
 * actions.  This allows dispatching a loading, success, and failure state.
 */
class NetworkThunks(private val networkContext: CoroutineContext,
                    private val repo: BookRepository) : CoroutineScope {
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = networkContext + job


    fun fetchBooks(query: String): Thunk = { dispatch, getState, extraArgument ->
        Logger.d("Fetching Books and Feed")
        launch {
            Logger.d("INSIDE FetchBooks 1")
            dispatch(Actions.FetchingItemsStartedAction())
            val result = repo.search(query)
            if (result.isSuccessful) {
                Logger.d("Success")
                dispatch(Actions.FetchingItemsSuccessAction(result.response!!))
            } else {
                Logger.d("Failure")
                dispatch(Actions.FetchingItemsFailedAction(result.message!!))
            }
        }
    }

    fun fetchBooksThunk2(query: String): Thunk2 = object : Thunk2 {
        override fun dispatch(dispatch: Dispatcher, getState: GetState, extraArgument: Any?) {
            Logger.d("Fetching Books and Feed")
            launch {
                Logger.d("INSIDE FetchBooks 1")
                dispatch(Actions.FetchingItemsStartedAction())
                val result = repo.search(query)
                if (result.isSuccessful) {
                    Logger.d("Success")
                    dispatch(Actions.FetchingItemsSuccessAction(result.response!!))
                } else {
                    Logger.d("Failure")
                    dispatch(Actions.FetchingItemsFailedAction(result.message!!))
                }
            }
        }
    }
}

interface Thunk2 {
    fun dispatch(dispatch: Dispatcher, getState: GetState, extraArgument: Any?)
}

fun createThunkMiddleware2(extraArgument: Any? = null): ThunkMiddleware =
        { store ->
            { next: Dispatcher ->
                { action: Any ->
                    Logger.d("INSIDE ThunkMiddleware 1: $action")
                    if (action is Thunk2) {
                        Logger.d("INSIDE ThunkMiddleware 2: ")
                        try {
                            Logger.d("dispatch thunk 1: ${store.dispatch}")
                            Logger.d("dispatch thunk 2: ${store.getState}")
                            Logger.d("dispatch thunk 3: ${extraArgument}")
                            Logger.d("dispatch thunk 4: $action")
                            action.dispatch(store.dispatch, store.getState, extraArgument)
                        } catch (e: Exception) {
                            Logger.d("INSIDE ThunkMiddleware 3: ${e.message}")
                            throw IllegalArgumentException()
                            Logger.d("Dispatching functions must use type Thunk: " + e.message)
                        }
                    } else {
                        Logger.d("INSIDE ThunkMiddleware 4: ")
                        next(action)
                    }
                }
            }
        }
