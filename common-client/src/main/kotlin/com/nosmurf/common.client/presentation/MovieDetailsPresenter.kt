package com.nosmurf.common.client.presentation

import com.nosmurf.common.client.MovieDetail
import com.nosmurf.common.client.domain.error.ErrorHandler
import com.nosmurf.common.client.domain.executor.Executor
import com.nosmurf.common.client.domain.repository.Repository
import com.nosmurf.common.client.domain.usecase.getMovie
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MovieDetailsPresenter(private val executor: Executor, private val repository: Repository,
                            view: MovieDetailView, errorHandler: ErrorHandler) :
        Presenter<MovieDetailView>(view = view, errorHandler = errorHandler) {

    lateinit var movie: MovieDetail

    override fun initialize() {
        view.showProgress()
        GlobalScope.launch(context = executor.main) {
            val movie = getMovie(view.getMovieId(), repository)
            view.showDetails(movie)
            view.hideProgress()
        }
    }

    override fun destroy() {
        // Nothing to do yet
    }

    fun onSeeTrailerClicked() {
        //  view.showTrailer(movie.trailer.split("embed/")[1])
        view.showTrailer(movie.trailer.replace("embed/", "watch?v="))
    }

    fun onSeeComments() {
        view.navigateToCommentsScreen(view.getMovieId())
    }
}

interface MovieDetailView : Presenter.View {
    fun getMovieId(): Long
    fun showDetails(movieDetail: MovieDetail)
    fun showTrailer(movieUrl: String)
    fun navigateToCommentsScreen(id: Long)
}