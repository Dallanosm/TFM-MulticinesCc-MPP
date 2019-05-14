package com.nosmurf.android.ui.activity

import android.graphics.Typeface
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.eftimoff.viewpagertransformers.BackgroundToForegroundTransformer
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.provider
import com.nosmurf.android.R
import com.nosmurf.android.navigator.navigateToMovieDetailScreen
import com.nosmurf.android.ui.adapter.MoviesAdapter
import com.nosmurf.android.ui.adapter.NextPassAdapter
import com.nosmurf.common.client.EMPTY_STRING
import com.nosmurf.common.client.Movie
import com.nosmurf.common.client.presentation.MoviesPresenter
import com.nosmurf.common.client.presentation.MoviesView
import kotlinx.android.synthetic.main.activity_movies.*

class MoviesActivity : RootActivity<MoviesView>(), MoviesView {

    override val progress: View by lazy { progressView }

    override val presenter: MoviesPresenter by instance()

    override val layoutResourceId: Int = R.layout.activity_movies

    override val activityModule: Kodein.Module = Kodein.Module {
        bind<MoviesPresenter>() with provider {
            MoviesPresenter(
                    repository = instance(),
                    executor = instance(),
                    view = this@MoviesActivity,
                    errorHandler = instance())
        }
    }

    private val nextPassAdapter = NextPassAdapter()

    lateinit var movies: List<Movie>

    override fun initializeUI() {
        movieTitle.typeface = Typeface.createFromAsset(assets, "open-sans-extrabold.ttf")
        nextPasses.adapter = nextPassAdapter
        nextPasses.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
    }

    override fun registerListeners() {
        moviesImages.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                showMovieInfo(position)
            }

        })
    }

    override fun showMovies(movies: List<Movie>) {
        this.movies = movies
        moviesImages.adapter = MoviesAdapter(context = this, items = movies,
                onItemClicked = { navigateToMovieDetailScreen(context = this, id = it.id.toLong()) })
        moviesImages.setPageTransformer(true, BackgroundToForegroundTransformer())
        showMovieInfo(0)
    }

    private fun showMovieInfo(index: Int) {
        val movie = movies[index]
        movieTitle.text = movie.title
        classification.text = movie.classification
        val classificationValue = movie.classification.replace("+", "").toLongOrNull()
        if (classificationValue != null && classificationValue >= 13) {
            if (classificationValue < 18) {
                classification.background.setTint(ContextCompat.getColor(this, R.color.medium_classification))
            } else {
                classification.background.setTint(ContextCompat.getColor(this, R.color.high_classification))
            }
        } else {
            classification.background.setTint(ContextCompat.getColor(this, R.color.low_classification))
        }
        nextPassAdapter.replace(movie.schedule.map { it.time }.toMutableList())
        val p = movie.schedule.firstOrNull { it.price != EMPTY_STRING }?.price?.split("€")?.let { it[1] }
                ?: "-"
        price.text = "$p €"
    }

}