package com.willowtreeapps.common

import org.reduxkotlin.ActionTypes
import com.willowtreeapps.common.Actions.*
import com.willowtreeapps.common.repo.Book
import org.reduxkotlin.Reducer

/**
 * Reducers and functions used by reducers are in this file.  Functions must be pure functions without
 * side effects.
 */
val reducer = castingReducer { state: AppState, action ->
    when (action) {
        is ActionTypes.INIT -> {
            AppState.INITIAL_STATE
        }
        is FetchingItemsStartedAction -> state.copy(isLoadingItems = true)
        is FetchingItemsSuccessAction -> {
            state.copy(isLoadingItems = false,
                    searchBooks = action.itemsHolder)
        }
        is FetchingItemsFailedAction -> state.copy(isLoadingItems = false, errorLoadingItems = true, errorMsg = action.message)
        is BookSelected -> state.copy(selectedBook = Book(title = action.book.title,
                authorName = listOf(action.book.author),
                cover_edition_key = action.book.id))

        is ToReadLoaded -> state.copy(toReadBook = action.books.toSet())
        is CompletedLoaded -> state.copy(completed = action.books.toSet())


        else -> {
//            Logger.d("Action ${action::class.simpleName} not handled")
            state
        }
    }
}
inline fun <reified T> castingReducer(crossinline reducer: ((T, Any) -> Any)): Reducer = { state: Any, action: Any ->
    if (state is T) {
        reducer(state as T, action)
    } else {
        { s: Any, _: Any -> s }
    }
}
