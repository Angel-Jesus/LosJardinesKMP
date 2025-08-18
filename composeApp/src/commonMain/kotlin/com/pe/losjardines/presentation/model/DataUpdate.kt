package com.pe.losjardines.presentation.model

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import com.pe.losjardines.domain.model.ClientModel
import com.pe.losjardines.presentation.enums.TitleClient
import com.pe.losjardines.utils.companions.EMPTY

data class DataUpdate(
    val type: TitleClient = TitleClient.ROOM,
    val keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
    val value: String = String.EMPTY,
    val userModel: ClientModel? = null
)
