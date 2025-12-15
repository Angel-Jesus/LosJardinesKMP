package com.pe.losjardines.usecases.login

import com.pe.losjardines.repository.AuthRepository

class LogoutUseCase(
    private val firebaseAuthRepository: AuthRepository
) {
    suspend fun invoke(){
        firebaseAuthRepository.logout()
    }
}