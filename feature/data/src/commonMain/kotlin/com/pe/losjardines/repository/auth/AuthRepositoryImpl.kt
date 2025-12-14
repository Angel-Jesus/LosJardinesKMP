package com.pe.losjardines.repository.auth

import com.pe.losjardines.base.either.Either
import com.pe.losjardines.base.error.Failure
import com.pe.losjardines.base.network.BaseClient
import com.pe.losjardines.firebase.auth.LoginManager
import com.pe.losjardines.repository.AuthRepository
import com.pe.losjardines.repository.auth.mapper.toData
import com.pe.losjardines.usecases.model.AuthResponseDto

class AuthRepositoryImpl(
    private val loginManager: LoginManager
): BaseClient(), AuthRepository {

    override fun isLoggedIn(): Boolean{
        return loginManager.isLoggedIn()
    }
    override suspend fun login(email: String, password: String): Either<Failure, AuthResponseDto>{
        return callAuth {
           loginManager.login(email, password).toData()
        }
    }

    override suspend fun logout(){
        loginManager.logout()
    }
}