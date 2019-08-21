package com.willowtreeapps.common.ui

/**
 * Actions to be dispatched from the UI layer.  This are the only actions
 * that should be dispatched by the UI.  These actions are handled by the UiMiddleware
 * and dispatches appropriate and reusable actions.
 */
sealed class UiActions {
    //Should we fire event when screen shown? or always navigate to a screen
    class ReadingListShown
    class CompletedListShown
    class SearchBtnTapped
    class ReadingListBtnTapped
    class CompletedListBtnTapped
    class BookTapped(val bookPosition: Int)
    data class SearchQueryEntered(val query: String)
    class AddToReadingButtonTapped
    class RemoveFromReadingButtonTapped
    class AddToCompletedButtonTapped
    class RemoveFromCompletedButtonTapped


}