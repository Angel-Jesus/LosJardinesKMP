package com.pe.losjardines.domain.model

import com.pe.losjardines.utils.EMPTY

sealed class ClientFilter {
    data object None: ClientFilter()
    data class Month(val month: String): ClientFilter()
    data class Origin(val origin: String, val month: String = String.EMPTY): ClientFilter()
    data class DNI(val dni: String, val month: String = String.EMPTY): ClientFilter()
}