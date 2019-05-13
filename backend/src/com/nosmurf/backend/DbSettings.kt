package com.nosmurf.backend

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object CommentVo : Table("comments") {
    val id: Column<Long> = long("comment_id").autoIncrement().primaryKey()
    val filmId: Column<Long> = long("film_id")
    val text: Column<String> = varchar("text", 500)
    val createdDate: Column<Long> = long("date")
}