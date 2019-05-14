package com.nosmurf.desktop.view

import com.nosmurf.common.client.Movie
import com.nosmurf.common.client.presentation.MoviesPresenter
import com.nosmurf.common.client.presentation.MoviesView
import com.nosmurf.desktop.di.errorHandler
import com.nosmurf.desktop.di.executor
import com.nosmurf.desktop.di.repository
import javafx.beans.property.SimpleBooleanProperty
import javafx.scene.Parent
import tornadofx.*

class SplashView : View("Movies"), MoviesView {
    override fun showError(errorId: Int) {

    }

    override fun showMessage(messageId: Int) {

    }

    private val progressProperty = SimpleBooleanProperty()

    private val presenter = MoviesPresenter(
            executor = executor,
            repository = repository,
            errorHandler = errorHandler,
            view = this
    )

    override fun onDock() {
        super.onDock()
        presenter.initialize()
    }

    override fun onUndock() {
        super.onUndock()
        presenter.destroy()
    }

    override val root: Parent = stackpane {
        fitToParentSize()
        progressbar { visibleWhen { progressProperty } }
    }

    override fun showProgress() {
        progressProperty.value = true
    }

    override fun hideProgress() {
        progressProperty.value = false
    }

    override fun showError(error: String) {
        println("showError: $error")
    }

    override fun showMessage(message: String) {
        println("showMessage: $message")
    }

    override fun showMovies(movies: List<Movie>) {

    }

}