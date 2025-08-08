package ru.vasilev.data.local.datasources

import ru.vasilev.data.local.dao.FavoriteCoursesDao
import ru.vasilev.data.local.entities.FavoriteCourseEntity
import javax.inject.Inject

class FavoriteCoursesLocalDataSource @Inject constructor(
    private val favoriteCoursesDao: FavoriteCoursesDao
) {
    suspend fun addFavorite(courseId: String) {
        favoriteCoursesDao.addFavorite(FavoriteCourseEntity(courseId))
    }

    suspend fun removeFavorite(courseId: String) {
        favoriteCoursesDao.removeFavorite(courseId)
    }

    suspend fun getAllFavorites(): List<String> {
        return favoriteCoursesDao.getAllFavorites().map { it.courseId }
    }

    suspend fun isFavorite(courseId: String): Boolean {
        return favoriteCoursesDao.isFavorite(courseId)
    }
}