package com.nosmurf.desktop.view

import com.nosmurf.common.client.presentation.MoviesPresenter
import com.nosmurf.common.client.presentation.MoviesView
import com.nosmurf.desktop.di.errorHandler
import javafx.beans.property.SimpleBooleanProperty
import javafx.scene.Parent
import tornadofx.*

class SplashView : View("Splash"), MoviesView {

    private val progressProperty = SimpleBooleanProperty()

    private val presenter = MoviesPresenter(
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
}