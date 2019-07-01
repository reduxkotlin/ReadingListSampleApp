package com.willowtreeapps.common.ui

import com.willowtreeapps.common.AppState
import com.willowtreeapps.common.Presenter
import com.willowtreeapps.common.PresenterBuilder
import com.willowtreeapps.common.createGenericPresenter

//a Presenter typed to our app's State type for convenience
fun <V : LibraryView> presenter(actions: PresenterBuilder<AppState, V>): Presenter<View<AppState>> {
    return createGenericPresenter(actions) as Presenter<View<AppState>>
}
