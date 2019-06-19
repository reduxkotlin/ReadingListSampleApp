package com.willowtreeapps.common

import org.reduxkotlin.ActionTypes
import com.willowtreeapps.common.Actions.*
import org.reduxkotlin.castingReducer

/**
 * Reducers and functions used by reducers are in this file.  Functions must be pure functions without
 * side effects.
 */
val reducer = castingReducer { state: AppState, action ->
    when (action) {
        is ActionTypes.INIT -> {
            AppState.INITIAL_STATE
        }
        is FetchingItemsStartedAction -> state.copy(isLoadingItems = true)
        is FetchingItemsSuccessAction -> {
            state.copy(isLoadingItems = false,
                    items = action.itemsHolder)
        }
        is FetchingItemsFailedAction -> state.copy(isLoadingItems = false, errorLoadingItems = true, errorMsg = action.message)
        is BookSelected -> state.copy(selectedBook = state.bookForId(action.bookId))
        is AddToRead -> {
            state.copy(toReadBook = state.toReadBook.plus(action.book),
                    completed = state.completed.minus(action.book))
        }
        is AddToCompleted -> {
            state.copy(toReadBook = state.toReadBook.minus(action.book),
                    completed = state.completed.plus(action.book))
        }

        is StartOverAction, is ResetGameStateAction -> AppState.INITIAL_STATE.copy(settings = state.settings)


        is ChangeNumQuestionsSettingsAction -> state.copy(settings = state.settings.copy(numQuestions = action.num))
        is ChangeMicrophoneModeSettingsAction -> state.copy(settings = state.settings.copy(microphoneMode = action.enabled))
        is SettingsLoadedAction -> state.copy(settings = action.settings)

        is LoadAllSettingsAction -> {
            state
        }

        else -> {
            Logger.d("Action ${action::class.simpleName} not handled")
            state
        }
    }
}

