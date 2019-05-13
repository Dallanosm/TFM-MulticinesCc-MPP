package com.nosmurf.web.presentation.screen

import com.nosmurf.common.client.Movie
import com.nosmurf.common.client.presentation.MoviesPresenter
import com.nosmurf.common.client.presentation.MoviesView
import com.nosmurf.web.presentation.di.errorHandler
import com.nosmurf.web.presentation.di.executor
import com.nosmurf.web.presentation.di.repository
import com.nosmurf.web.presentation.navigator.Screen
import react.RBuilder
import react.RProps

class SplashScreen : RootScreen<SplashProps, SplashState, MoviesView>(), MoviesView {

    override val presenter: MoviesPresenter = MoviesPresenter(
            executor = executor,
            repository = repository,
            view = this,
            errorHandler = errorHandler
    )

    override fun RBuilder.render() {

    }

    override fun showError(error: String) {
        println(error)
    }

    override fun showMessage(message: String) {
        println(message)
    }

    override fun showMovies(movies: List<Movie>) {

    }
}

interface SplashState : ScreenState

interface SplashProps : RProps {
    var onDownloadFinished: (Screen) -> Unit
}

fun RBuilder.movies(downloadFinished: (Screen) -> Unit) = child(SplashScreen::class) {
    attrs.onDownloadFinished = downloadFinished
}