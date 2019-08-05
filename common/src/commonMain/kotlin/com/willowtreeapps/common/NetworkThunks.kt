package com.willowtreeapps.common

import com.willowtreeapps.common.repo.*
import kotlinx.coroutines.*
import org.reduxkotlin.*
import kotlin.coroutines.CoroutineContext

/**
 * Thunks are functions that are executed by the "ThunkMiddleware".  They are asynchronous and dispatch
 * actions.  This allows dispatching a loading, success, and failure state.
 */
class NetworkThunks(private val networkContext: CoroutineContext,
                    private val repo: BookRepository) : CoroutineScope {
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = networkContext + job

    fun fetchBooksThunk2(query: String) = createThunk { dispatch, getState, extraArgument ->
        Logger.d("Fetching Books and Feed")
        launch {
            dispatch(Actions.FetchingItemsStartedAction())
            val result = repo.search(query)
            if (result.isSuccessful) {
                dispatch(Actions.FetchingItemsSuccessAction(result.response!!))
            } else {
                dispatch(Actions.FetchingItemsFailedAction(result.message!!))
            }
        }
    }
}
