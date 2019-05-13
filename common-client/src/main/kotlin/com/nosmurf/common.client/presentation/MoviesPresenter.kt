package com.nosmurf.common.client.presentation

import com.nosmurf.common.client.domain.error.ErrorHandler
import com.nosmurf.common.client.domain.executor.Executor
import com.nosmurf.common.client.domain.repository.Repository
import com.nosmurf.common.client.domain.usecase.getMovies
import com.nosmurf.common.client.Movie
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MoviesPresenter(private val executor: Executor, private val repository: Repository,
                      view: MoviesView, errorHandler: ErrorHandler) :
        Presenter<MoviesView>(view = view, errorHandler = errorHandler) {

    override fun initialize() {
        view.showProgress()
        GlobalScope.launch(context = executor.main) {
            val movies = getMovies(repository)
            view.showMovies(movies)
            view.hideProgress()
        }
    }

    override fun destroy() {
        // Nothing to do yet
    }
}


interface MoviesView : Presenter.View {
    fun showMovies(movies: List<Movie>)
}