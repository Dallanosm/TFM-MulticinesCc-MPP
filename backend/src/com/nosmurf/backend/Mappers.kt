package com.nosmurf.backend

import com.nosmurf.common.client.Comment
import org.jetbrains.exposed.sql.ResultRow

fun ResultRow.toComment() = Comment(
        id = this[CommentVo.id].toLong(),
        movieId = this[CommentVo.filmId].toLong(),
        value = this[CommentVo.text],
        createdDate = this[CommentVo.createdDate]
)