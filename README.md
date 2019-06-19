# Open Library Sample Project



A Kotlin multiplatform (Android/iOS) Library app.   The app utilizes the following:
 * [Ktor](https://ktor.io/clients/http-client.html) for networking
 * [Kotlinx Serialization](https://github.com/Kotlin/kotlinx.serialization)
 * [Multiplatform Settings](https://github.com/russhwolf/multiplatform-settings)
 * [SqlDelight](https://github.com/square/sqldelight)
 * [ReduxKotlin](https://reduxkotlin.org)
 


## Android
Building and testing the Android App can be completed with:
```./gradlew build```
or install with:
```./gradle androidInstall```
or opened and ran in Android Studio


## iOS
iOS can be implemented quickly now that code is in a common module.  


## Architecture

A `LibraryApp` object holds the state of the app in a redux store and provides a methods for views (fragments/UIViewControllers) to "attach".  The `LibraryApp` is initialized in the Application class on Android, and the AppDelegate on iOS.  Because this `LibraryApp` and the store is created at the application scope, the application state survives between ViewControllers/Fragments and rotation.  Each view must attach/detach from the GameEngine when it is visible.  `LibraryApp.attachView(view)` returns the appropriate presenter for the view.  

`BaseLibraryViewFragment` & `BaseLibraryViewController` handle attaching/detaching the presenter at the appropriate lifecycle methods.  Each Fragment/ViewController extends from these.

An MVP arch is used with a redux store as the 'Model'.  This approach allows maximum reuse of code and a simple contract for the platforms to satisfy.  Presenters send `ViewStates` (simple data classes with fields needed to render UI) to the View interface.  The View implementation has a reference to the presenter, and calls methods on the presenter for user interaction.  This creates a unidirectional dataflow:

    User interaction -> Dispatch Action -> new state (reduce) -> view rendered by presenter
    
## "Dumb Views"
Views in this arch are truly 'dumb' - they should contain nearly no logic.  They are responsible for rendering the view based on the `ViewState` given to them by the presenter. They are implemented for each platform and utilize native UI SDKs and libs for each platform.  Android uses Fragments and iOS uses UIViewControllers.

## Presenters

![](https://storage.googleapis.com/treestorage/ui_f_of_state.png)


Presenters give a layer of control between subscribing to the new state and the View.  Views subscribing directly to the store results in code and logic in the View which must be duplicated on each platform.  Presenters are singleton objects that contain no state other than the previous AppState.  This works while presenters are for an entire screen, which for this app is the case.  Another approach will be needed if multiple instances of a given presenter are needed.  The presenter is responsible for rendering the view given the AppState or the delta in AppState.  The Reduks library has a port of Reselect, which allows calling code only when a property changes.  Presenters pass `ViewState` to View methods.  All transformations from Appstate -> ViewState are extension functions in `Transformations.kt`.



![arch diagram](https://storage.googleapis.com/treestorage/Kotlin%20MPP%20Demo%20Arch.png)

## Async Actions
In the redux world there are many ways to handle creation of async actions.  `Thunks` have been used in this app.  `NetworkThunks`  both use coroutines to launch concurrent operations that dispatch actions.

## Navigation
In this app, Navigation is considered a side effect of the AppState.  The `NavigationMiddleware` handles changing screens based on dispatched actions.  The `NavigationMiddleware` takes an implementation of `Navigator` which is implemented for each platform.

#Tests
Unforntunately ran out of time and do not have tests.  Unit tests can be written in the common module and ran in JVM and native.  Reducers are very simple to tests and quick to run.
