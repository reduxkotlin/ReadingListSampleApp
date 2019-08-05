package com.willowtreeapps.common.ui

import com.willowtreeapps.common.AppState
import com.willowtreeapps.common.external.*

//a Presenter typed to our app's State type for convenience
fun <V: ViewWithProvider> presenter(actions: PresenterBuilder<AppState, V>): Presenter<View> {
    return createGenericPresenter(actions) as Presenter<View>
}

fun <V: ViewWithProvider> presenterWithViewArg(actions: PresenterBuilderWithViewArg<AppState, V>): Presenter<View> {
    return createGenericPresenter(actions) as Presenter<View>
}
