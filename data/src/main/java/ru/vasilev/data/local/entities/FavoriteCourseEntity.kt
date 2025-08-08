package ru.vasilev.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_courses")
data class FavoriteCourseEntity(
    @PrimaryKey
    val courseId: String
)