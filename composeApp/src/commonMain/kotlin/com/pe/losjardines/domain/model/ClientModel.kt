package com.pe.losjardines.domain.model

import com.pe.losjardines.utils.companions.EMPTY
import com.pe.losjardines.utils.companions.EMPTY_ID

data class ClientModel (
    val id: Int = Int.EMPTY_ID,
    val room: String = String.EMPTY,
    val date: String = String.EMPTY,
    val time: String = String.EMPTY,
    val name: String = String.EMPTY,
    val dni: String = String.EMPTY,
    val price: String = String.EMPTY,
    val origin: String = String.EMPTY,
    val observation: String = String.EMPTY
)