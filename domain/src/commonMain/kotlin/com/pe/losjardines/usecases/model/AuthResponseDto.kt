package com.pe.losjardines.usecases.model

data class AuthResponseDto(
    val uid: String,
    val isEmailVerified: Boolean,
    val providerId: String
)
