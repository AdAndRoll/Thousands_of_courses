package ru.vasilev.data.remote.api

import ru.vasilev.data.remote.dto.CoursesResponse
import retrofit2.http.GET

interface CourseApi {
    @GET("https://drive.usercontent.google.com/u/0/uc?id=15arTK7XT2b7Yv4BJsmDctA4Hg-BbS8-q&export=download")
    suspend fun getCourses(): CoursesResponse
}