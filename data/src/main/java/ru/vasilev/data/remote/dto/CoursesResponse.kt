package ru.vasilev.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CoursesResponse(
    @Json(name = "courses")
    val courses: List<CourseDto>
)