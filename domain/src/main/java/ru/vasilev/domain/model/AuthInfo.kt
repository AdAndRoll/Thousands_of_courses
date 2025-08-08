package ru.vasilev.domain.model

data class AuthInfo(
    val email: String,
    val isAuthenticated: Boolean = true
)