package com.nosmurf.common.client.presentation

import com.nosmurf.common.client.Comment
import com.nosmurf.common.client.NewComment
import com.nosmurf.common.client.domain.error.ErrorHandler
import com.nosmurf.common.client.domain.executor.Executor
import com.nosmurf.common.client.domain.repository.Repository
import com.nosmurf.common.client.domain.usecase.addNewComment
import com.nosmurf.common.client.domain.usecase.getComments
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CommentsPresenter(private val executor: Executor, private val repository: Repository,
                        view: CommentsView, errorHandler: ErrorHandler) :
        Presenter<CommentsView>(view = view, errorHandler = errorHandler) {

    private val movieId by lazy { view.getMovieId() }

    override fun initialize() {
        view.showProgress()
        GlobalScope.launch(context = executor.main) {
            val comments = getComments(movieId, repository)
            showComments(comments)
        }
    }

    override fun destroy() {
        // Nothing to do yet
    }

    fun addComment(newComment: String) {
        view.showProgress()
        GlobalScope.launch(context = executor.main) {
            val comments = addNewComment(
                    newComment = NewComment(newComment),
                    movieId = movieId,
                    repository = repository)
            view.clearNewCommentInput()
            showComments(comments)
        }
    }

    private fun showComments(comments: List<Comment>) {
        if (comments.isNotEmpty()) {
            view.showComments(comments)
        } else {
            view.showEmptyContentView()
        }
        view.hideProgress()
    }

    fun onCloseClick() {
        view.finish()
    }

}

interface CommentsView : Presenter.View {
    fun getMovieId(): Long
    fun showComments(comments: List<Comment>)
    fun showEmptyContentView()
    fun finish()
    fun clearNewCommentInput()
}