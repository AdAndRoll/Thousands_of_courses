package ru.vasilev.data.remote.datasources

import jakarta.inject.Inject
import ru.vasilev.data.remote.api.CourseApi
import ru.vasilev.data.remote.dto.CoursesResponse

class CoursesRemoteDataSource @Inject constructor(
    private val courseApi: CourseApi
) {
    suspend fun getCourses(): CoursesResponse {
        return courseApi.getCourses()
    }
}