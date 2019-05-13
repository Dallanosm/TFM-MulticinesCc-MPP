package com.nosmurf.common.client.presentation

import com.nosmurf.common.client.domain.error.ErrorHandler

class MoviesPresenter(view: MoviesView, errorHandler: ErrorHandler) :
        Presenter<MoviesView>(view = view, errorHandler = errorHandler) {

    override fun initialize() {

    }

    override fun destroy() {

    }

}


interface MoviesView : Presenter.View {

}