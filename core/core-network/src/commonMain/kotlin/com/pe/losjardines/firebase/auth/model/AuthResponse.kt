package com.pe.losjardines.firebase.auth.model

import dev.gitlive.firebase.auth.AuthResult

data class AuthResponse(
    val uid: String,
    val isEmailVerified: Boolean,
    val providerId: String
)

 expect suspend fun AuthResult.toAuthResponse(): AuthResponse

internal val EmptyAuthResponse = AuthResponse(
    uid = "",
    isEmailVerified = false,
    providerId = ""
)
