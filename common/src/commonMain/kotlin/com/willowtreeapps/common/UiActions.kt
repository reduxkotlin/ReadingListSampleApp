package com.willowtreeapps.common

import com.willowtreeapps.common.ui.BookListItemViewState

sealed class UiActions {
    //Should we fire event when screen shown? or always navigate to a screen
    class ReadingListShown
    class CompletedListShown
    class SearchBtnTapped
    class ReadingListBtnTapped
    class CompletedListBtnTapped
    class BookTapped(val book: BookListItemViewState)
    data class SearchQueryEntered(val query: String)
    class AddToReadingButtonTapped
    class RemoveFromReadingButtonTapped
    class AddToCompletedButtonTapped
    class RemoveFromCompletedButtonTapped


}