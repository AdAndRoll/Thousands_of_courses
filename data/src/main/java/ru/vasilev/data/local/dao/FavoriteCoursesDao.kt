package ru.vasilev.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.vasilev.data.local.entities.FavoriteCourseEntity

@Dao
interface FavoriteCoursesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(entity: FavoriteCourseEntity)

    @Query("DELETE FROM favorite_courses WHERE courseId = :courseId")
    suspend fun removeFavorite(courseId: String)

    @Query("SELECT * FROM favorite_courses")
    suspend fun getAllFavorites(): List<FavoriteCourseEntity>

    @Query("SELECT EXISTS(SELECT * FROM favorite_courses WHERE courseId = :courseId)")
    suspend fun isFavorite(courseId: String): Boolean
}