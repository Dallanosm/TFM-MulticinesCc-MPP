package com.nosmurf.common.client.domain.usecase

import com.nosmurf.common.client.domain.repository.Repository
import com.nosmurf.model.Comment
import com.nosmurf.model.Movie
import com.nosmurf.model.MovieDetail
import com.nosmurf.model.NewComment

suspend fun getMovies(repository: Repository): List<Movie> = repository.getMovies()

suspend fun getMovie(id: Long, repository: Repository): MovieDetail = repository.getMovie(id)

suspend fun getComments(movieId: Long, repository: Repository): List<Comment> =
        repository.getComments(movieId)

suspend fun addNewComment(newComment: NewComment, movieId: Long, repository: Repository): List<Comment> =
        repository.addNewComment(newComment, movieId)