package com.nosmurf.common.client.data.datasource.remote

import com.nosmurf.common.client.Comment
import com.nosmurf.common.client.Movie
import com.nosmurf.common.client.MovieDetail
import com.nosmurf.common.client.NewComment

interface RemoteDataSource {
    suspend fun getMovies(): List<Movie>
    suspend fun getMovie(id: Long): MovieDetail
    suspend fun getComments(movieId: Long): List<Comment>
    suspend fun addNewComment(newComment: NewComment, movieId: Long): List<Comment>
}