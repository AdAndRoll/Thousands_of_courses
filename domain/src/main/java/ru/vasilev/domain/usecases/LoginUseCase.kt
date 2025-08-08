package ru.vasilev.domain.usecases

import ru.vasilev.domain.model.AuthInfo
import ru.vasilev.domain.repository.AuthRepository


class LoginUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(email: String, password: String): Result<AuthInfo> {
        if (email.isBlank() || password.isBlank()) {
            return Result.failure(IllegalArgumentException("Email and password cannot be empty"))
        }

        return authRepository.login(email, password)
    }
}