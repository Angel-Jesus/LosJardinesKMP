package com.pe.losjardines.di

import com.pe.losjardines.usecases.content.GetCountriesUseCase
import com.pe.losjardines.usecases.content.GetReasonTravelsUseCase
import com.pe.losjardines.usecases.content.GetRegionsUseCase
import com.pe.losjardines.usecases.content.SyncronizationUseCase
import com.pe.losjardines.usecases.login.CheckSessionUseCase
import com.pe.losjardines.usecases.login.LoginUseCase
import com.pe.losjardines.usecases.login.LogoutUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val domainModules = module {
    factoryOf(::CheckSessionUseCase)
    factoryOf(::LoginUseCase)
    factoryOf(::LogoutUseCase)
    factoryOf(::SyncronizationUseCase)
    factoryOf(::GetCountriesUseCase)
    factoryOf(::GetRegionsUseCase)
    factoryOf(::GetReasonTravelsUseCase)
}