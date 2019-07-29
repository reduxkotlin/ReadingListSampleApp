package com.willowtreeapps.common.middleware

import com.willowtreeapps.common.Actions
import com.willowtreeapps.common.NavigationActions
import com.willowtreeapps.common.NetworkThunks
import com.willowtreeapps.common.UiActions
import org.reduxkotlin.Dispatcher
import org.reduxkotlin.Store
import org.reduxkotlin.middleware

/**
 * Handles UI actions and splits to other events.
 */
fun uiActionMiddleware(networkThunks: NetworkThunks) = middleware { store, next, action ->
    val dispatch = store.dispatch
    when (action) {
        is UiActions.SearchQueryEntered -> dispatch(networkThunks.fetchBooksThunk2(action.query))
        is UiActions.BookTapped -> {
            dispatch(Actions.BookSelected(action.book))
            dispatch(NavigationActions.GotoScreen(Screen.BOOK_DETAILS))
        }
        is UiActions.AddToCompletedButtonTapped -> dispatch(Actions.AddCurrentToCompleted())
        is UiActions.AddToReadingButtonTapped -> dispatch(Actions.AddCurrentToRead())
    }
    next(action)
}

