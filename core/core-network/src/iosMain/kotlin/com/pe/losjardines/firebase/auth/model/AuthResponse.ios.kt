package com.pe.losjardines.firebase.auth.model

import dev.gitlive.firebase.auth.AuthResult

actual suspend fun AuthResult.toAuthResponse(): AuthResponse {
    val user = user ?: return EmptyAuthResponse
    return AuthResponse(
        uid = user.uid,
        isEmailVerified = user.isEmailVerified,
        providerId = user.providerId
    )
}
