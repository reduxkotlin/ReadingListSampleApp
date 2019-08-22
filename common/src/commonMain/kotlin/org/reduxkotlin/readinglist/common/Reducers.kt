package org.reduxkotlin.readinglist.common

import org.reduxkotlin.ActionTypes
import org.reduxkotlin.readinglist.common.Actions.*
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
        is ReadingListBookSelected -> {
            val book = state.readingList[action.position]
            state.copy(selectedBook = book)
        }
        is CompletedBookSelected -> {
            val book = state.completedList[action.position]
            state.copy(selectedBook = book)
        }
        is SearchBookSelected -> {
            val book = state.searchBooks[action.position]
            state.copy(selectedBook = book)
        }
        is ToReadLoaded -> state.copy(readingList = action.books)
        is CompletedLoaded -> state.copy(completedList = action.books)
        is PrevBook -> state.copy(selectedBook = state.searchBooks[state.currentSearchIndex() - 1])
        is NextBook -> state.copy(selectedBook = state.searchBooks[state.currentSearchIndex() + 1])

        else -> state
    }
}


inline fun <reified T> reducer(crossinline reducer: ((T, Any) -> T)): Reducer<T> = { state: T, action: Any ->
        reducer(state, action)
}

