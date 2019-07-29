package com.willowtreeapps.common.ui

import com.willowtreeapps.common.AppState
import com.willowtreeapps.common.external.Presenter
import com.willowtreeapps.common.external.PresenterBuilder
import com.willowtreeapps.common.external.createGenericPresenter
import com.willowtreeapps.common.external.View

//a Presenter typed to our app's State type for convenience
fun <V : LibraryView> presenter(actions: PresenterBuilder<AppState, V>): Presenter<View<AppState>> {
    return createGenericPresenter(actions) as Presenter<View<AppState>>
}
