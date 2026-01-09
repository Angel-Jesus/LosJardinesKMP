package com.pe.losjardines.di

import com.pe.losjardines.presentation.content.registration.viewmodel.RegistrationViewModel
import com.pe.losjardines.presentation.login.viewmodel.LoginViewModel
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val uiModules = module{
    viewModelOf(::LoginViewModel)
    viewModelOf(::RegistrationViewModel)
}