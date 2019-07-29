package com.willowtreeapps.common

import com.willowtreeapps.common.middleware.Screen
import com.willowtreeapps.common.repo.Book
import com.willowtreeapps.common.ui.BookListItemViewState
import com.willowtreeapps.common.ui.View

sealed class Actions {


    class FetchingItemsStartedAction
    data class FetchingItemsSuccessAction(val itemsHolder: List<Book>)
    data class FetchingItemsFailedAction(val message: String)

    data class BookSelected(val book: BookListItemViewState)

    class AddCurrentToCompleted
    class AddCurrentToRead


    class LoadToRead
    data class ToReadLoaded(val books: List<Book>)
    class LoadCompleted
    data class CompletedLoaded(val books: List<Book>)
    data class AttachView<S: Any>(val view: View<S>)

    class NextBook
    class PrevBook
}

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

sealed class NavigationActions {
    data class GotoScreen(val screen: Screen)
}
