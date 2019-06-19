package com.willowtreeapps.common

import com.willowtreeapps.common.repo.Book
import org.reduxkotlin.Thunk


sealed class Actions {


    class FetchingItemsStartedAction
    data class FetchingItemsSuccessAction(val itemsHolder: List<Book>)
    data class FetchingItemsFailedAction(val message: String)

    open class ThrottledAction(val waitTimeMs: Int, val thunk: Thunk)

    data class BookSelected(val bookId: String)

    data class AddToCompleted(val book: Book)
    data class AddToRead(val book: Book)

    class StartOverAction
    class ResetGameStateAction


    class SettingsTappedAction
    class LoadAllSettingsAction
    data class SettingsLoadedAction(val settings: UserSettings)
    data class ChangeNumQuestionsSettingsAction(val num: Int)
    data class ChangeMicrophoneModeSettingsAction(val enabled: Boolean)

}

