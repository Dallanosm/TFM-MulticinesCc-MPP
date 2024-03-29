package com.nosmurf.common.client.data.datasource.remote

import com.nosmurf.common.client.*
import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.takeFrom

class CommonRemoteDataSource : RemoteDataSource {

    private val endPoint: String = "http://llanosmunoz.com:8054"

    private val client: HttpClient = HttpClient {
        install(JsonFeature) {
            serializer = KotlinxSerializer().apply {
                setMapper(MoviesResponse::class, MoviesResponse.serializer())
                setMapper(Movie::class, Movie.serializer())
                setMapper(MovieDetail::class, MovieDetail.serializer())
                setMapper(Actor::class, Actor.serializer())
                setMapper(Schedule::class, Schedule.serializer())
                setMapper(Comment::class, Comment.serializer())
                setMapper(CommentResponse::class, CommentResponse.serializer())
                setMapper(NewComment::class, NewComment.serializer())
            }
        }
    }

    private fun HttpRequestBuilder.apiUrl(path: String) {
        url {
            takeFrom(endPoint)
            encodedPath = path
        }
    }

    override suspend fun getMovies(): List<Movie> =
            client.get<MoviesResponse> { apiUrl("movies") }.movies

    override suspend fun getMovie(id: Long): MovieDetail =
            client.get { apiUrl("movies/$id") }

    override suspend fun getComments(movieId: Long): List<Comment> =
            client.get<CommentResponse> { apiUrl("movies/$movieId/comments") }.comments

    override suspend fun addNewComment(newComment: NewComment, movieId: Long): List<Comment> =
            try {
                client.post<CommentResponse> {
                    apiUrl("movies/$movieId/comments")
                    body = newComment
                    contentType(ContentType.Application.Json)
                }.comments
            } catch (e: Exception) {
                println("result: ${e.message}")
                listOf()
            }

}