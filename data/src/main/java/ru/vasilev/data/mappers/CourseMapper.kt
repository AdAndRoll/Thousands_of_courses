package ru.vasilev.data.mappers

import ru.vasilev.data.remote.dto.CourseDto
import ru.vasilev.domain.model.Course
import ru.vasilev.data.local.entities.FavoriteCourseEntity

fun CourseDto.toDomain(): Course {
    return Course(
        id = this.id,
        title = this.title,
        text = this.text,
        price = this.price,
        rate = this.rate,
        startDate = this.startDate,
        publishDate = this.publishDate,
        // hasLike будет устанавливаться в репозитории, исходя из локальной БД
        hasLike = this.hasLike
    )
}

fun Course.toFavoriteEntity(): FavoriteCourseEntity {
    return FavoriteCourseEntity(
        courseId = this.id
    )
}