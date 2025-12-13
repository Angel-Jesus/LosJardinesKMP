package com.pe.losjardines.presentation.contract

import com.pe.losjardines.base.ui.BaseEffect

sealed class LoginEffect: BaseEffect{
    data object LoginSuccess: LoginEffect()
    data object LoginError: LoginEffect()
}
