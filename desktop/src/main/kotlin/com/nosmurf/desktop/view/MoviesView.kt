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

class MoviesView : View("Multicines Cáceres"), MoviesView {

    private val progressProperty = SimpleBooleanProperty()
    private var moviesProperty = observableList<Movie>()

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
        datagrid(moviesProperty) {
            cellCache {
                cellHeight = 650.0
                cellWidth = 320.0
                imageview(it.img) {
                    fitHeight = 650.0
                    fitWidth = 320.0
                    onUserSelect { }
                }
            }

        }
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
        moviesProperty.addAll(movies)
    }

}