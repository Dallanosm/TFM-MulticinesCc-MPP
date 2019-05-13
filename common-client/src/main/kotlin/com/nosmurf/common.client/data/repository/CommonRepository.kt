package com.nosmurf.common.client.data.repository

import com.nosmurf.common.client.data.datasource.local.LocalDataSource
import com.nosmurf.common.client.data.datasource.remote.RemoteDataSource
import com.nosmurf.common.client.domain.repository.Repository
import com.nosmurf.common.client.Comment
import com.nosmurf.common.client.Movie
import com.nosmurf.common.client.MovieDetail
import com.nosmurf.common.client.NewComment

class CommonRepository(
        private val local: LocalDataSource,
        private val remote: RemoteDataSource
) : Repository {

    override suspend fun getMovies(): List<Movie> = remote.getMovies()

    override suspend fun getMovie(id: Long): MovieDetail = remote.getMovie(id)

    override suspend fun getComments(movieId: Long): List<Comment> = remote.getComments(movieId)

    override suspend fun addNewComment(newComment: NewComment, movieId: Long): List<Comment> =
            remote.addNewComment(newComment, movieId)

}