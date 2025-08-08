package ru.vasilev.data.remote.datasources

import ru.vasilev.data.remote.api.AuthApi
import ru.vasilev.data.remote.dto.AuthResponse
import javax.inject.Inject

class AuthRemoteDataSource @Inject constructor(
    private val authApi: AuthApi
) {
    suspend fun login(email: String, password: String): AuthResponse {
        return authApi.login(email, password)
    }
}