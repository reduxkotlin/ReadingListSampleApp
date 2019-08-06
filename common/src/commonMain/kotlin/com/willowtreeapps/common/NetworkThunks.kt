package com.willowtreeapps.common

import com.willowtreeapps.common.repo.*
import kotlinx.coroutines.*
import org.reduxkotlin.Dispatcher
import org.reduxkotlin.GetState
import org.reduxkotlin.createThunk
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

    fun fetchBooksThunk(query: String) = thunk { dispatch, _, _ ->
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

/**
 * Convenience function so state type does is not needed every time a thunk is created.
 */
fun thunk(thunkLambda: (dispatch: Dispatcher, getState: GetState<AppState>, extraArgument: Any?) -> Any) =
        createThunk(thunkLambda)
