package ru.vasilev.domain.repository

import ru.vasilev.domain.model.Course

interface CourseRepository {
    suspend fun getCourses(): List<Course>

    suspend fun getFavoriteCourses(): List<Course>

    suspend fun addFavorite(courseId: String)

    suspend fun removeFavorite(courseId: String)

    suspend fun isFavorite(courseId: String): Boolean
}