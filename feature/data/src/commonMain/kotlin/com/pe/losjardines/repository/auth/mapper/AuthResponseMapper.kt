package com.pe.losjardines.repository.auth.mapper

import com.pe.losjardines.firebase.auth.model.AuthResponse
import com.pe.losjardines.usecases.model.AuthResponseDto

fun AuthResponse.toData(): AuthResponseDto = AuthResponseDto(
    uid = this.uid,
    isEmailVerified = this.isEmailVerified,
    providerId = this.providerId
)