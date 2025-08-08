package ru.vasilev.data.remote.api

import ru.vasilev.data.remote.dto.CoursesResponse
import retrofit2.http.GET

interface CourseApi {
    @GET("courses/all")
    suspend fun getCourses(): CoursesResponse
}