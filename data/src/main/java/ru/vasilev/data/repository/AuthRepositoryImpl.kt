package ru.vasilev.data.repository


import ru.vasilev.data.mappers.toDomain
import ru.vasilev.data.remote.datasources.AuthRemoteDataSource
import ru.vasilev.domain.model.AuthInfo
import ru.vasilev.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource
) : AuthRepository {

    override suspend fun login(email: String, password: String): Result<AuthInfo> {
        return try {
            val authResponse = authRemoteDataSource.login(email, password)
            val authInfo = authResponse.toDomain(email)
            Result.success(authInfo)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}