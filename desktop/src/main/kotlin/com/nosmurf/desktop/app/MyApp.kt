package com.nosmurf.desktop.app

import com.nosmurf.desktop.view.MoviesView
import javafx.stage.Stage
import tornadofx.*

class MyApp : App(MoviesView::class, Styles::class) {

    override fun start(stage: Stage) {
        super.start(stage)
        stage.isMaximized = true
    }
}