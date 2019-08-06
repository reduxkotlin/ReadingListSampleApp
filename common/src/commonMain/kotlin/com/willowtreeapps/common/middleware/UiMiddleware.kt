package com.willowtreeapps.common.middleware

import com.willowtreeapps.common.Actions
import com.willowtreeapps.common.AppState
import com.willowtreeapps.common.NavigationActions
import com.willowtreeapps.common.NetworkThunks
import com.willowtreeapps.common.ui.UiActions
import org.reduxkotlin.middleware

/**
 * Handles UI actions and splits to other events.
 */
fun uiActionMiddleware(networkThunks: NetworkThunks) = middleware<AppState> { store, next, action ->
    val dispatch = store.dispatch
    val result = next(action)
    when (action) {
        is UiActions.SearchQueryEntered -> dispatch(networkThunks.fetchBooksThunk(action.query))
        is UiActions.BookTapped -> {
            dispatch(Actions.BookSelected(action.book))
            dispatch(NavigationActions.GotoScreen(Screen.BOOK_DETAILS))
        }
        is UiActions.AddToCompletedButtonTapped -> dispatch(Actions.AddCurrentToCompleted())
        is UiActions.AddToReadingButtonTapped -> dispatch(Actions.AddCurrentToRead())
        is UiActions.SearchBtnTapped -> dispatch(NavigationActions.GotoScreen(Screen.SEARCH))
        is UiActions.ReadingListBtnTapped -> dispatch(NavigationActions.GotoScreen(Screen.READING_LIST))
        is UiActions.CompletedListBtnTapped -> dispatch(NavigationActions.GotoScreen(Screen.COMPLETED_LIST))
        is UiActions.ReadingListShown -> dispatch(Actions.LoadToRead())
        is UiActions.CompletedListShown -> dispatch(Actions.LoadCompleted())
    }
    result
}

