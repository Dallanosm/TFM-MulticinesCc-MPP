package com.nosmurf.android.ui.activity

import android.content.Context
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.RequestOptions.bitmapTransform
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.provider
import com.nosmurf.android.R
import com.nosmurf.android.extensions.load
import com.nosmurf.android.extensions.showMe
import com.nosmurf.android.navigator.openVideo
import com.nosmurf.android.ui.adapter.NextPassAdapter
import com.nosmurf.common.client.MovieDetail
import com.nosmurf.common.client.presentation.MovieDetailView
import com.nosmurf.common.client.presentation.MovieDetailsPresenter
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.android.synthetic.main.activity_movie_details.*


class MovieDetailsActivity : RootActivity<MovieDetailView>(), MovieDetailView {

    companion object {
        const val DIALOG_TAG = "DIALOG"
        const val MOVIE_ID_KEY = "MOVIE_ID_KEY"

        fun getCallingIntent(context: Context, id: Long): Intent {
            val intent = Intent(context, MovieDetailsActivity::class.java)
            intent.putExtra(MOVIE_ID_KEY, id)
            return intent
        }
    }

    override val progress: View by lazy { progressView }

    override val presenter: MovieDetailsPresenter by instance()

    override val layoutResourceId: Int = R.layout.activity_movie_details

    override val activityModule: Kodein.Module = Kodein.Module {
        bind<MovieDetailsPresenter>() with provider {
            MovieDetailsPresenter(
                    executor = instance(),
                    repository = instance(),
                    view = this@MovieDetailsActivity,
                    errorHandler = instance()
            )
        }
    }

    private val nextPassAdapter = NextPassAdapter()

    override fun initializeUI() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        nextPasses.adapter = nextPassAdapter
        nextPasses.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
    }

    override fun registerListeners() {
        seeTrailer.setOnClickListener { presenter.onSeeTrailerClicked() }
        seeComments.setOnClickListener { presenter.onSeeComments() }
    }

    override fun getMovieId(): Long {
        return intent?.extras?.getLong(MOVIE_ID_KEY) ?: throw Exception("Id must be not null")
        //return 4311
    }

    override fun showDetails(movieDetail: MovieDetail) {
        movieTitle.text = movieDetail.title
        classification.text = movieDetail.classification
        movieImage.load(movieDetail.image)

        val options = RequestOptions()
        options.fitCenter()

        Glide.with(background).load(movieDetail.image)
                .apply(options)
                .apply(bitmapTransform(BlurTransformation(25, 3)))
                .into(background)

        sinopsis.text = movieDetail.sinopsis
        nextPassAdapter.replace(movieDetail.tickets.map { it.time }.toMutableList())
        seeComments.showMe()
    }

    override fun showTrailer(movieUrl: String) {
        openVideo(this, videoURL = movieUrl)
    }

    override fun navigateToCommentsScreen(id: Long) {
        // val commentsDialog = CommentsDialog.newInstance(id)
        //commentsDialog.show(supportFragmentManager, DIALOG_TAG)
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}