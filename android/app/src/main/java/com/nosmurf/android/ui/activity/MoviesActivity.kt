package com.nosmurf.android.ui.activity

import android.widget.Toast
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.provider
import com.nosmurf.android.R
import com.nosmurf.common.client.presentation.MoviesPresenter
import com.nosmurf.common.client.presentation.MoviesView
import com.nosmurf.common.client.Movie

class MoviesActivity : RootActivity<MoviesView>(), MoviesView {

    override val presenter: MoviesPresenter by instance()

    override val layoutResourceId: Int = R.layout.activity_movies

    override val activityModule: Kodein.Module = Kodein.Module {
        bind<MoviesPresenter>() with provider {
            MoviesPresenter(
                    executor = instance(),
                    repository = instance(),
                    view = this@MoviesActivity,
                    errorHandler = instance()
            )
        }
    }

    override fun initializeUI() {

    }

    override fun registerListeners() {

    }

    override fun showProgress() {

    }

    override fun hideProgress() {

    }

    override fun showMovies(movies: List<Movie>) {
        Toast.makeText(this, movies.toString(), Toast.LENGTH_LONG).show()
    }

}