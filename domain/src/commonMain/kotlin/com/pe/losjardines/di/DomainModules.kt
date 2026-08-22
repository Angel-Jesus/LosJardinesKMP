package com.pe.losjardines.di

import com.pe.losjardines.usecases.content.DeleteClientUseCase
import com.pe.losjardines.usecases.content.GenerateExcelReportUseCase
import com.pe.losjardines.usecases.content.GetClientsRegisterUseCase
import com.pe.losjardines.usecases.catalog.GetCountriesUseCase
import com.pe.losjardines.usecases.catalog.GetReasonTravelsUseCase
import com.pe.losjardines.usecases.catalog.GetRegionsUseCase
import com.pe.losjardines.usecases.catalog.GetTypeRoomUseCase
import com.pe.losjardines.usecases.content.CheckInReservationUseCase
import com.pe.losjardines.usecases.content.DeleteReservationUseCase
import com.pe.losjardines.usecases.content.GetReservationByIdUseCase
import com.pe.losjardines.usecases.content.GetReservationsUseCase
import com.pe.losjardines.usecases.content.GetRoomStateUseCase
import com.pe.losjardines.usecases.content.SaveCustomerRegistrationUseCase
import com.pe.losjardines.usecases.content.SaveReservationUseCase
import com.pe.losjardines.usecases.content.SyncronizationUseCase
import com.pe.losjardines.usecases.content.ValidateRoomAvailabilityUseCase
import com.pe.losjardines.usecases.content.UpdateClientInfoUseCase
import com.pe.losjardines.usecases.content.UpdateRegisterByCloudUseCase
import com.pe.losjardines.usecases.content.UpdateStateRoomUseCase
import com.pe.losjardines.usecases.login.CheckSessionUseCase
import com.pe.losjardines.usecases.login.LoginUseCase
import com.pe.losjardines.usecases.login.LogoutUseCase
import com.pe.losjardines.utils.files.ExcelEditor
import com.pe.losjardines.utils.files.ExcelGenerator
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
    factoryOf(::DeleteClientUseCase)
    factoryOf(::SaveReservationUseCase)
    factoryOf(::GetReservationsUseCase)
    factoryOf(::DeleteReservationUseCase)
    factoryOf(::CheckInReservationUseCase)
    factoryOf(::GetReservationByIdUseCase)
    factoryOf(::ValidateRoomAvailabilityUseCase)
    factoryOf(::GenerateExcelReportUseCase)
    factoryOf(::UpdateRegisterByCloudUseCase)

    single<ExcelGenerator> { ExcelEditor() }
}