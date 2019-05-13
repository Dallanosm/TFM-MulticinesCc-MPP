package com.nosmurf.backend

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.*

fun Application.main() {
    movies()
    comments()
}

fun Application.movies() {
    routing {
        route("/movies") {
            get {
                call.respond(retrieveMovies())
            }

            get("/{id}") {
                val id = call.parameters["id"]?.toLongOrNull()
                when (id) {
                    null -> call.respond(HttpStatusCode.BadRequest, Error("ID must be long"))
                    else -> id.let { call.respond(movieDetail(id)) }
                }
            }
        }
    }
}

fun Application.comments() {
    routing {
        route("/movies/{id}/comments") {
            get {
                val candidateMovieId = call.parameters["id"]?.toLongOrNull()
                when (candidateMovieId) {
                    null -> call.respond(HttpStatusCode.BadRequest, Error("Movie ID must be long"))
                    else -> call.respond(getComments(candidateMovieId))
                }
            }

            post {
                val candidateMovieId = call.parameters["id"]?.toLongOrNull()
                when (candidateMovieId) {
                    null -> call.respond(HttpStatusCode.BadRequest, Error("Movie ID must be long"))
                    else -> {
                        addNewComment(call.receive(), candidateMovieId)
                        call.respond(HttpStatusCode.Created, getComments(candidateMovieId))
                    }
                }
            }

            delete("/{idComment}") {
                val candidateMovieId = call.parameters["id"]?.toLongOrNull()
                when (candidateMovieId) {
                    null -> call.respond(HttpStatusCode.BadRequest, Error("Movie ID must be long"))
                    else -> {
                        val candidateId = call.parameters["idComment"]?.toLongOrNull()
                        when (candidateId) {
                            null -> call.respond(HttpStatusCode.BadRequest, Error("Comment ID must be long"))
                            else -> call.respond(deleteComment(id = candidateId))
                        }
                    }
                }
            }

        }
    }
}