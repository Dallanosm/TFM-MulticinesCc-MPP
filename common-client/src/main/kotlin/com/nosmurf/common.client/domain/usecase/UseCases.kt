package com.nosmurf.common.client.domain.usecase

import com.nosmurf.common.client.domain.repository.Repository
import com.nosmurf.common.client.Comment
import com.nosmurf.common.client.Movie
import com.nosmurf.common.client.MovieDetail
import com.nosmurf.common.client.NewComment

suspend fun getMovies(repository: Repository): List<Movie> = repository.getMovies()

suspend fun getMovie(id: Long, repository: Repository): MovieDetail = repository.getMovie(id)

suspend fun getComments(movieId: Long, repository: Repository): List<Comment> =
        repository.getComments(movieId)

suspend fun addNewComment(newComment: NewComment, movieId: Long, repository: Repository): List<Comment> =
        repository.addNewComment(newComment, movieId)