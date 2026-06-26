package com.pe.losjardines.usecases.login

import com.pe.losjardines.base.either.Either
import com.pe.losjardines.base.error.Failure
import com.pe.losjardines.repository.AuthRepository
import com.pe.losjardines.usecases.model.AuthResponseDto
import kotlin.coroutines.cancellation.CancellationException

class LoginUseCase(
    private val firebaseAuth: AuthRepository
) {
    @Throws(Exception::class, CancellationException::class, Failure::class)
    suspend fun run(email: String, password: String): AuthResponseDto {
        return when (val result = firebaseAuth.login(email, password)) {
            is Either.Success -> AuthResponseDto(
                uid = result.data.uid,
                isEmailVerified = result.data.isEmailVerified,
                providerId = result.data.providerId
            )
            is Either.Error -> throw result.error
        }
    }
}
