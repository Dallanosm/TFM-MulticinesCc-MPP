package com.nosmurf.common.client

import kotlinx.serialization.Serializable


@Serializable
data class MoviesResponse(val movies: List<Movie>)

@Serializable
data class Movie(
        val id: String,
        val title: String,
        val img: String,
        val classification: String,
        val schedule: List<Schedule>
)

@Serializable
data class MovieDetail(
        val title: String,
        val image: String,
        val country: String,
        val year: String,
        val duration: String,
        val director: String,
        val releaseDate: String,
        val classification: String,
        val cast: List<Actor>,
        val sinopsis: String,
        val trailer: String,
        val tickets: List<Schedule>
)

@Serializable
data class Actor(
        val name: String,
        val image: String
)

@Serializable
data class Schedule(
        val time: String,
        val paymentUrl: String,
        val price: String
)

@Serializable
data class CommentResponse(val comments: List<Comment>)

@Serializable
data class Comment(
        val id: Long,
        val createdDate: Long,
        val value: String
)

@Serializable
data class NewComment(val value: String)