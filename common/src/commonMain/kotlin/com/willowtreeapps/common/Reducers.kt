package com.willowtreeapps.common

import org.reduxkotlin.ActionTypes
import com.willowtreeapps.common.Actions.*
import com.willowtreeapps.common.repo.Book
import org.reduxkotlin.Reducer

/**
 * Reducers and functions used by reducers are in this file.  Functions must be pure functions without
 * side effects.
 */
val reducer: Reducer<AppState> = { state, action ->
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
        is BookSelected -> {
            val book = state.searchBooks[action.position]
            state.copy(selectedBook = book)
        }

        is ToReadLoaded -> state.copy(readingList = action.books.toSet())
        is CompletedLoaded -> state.copy(completedList = action.books.toSet())
        is PrevBook -> state.copy(selectedBook = state.searchBooks[state.currentSearchIndex() - 1])
        is NextBook -> state.copy(selectedBook = state.searchBooks[state.currentSearchIndex() + 1])

        else -> state
    }
}


inline fun <reified T> reducer(crossinline reducer: ((T, Any) -> T)): Reducer<T> = { state: T, action: Any ->
        reducer(state, action)
}

