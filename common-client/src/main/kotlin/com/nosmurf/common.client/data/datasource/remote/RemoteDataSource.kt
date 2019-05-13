package com.nosmurf.common.client.data.datasource.remote

import com.nosmurf.model.Comment
import com.nosmurf.model.Movie
import com.nosmurf.model.MovieDetail
import com.nosmurf.model.NewComment

interface RemoteDataSource {
    suspend fun getMovies(): List<Movie>
    suspend fun getMovie(id: Long): MovieDetail
    suspend fun getComments(movieId: Long): List<Comment>
    suspend fun addNewComment(newComment: NewComment, movieId: Long): List<Comment>
}