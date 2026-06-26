package com.pe.losjardines.di

import com.pe.losjardines.usecases.content.GenerateExcelReportUseCase
import com.pe.losjardines.usecases.content.GetClientsRegisterUseCase
import com.pe.losjardines.usecases.catalog.GetCountriesUseCase
import com.pe.losjardines.usecases.catalog.GetReasonTravelsUseCase
import com.pe.losjardines.usecases.catalog.GetRegionsUseCase
import com.pe.losjardines.usecases.catalog.GetTypeRoomUseCase
import com.pe.losjardines.usecases.content.GetRoomStateUseCase
import com.pe.losjardines.usecases.content.SaveCustomerRegistrationUseCase
import com.pe.losjardines.usecases.content.SyncronizationUseCase
import com.pe.losjardines.usecases.content.UpdateClientInfoUseCase
import com.pe.losjardines.usecases.content.UpdateStateRoomUseCase
import com.pe.losjardines.usecases.login.CheckSessionUseCase
import com.pe.losjardines.usecases.login.LoginUseCase
import com.pe.losjardines.usecases.login.LogoutUseCase
import com.pe.losjardines.utils.files.ExcelEditor
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
    factoryOf(::GetTypeRoomUseCase)
    factoryOf(::SaveCustomerRegistrationUseCase)
    factoryOf(::GetClientsRegisterUseCase)
    factoryOf(::GetRoomStateUseCase)
    factoryOf(::UpdateStateRoomUseCase)
    factoryOf(::UpdateClientInfoUseCase)
    factoryOf(::GenerateExcelReportUseCase)

    single { ExcelEditor() }
}