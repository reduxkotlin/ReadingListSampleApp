package com.willowtreeapps.common.ui

import com.willowtreeapps.common.*
import org.reduxkotlin.SelectorSubscriberFn

class SettingsPresenter(private val engine: GameEngine) : Presenter<SettingsView>() {

    override fun recreateView() {
    }

    override fun makeSubscriber() = SelectorSubscriberFn<AppState>(engine.appStore) {
//        withSingleField({ it.settings }) { view?.showSettings(state.settings.toViewState()) }
    }

    fun numQuestionsChanged(numQuestions: Int) {
        engine.dispatch(Actions.ChangeNumQuestionsSettingsAction(numQuestions))
    }

    fun microphoneModeChanged(enabled: Boolean) {
        if (enabled) {
            view?.askForMicPermissions()
        } else {
            engine.dispatch(Actions.ChangeMicrophoneModeSettingsAction(false))
        }
    }

    fun microphonePermissionGranted() {
        engine.dispatch(Actions.ChangeMicrophoneModeSettingsAction(true))
    }

    fun microphonePermissionDenied() {
        engine.dispatch(Actions.ChangeMicrophoneModeSettingsAction(false))
    }
}