package com.pe.losjardines.firebase.auth

import com.pe.losjardines.firebase.auth.model.AuthResponse
import com.pe.losjardines.firebase.auth.model.toAuthResponse

class LoginManager(
    private val loginService: LoginService
) {
    fun isLoggedIn(): Boolean{
        return loginService.currentUser() != null
    }

    suspend fun login(email: String, password: String): AuthResponse{
        return loginService.login(email, password).toAuthResponse()
    }

    suspend fun logout(){
        loginService.logout()
    }
}