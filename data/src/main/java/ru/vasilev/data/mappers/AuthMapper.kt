package ru.vasilev.data.mappers

import ru.vasilev.data.remote.dto.AuthResponse
import ru.vasilev.domain.model.AuthInfo

fun AuthResponse.toDomain(email: String): AuthInfo {
    return AuthInfo(
        email = email,
        isAuthenticated = this.success
    )
}