package ru.vasilev.domain.repository

import ru.vasilev.domain.model.AuthInfo

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<AuthInfo>
}