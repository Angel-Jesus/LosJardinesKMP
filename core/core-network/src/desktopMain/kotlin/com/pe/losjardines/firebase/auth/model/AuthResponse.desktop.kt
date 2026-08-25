package com.pe.losjardines.firebase.auth.model

import dev.gitlive.firebase.auth.AuthResult

/**
 * En JVM el firebase-java-sdk no implementa [dev.gitlive.firebase.auth.FirebaseUser.isEmailVerified]
 * ni `providerId` (lanzan NotImplementedError), asi que esos datos se leen de los claims del ID token.
 */
actual suspend fun AuthResult.toAuthResponse(): AuthResponse {
    val user = user ?: return EmptyAuthResponse
    val tokenResult = user.getIdTokenResult(forceRefresh = false)
    return AuthResponse(
        uid = user.uid,
        isEmailVerified = tokenResult.claims["email_verified"] as? Boolean == true,
        providerId = tokenResult.signInProvider.orEmpty()
    )
}
