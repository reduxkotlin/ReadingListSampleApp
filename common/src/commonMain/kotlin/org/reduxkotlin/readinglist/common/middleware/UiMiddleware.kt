package org.reduxkotlin.readinglist.common.middleware

import org.reduxkotlin.readinglist.common.Actions
import org.reduxkotlin.readinglist.common.AppState
import org.reduxkotlin.readinglist.common.NavigationActions
import org.reduxkotlin.readinglist.common.NetworkThunks
import org.reduxkotlin.readinglist.common.ui.UiActions
import org.reduxkotlin.middleware

/**
 * Handles UI actions and splits to other events.
 */
fun uiActionMiddleware(networkThunks: NetworkThunks) = middleware<AppState> { store, next, action ->
    val dispatch = store.dispatch
    val result = next(action)
    when (action) {
        is UiActions.SearchQueryEntered -> dispatch(networkThunks.fetchBooksThunk(action.query))
        is UiActions.ReadingListBookTapped -> {
            dispatch(Actions.ReadingListBookSelected(action.position))
            dispatch(NavigationActions.GotoScreen(Screen.BOOK_DETAILS))
        }
        is UiActions.CompletedBookTapped -> {
            dispatch(Actions.CompletedBookSelected(action.position))
            dispatch(NavigationActions.GotoScreen(Screen.BOOK_DETAILS))
        }
        is UiActions.SearchBookTapped -> {
            dispatch(Actions.SearchBookSelected(action.position))
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

