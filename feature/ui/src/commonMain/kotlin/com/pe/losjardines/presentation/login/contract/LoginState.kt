package com.pe.losjardines.presentation.login.contract

import com.pe.losjardines.base.ui.BaseUiState
import com.pe.losjardines.utils.companions.EMPTY

data class LoginState(
    val login: Boolean = false,
    val email: String = String.EMPTY,
    val password: String = String.EMPTY,
    val messageError: String = String.EMPTY
): BaseUiState
