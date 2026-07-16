package com.pe.losjardines.presentation.login.contract

import com.pe.losjardines.base.ui.BaseEffect

sealed class LoginEffect: BaseEffect{
    data object LoginSuccess: LoginEffect()
}
