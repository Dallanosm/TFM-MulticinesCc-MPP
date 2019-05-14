package com.nosmurf.backend

import com.nosmurf.common.client.*
import io.ktor.http.HttpStatusCode
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.jsoup.Jsoup

fun getComments(filmId: Long): CommentResponse = transaction {
    CommentResponse(CommentVo.select { CommentVo.filmId eq filmId }.toList().map { it.toComment() })
}

fun addNewComment(newComment: NewComment, candidateMovieId: Long): HttpStatusCode {
    transaction {
        CommentVo.insert { commentVo ->
            commentVo[filmId] = candidateMovieId
            commentVo[text] = newComment.value
            commentVo[createdDate] = now()
        }
    }

    return HttpStatusCode.Created
}

fun deleteComment(id: Long): HttpStatusCode {
    transaction { CommentVo.deleteWhere { CommentVo.id eq id } }
    return HttpStatusCode.OK
}

fun retrieveMovies(): MoviesResponse {
    val document = Jsoup.connect(MULTICINES_CACERES_MOVIES).get()

    val titles = document.select("h5")
    val images = document.select("img")
    val divs = document.select("div").filter { it.attr("class") == "info-line" }

    return MoviesResponse(titles.map { it.text() }.map { title ->
        val movieDiv = divs.first { it.select("h5").text() == title }

        val id = movieDiv.attr("ng-click").split(",").first().replace("lookUpFilm(", "")
        val image = images.firstOrNull { it.attr("alt").trim() == title }?.attr("src")

        val scheduleTimes = movieDiv.select("a")
        val schedule = mutableListOf<Schedule>()
        scheduleTimes.forEach {
            val paySite = it.attr("href")
            val paySiteDocument = Jsoup.connect(paySite).get()
            val price = paySiteDocument.select("label").text()

            schedule.add(
                    Schedule(
                            time = it.text(),
                            paymentUrl = paySite,
                            price = price
                    )
            )
        }

        val description = movieDiv.select("p").firstOrNull()?.text()

        Movie(
                id = id,
                title = title,
                img = image ?: "",
                classification = description?.capitalize() ?: "",
                schedule = schedule
        )
    })
}

fun movieDetail(id: Long): MovieDetail {
    val document = Jsoup.connect(MULTICINES_CACERES_MOVIE_DETAIL.replace("{id}", id.toString())).get()

    val title = document.select("h3").text()

    val h5 = document.select("h5")
    val info = h5.first()
    val infoFields = info.text().split(" - ")


    val moreFields = document.select("dd")

    val images = document.select("img")
    val image = images.first().attr("src")

    val cast = mutableListOf<Actor>()

    h5.drop(1).forEachIndexed { index, element ->
        cast.add(Actor(name = element.text(), image = images[index + 2].attr("src")))
    }

    val sinopsis = document.select("span")[1].text()

    val trailer = document.select("iframe").attr("src")

    val ticketsDate = document.select("strong").text()

    val tickets = mutableListOf<Schedule>()

    val ticketsSpan = document.select("span")[0]
    val ticketsSpans = ticketsSpan.select("a")


    ticketsSpans.forEach {
        val paySite = it.attr("href")
        val paySiteDocument = Jsoup.connect(paySite).get()
        val price = paySiteDocument.select("label").text()

        tickets.add(
                Schedule(
                        time = it.text(),
                        paymentUrl = paySite,
                        price = price
                )
        )
    }

    return MovieDetail(
            title = title,
            image = image ?: "",
            country = infoFields[0],
            year = infoFields[1],
            duration = infoFields[2],
            director = moreFields[0].text(),
            releaseDate = moreFields[1].text(),
            classification = moreFields[2].text(),
            cast = cast,
            sinopsis = sinopsis,
            trailer = trailer,
            tickets = tickets
    )

}