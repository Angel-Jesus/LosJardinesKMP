package com.pe.losjardines.presentation.contract

import com.pe.losjardines.base.ui.BaseEvent

sealed class LoginEvent: BaseEvent{
    data object CheckSession: LoginEvent()
    data class EnterLogin(val email: String, val password: String): LoginEvent()
}
