package com.nosmurf.web.presentation.app

import com.nosmurf.web.presentation.navigator.Screen
import com.nosmurf.web.presentation.screen.movies
import react.*
import react.dom.div

abstract class App : RComponent<RProps, AppState>() {

    init {
        state = AppState()
    }

    override fun RBuilder.render() {
        div("app") {
            when (state.screen) {
                Screen.MOVIES -> movies { setState { screen = it } }

            }
        }
    }
}

class AppState : RState {
    var screen: Screen = Screen.MOVIES
}

fun RBuilder.app() = child(App::class) {}