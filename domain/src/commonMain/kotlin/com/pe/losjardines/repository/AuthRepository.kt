package com.pe.losjardines.repository

import com.pe.losjardines.base.either.Either
import com.pe.losjardines.base.error.Failure
import com.pe.losjardines.usecases.model.AuthResponseDto

interface AuthRepository {
    fun isLoggedIn(): Boolean
    suspend fun login(email: String, password: String): Either<Failure, AuthResponseDto>
    suspend fun logout()
}