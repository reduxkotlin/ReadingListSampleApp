package org.reduxkotlin.readinglist.common

import org.reduxkotlin.readinglist.common.middleware.Screen
import org.reduxkotlin.readinglist.common.repo.Book

internal sealed class Actions {
    class FetchingItemsStartedAction
    data class FetchingItemsSuccessAction(val itemsHolder: List<Book>)
    data class FetchingItemsFailedAction(val message: String)

    data class BookSelected(val position: Int)

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
