package ru.vasilev.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.vasilev.data.local.dao.FavoriteCoursesDao
import ru.vasilev.data.local.entities.FavoriteCourseEntity

@Database(
    entities = [FavoriteCourseEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteCoursesDao(): FavoriteCoursesDao
}