package com.nosmurf.common.client.domain.repository

import com.nosmurf.common.client.Comment
import com.nosmurf.common.client.Movie
import com.nosmurf.common.client.MovieDetail
import com.nosmurf.common.client.NewComment

interface Repository {
    suspend fun getMovies(): List<Movie>
    suspend fun getMovie(id: Long): MovieDetail
    suspend fun getComments(movieId: Long): List<Comment>
    suspend fun addNewComment(newComment: NewComment, movieId: Long): List<Comment>
}