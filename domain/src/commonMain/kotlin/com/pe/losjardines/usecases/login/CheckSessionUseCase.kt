package com.pe.losjardines.usecases.login

import com.pe.losjardines.repository.AuthRepository

class CheckSessionUseCase(
    private val firebaseAuth: AuthRepository
) {
    fun invoke(): Boolean {
        return firebaseAuth.isLoggedIn()
    }
}