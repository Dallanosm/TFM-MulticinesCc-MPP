package com.nosmurf.android.ui.dialog

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.provider
import com.nosmurf.android.R
import com.nosmurf.android.extensions.hideMe
import com.nosmurf.android.extensions.showMe
import com.nosmurf.android.extensions.toast
import com.nosmurf.android.ui.adapter.CommentsAdapter
import com.nosmurf.common.client.Comment
import com.nosmurf.common.client.EMPTY_STRING
import com.nosmurf.common.client.presentation.CommentsPresenter
import com.nosmurf.common.client.presentation.CommentsView
import kotlinx.android.synthetic.main.dialog_comments.*
import kotlinx.android.synthetic.main.view_toolbar.*

class CommentsDialog : RootDialog<CommentsView>(), CommentsView {

    companion object {
        const val MOVIE_ID = "MOVIE_ID"

        fun newInstance(movieId: Long): CommentsDialog {
            val commentsDialog = CommentsDialog()

            val extras = Bundle()
            extras.putLong(MOVIE_ID, movieId)

            commentsDialog.arguments = extras
            return commentsDialog
        }
    }

    override val layoutResourceId: Int = R.layout.dialog_comments

    override val presenter: CommentsPresenter by instance()

    override val fragmentModule: Kodein.Module = Kodein.Module {
        bind<CommentsPresenter>() with provider {
            CommentsPresenter(
                    executor = instance(),
                    repository = instance(),
                    view = this@CommentsDialog,
                    errorHandler = instance())
        }
    }

    private val adapter = CommentsAdapter()

    override fun initializeUI() {
        toolbarTitle.text = getString(R.string.comments)

        commentsView.adapter = adapter
        commentsView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
    }

    override fun registerListeners() {
        close.setOnClickListener { presenter.onCloseClick() }
        addNewComment.setOnClickListener { presenter.addComment(newComment.text.toString()) }
    }

    override fun getMovieId(): Long = arguments?.getLong(MOVIE_ID)
            ?: throw IllegalArgumentException("Movie id must be long")


    override fun showProgress() = progressView.showMe()
    override fun hideProgress() = progressView.hideMe()

    override fun showError(error: String) = toast(error)
    override fun showMessage(message: String) = toast(message)

    override fun showComments(comments: List<Comment>) {
        emptyContentView.hideMe()
        commentsView.showMe()
        adapter.replace(comments.toMutableList())
    }

    override fun showEmptyContentView() {
        emptyContentView.showMe()
        commentsView.hideMe()
    }

    override fun clearNewCommentInput() = newComment.setText(EMPTY_STRING)

    override fun finish() = dismiss()

}