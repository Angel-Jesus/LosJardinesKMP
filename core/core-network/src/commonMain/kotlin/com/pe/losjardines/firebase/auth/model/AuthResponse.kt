package com.pe.losjardines.firebase.auth.model

import dev.gitlive.firebase.auth.AuthResult

data class AuthResponse(
    val uid: String,
    val isEmailVerified: Boolean,
    val providerId: String
)

fun AuthResult.toAuthResponse() = AuthResponse(
    uid = user?.uid.orEmpty(),
    isEmailVerified = user?.isEmailVerified == true,
    providerId = user?.providerId.orEmpty()
)
