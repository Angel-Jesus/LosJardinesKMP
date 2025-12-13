package com.pe.losjardines.repository.auth

import com.pe.losjardines.base.either.Either
import com.pe.losjardines.base.error.Failure
import com.pe.losjardines.base.network.BaseClient
import com.pe.losjardines.firebase.auth.LoginService
import com.pe.losjardines.repository.AuthRepository
import com.pe.losjardines.usecases.model.AuthResponseDto

class AuthRepositoryImpl(
    private val loginService: LoginService
): BaseClient(), AuthRepository {

    override fun isLoggedIn(): Boolean{
        return loginService.currentUser() != null
    }
    override suspend fun login(email: String, password: String): Either<Failure, AuthResponseDto>{
        return callAuth {
            val result = loginService.login(email, password)
            AuthResponseDto(result.user?.uid.orEmpty(), result.user?.isEmailVerified == true, result.user?.providerId.orEmpty())
        }
    }

    override suspend fun logout(){
        loginService.logout()
    }
}