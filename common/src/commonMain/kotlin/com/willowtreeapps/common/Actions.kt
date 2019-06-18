package com.willowtreeapps.common

import com.willowtreeapps.common.repo.Book
import com.willowtreeapps.common.repo.BookHolder


sealed class Actions {

    class FetchingItemsStartedAction
    data class FetchingItemsSuccessAction(val itemsHolder: List<Book>)
    data class FetchingItemsFailedAction(val message: String)

    data class NamePickedAction(val name: String)

    class NextQuestionAction

    class GameCompleteAction

    class StartOverAction
    class ResetGameStateAction

    data class StartQuestionTimerAction(val initialValue: Int)
    class DecrementCountDownAction
    class TimesUpAction


    class SettingsTappedAction
    class LoadAllSettingsAction
    data class SettingsLoadedAction(val settings: UserSettings)
    data class ChangeNumQuestionsSettingsAction(val num: Int)
    data class ChangeMicrophoneModeSettingsAction(val enabled: Boolean)

}

