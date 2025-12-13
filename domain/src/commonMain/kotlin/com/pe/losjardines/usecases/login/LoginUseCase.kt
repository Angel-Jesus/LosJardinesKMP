package com.pe.losjardines.usecases.login

import com.pe.losjardines.base.either.Either
import com.pe.losjardines.base.error.Failure
import com.pe.losjardines.base.usecase.BaseSafeUseCase
import com.pe.losjardines.repository.AuthRepository
import com.pe.losjardines.usecases.login.LoginUseCase.Params
import com.pe.losjardines.usecases.model.AuthResponseDto

class LoginUseCase(
    private val firebaseAuth: AuthRepository,
): BaseSafeUseCase<Params, AuthResponseDto>() {
    data class Params(
        val email: String,
        val password: String
    )

    override suspend fun run(params: Params): Either<Failure, AuthResponseDto> {
        return firebaseAuth.login(params.email, params.password).mapSafely {
            AuthResponseDto(
                uid = it.uid,
                isEmailVerified = it.isEmailVerified,
                providerId = it.providerId
            )
        }
    }
}