package ru.vasilev.data.remote.api

import retrofit2.http.POST
import retrofit2.http.Query
import ru.vasilev.data.remote.dto.AuthResponse

interface AuthApi {
    @POST("auth/login")
    suspend fun login(
        @Query("email") email: String,
        @Query("password") password: String
    ): AuthResponse
}