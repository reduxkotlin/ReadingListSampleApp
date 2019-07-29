package com.willowtreeapps.common

import com.willowtreeapps.common.middleware.Screen
import com.willowtreeapps.common.repo.Book
import com.willowtreeapps.common.ui.BookListItemViewState

internal sealed class Actions {


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

    class NextBook
    class PrevBook
}



internal sealed class NavigationActions {
    data class GotoScreen(val screen: Screen)
}
