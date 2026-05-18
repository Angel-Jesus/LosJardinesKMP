package com.pe.losjardines.usecases.model

data class FilterValues(
    val month: String,
    val year: String,
    val searchDni: String? = null
)
