package ru.vasilev.data.repository

import ru.vasilev.data.local.datasources.FavoriteCoursesLocalDataSource
import ru.vasilev.data.mappers.toDomain

import ru.vasilev.data.remote.datasources.CoursesRemoteDataSource
import ru.vasilev.domain.model.Course
import ru.vasilev.domain.repository.CourseRepository

import javax.inject.Inject

class CoursesRepositoryImpl @Inject constructor(
    private val coursesRemoteDataSource: CoursesRemoteDataSource,
    private val favoriteCoursesLocalDataSource: FavoriteCoursesLocalDataSource
) : CourseRepository {

    override suspend fun getCourses(): List<Course> {
        val remoteCourses = coursesRemoteDataSource.getCourses().courses
        val favoriteIds = favoriteCoursesLocalDataSource.getAllFavorites()

        return remoteCourses.map { courseDto ->
            val isFavorite = favoriteIds.contains(courseDto.id)
            courseDto.toDomain().copy(hasLike = isFavorite)
        }
    }

    override suspend fun getFavoriteCourses(): List<Course> {
        val favoriteIds = favoriteCoursesLocalDataSource.getAllFavorites()
        val allCourses = coursesRemoteDataSource.getCourses().courses

        return allCourses
            .filter { favoriteIds.contains(it.id) }
            .map { it.toDomain().copy(hasLike = true) }
    }

    override suspend fun addFavorite(courseId: String) {
        favoriteCoursesLocalDataSource.addFavorite(courseId)
    }

    override suspend fun removeFavorite(courseId: String) {
        favoriteCoursesLocalDataSource.removeFavorite(courseId)
    }

    override suspend fun isFavorite(courseId: String): Boolean {
        return favoriteCoursesLocalDataSource.isFavorite(courseId)
    }
}