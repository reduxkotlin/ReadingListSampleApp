package com.willowtreeapps.common

import com.willowtreeapps.common.repo.Book

sealed class Actions {


    class FetchingItemsStartedAction
    data class FetchingItemsSuccessAction(val itemsHolder: List<Book>)
    data class FetchingItemsFailedAction(val message: String)

    data class BookSelected(val book: BookListItemViewState)

    data class AddToCompleted(val book: Book)
    data class AddToRead(val book: Book)


    class LoadAllSettingsAction
    data class ChangeNumQuestionsSettingsAction(val num: Int)
    data class ChangeMicrophoneModeSettingsAction(val enabled: Boolean)
    class LoadToRead
    data class ToReadLoaded(val books: List<Book>)
    class LoadCompleted
    data class CompletedLoaded(val books: List<Book>)
}

