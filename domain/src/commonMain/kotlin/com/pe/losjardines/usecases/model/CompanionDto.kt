package com.pe.losjardines.usecases.model

data class CompanionDto(
    val name: String,
    val country: String,
    val sex: String,
    val typeDocument: String,
    val numberDocument: String,
    val observation: String
)
