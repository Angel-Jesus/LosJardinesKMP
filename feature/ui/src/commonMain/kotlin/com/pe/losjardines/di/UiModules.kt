package com.pe.losjardines.di

import com.pe.losjardines.presentation.content.consultation.viewmodel.ConsultationViewModel
import com.pe.losjardines.presentation.content.home.viewmodel.HomeViewModel
import com.pe.losjardines.presentation.content.registration.viewmodel.RegistrationViewModel
import com.pe.losjardines.presentation.content.room.viewmodel.RoomViewModel
import com.pe.losjardines.presentation.content.utils.ExcelTemplateProvider
import com.pe.losjardines.presentation.login.viewmodel.LoginViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val uiModules = module{
    viewModelOf(::LoginViewModel)
    viewModelOf(::RegistrationViewModel)
    viewModelOf(::ConsultationViewModel)
    viewModelOf(::RoomViewModel)
    viewModelOf(::HomeViewModel)

    single { ExcelTemplateProvider() }
}