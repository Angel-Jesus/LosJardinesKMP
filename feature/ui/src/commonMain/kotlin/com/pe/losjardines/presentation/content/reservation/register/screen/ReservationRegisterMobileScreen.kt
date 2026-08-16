package com.pe.losjardines.presentation.content.reservation.register.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pe.losjardines.components.buttom.ButtonAJ
import com.pe.losjardines.components.dialog.ResultDialog
import com.pe.losjardines.components.dropdown.DropDownAJ
import com.pe.losjardines.components.loading.LoadingAJ
import com.pe.losjardines.components.picker.NativeDatePicker
import com.pe.losjardines.components.textInput.InputType
import com.pe.losjardines.components.textInput.TextInputAJ
import com.pe.losjardines.presentation.components.HeaderComponent
import com.pe.losjardines.presentation.content.reservation.register.contract.ReservationRegisterEffect
import com.pe.losjardines.presentation.content.reservation.register.contract.ReservationRegisterEvent
import com.pe.losjardines.presentation.content.reservation.register.viewmodel.ReservationRegisterViewModel
import com.pe.losjardines.presentation.content.utils.DocumentType
import com.pe.losjardines.presentation.content.utils.FieldRegistration
import com.pe.losjardines.presentation.content.utils.ResultState
import com.pe.losjardines.presentation.content.utils.Sex
import com.pe.losjardines.utils.companions.EMPTY
import com.pe.losjardines.utils.getDayNow
import com.pe.losjardines.utils.toDateStringResult
import com.pe.losjardines.values.AppTypography
import com.pe.losjardines.values.LocalAppTypographyCore
import kotlinx.coroutines.flow.collectLatest
import losjardineskmp.feature.ui.generated.resources.Res
import losjardineskmp.feature.ui.generated.resources.check_in_date_input
import losjardineskmp.feature.ui.generated.resources.check_out_date_input
import losjardineskmp.feature.ui.generated.resources.country_of_residence_input
import losjardineskmp.feature.ui.generated.resources.document_number_input
import losjardineskmp.feature.ui.generated.resources.document_type_input
import losjardineskmp.feature.ui.generated.resources.fullname_input
import losjardineskmp.feature.ui.generated.resources.observation_input
import losjardineskmp.feature.ui.generated.resources.rate_input
import losjardineskmp.feature.ui.generated.resources.region_of_residence_input
import losjardineskmp.feature.ui.generated.resources.registration_personal_information_title
import losjardineskmp.feature.ui.generated.resources.room_input
import losjardineskmp.feature.ui.generated.resources.sex_input
import losjardineskmp.feature.ui.generated.resources.stay_detail_information_title
import losjardineskmp.feature.ui.generated.resources.type_room_input
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
fun ReservationRegisterMobileScreen(
    title: String,
    onBack: () -> Unit = {},
    viewModel: ReservationRegisterViewModel = koinViewModel(),
    typography: AppTypography = LocalAppTypographyCore.current
){
    var showDatePicker by rememberSaveable { mutableStateOf<Pair<FieldRegistration?, Boolean>>(Pair(null, false)) }
    var isLoading by rememberSaveable{ mutableStateOf(false) }

    val countryCatalog by viewModel.countryCatalog.collectAsState()
    val regionCatalog by viewModel.regionCatalog.collectAsState()
    val typeRoomsCatalog by viewModel.typeRoomsCatalog.collectAsState()

    val fullName by viewModel.fullName.collectAsState()
    val sex by viewModel.sex.collectAsState()
    val country by viewModel.country.collectAsState()
    val region by viewModel.region.collectAsState()
    val documentType by viewModel.documentType.collectAsState(DocumentType.DNI.type)
    val documentNumber by viewModel.documentNumber.collectAsState()
    val checkInDate by viewModel.checkInDate.collectAsState()
    val checkOutDate by viewModel.checkOutDate.collectAsState()
    val typeRoom by viewModel.typeRoom.collectAsState()
    val room by viewModel.room.collectAsState()
    val rate by viewModel.fee.collectAsState()
    val observation by viewModel.observation.collectAsState()

    var showResult by rememberSaveable { mutableStateOf(Pair(ResultState.NONE, String.EMPTY)) }

    val enableButton by derivedStateOf {
        fullName.text.isNotEmpty() &&
        sex.isNotEmpty() &&
        country.isNotEmpty() &&
        validateRegion(country, region) &&
        documentType.isNotEmpty() &&
        documentNumber.text.isNotEmpty() &&
        checkInDate.isNotEmpty() &&
        room.text.isNotEmpty() &&
        rate.text.isNotEmpty()
    }


    LaunchedEffect(true){
        viewModel.onEvent(ReservationRegisterEvent.GetCatalogInformation)

        viewModel.effect.collectLatest { effect ->
            isLoading = false

            showResult = when(effect){
                is ReservationRegisterEffect.ErrorSave -> {
                    Pair(ResultState.ERROR, effect.message)
                }

                is ReservationRegisterEffect.SuccessSave -> {
                    Pair(ResultState.SUCCESS, effect.message.orEmpty())
                }
            }
        }
    }

    if(showDatePicker.second){
        NativeDatePicker(
            initialDate = getDayNow(),
            onDismiss = {
                showDatePicker = Pair(null, false)
            },
            onDateSelected = {
                when (showDatePicker.first) {
                    FieldRegistration.CHECK_IN_DATE -> {
                        viewModel.onEvent(ReservationRegisterEvent.ValueChanged(it.toDateStringResult(), FieldRegistration.CHECK_IN_DATE))
                    }
                    FieldRegistration.CHECK_OUT_DATE -> {
                        viewModel.onEvent(ReservationRegisterEvent.ValueChanged(it.toDateStringResult(), FieldRegistration.CHECK_OUT_DATE))
                    }
                    else -> {
                        println("Error: Field not found")
                    }
                }
            }
        )
    }

    ResultDialog(
        modifier = Modifier,
        title = "Proceso de la reserva",
        description = showResult.second,
        isSuccess = showResult.first == ResultState.SUCCESS,
        visibility = showResult.first != ResultState.NONE,
        onDismiss = {
            showResult = Pair(ResultState.NONE, String.EMPTY)
        }
    )

    if(isLoading){
        LoadingAJ(
            title = "Información de la reserva",
            subtitle = "Enviando y guardando"
        )
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize().systemBarsPadding().padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            HeaderComponent(
                modifier = Modifier.fillMaxWidth().height(48.dp),
                title = title,
                backEnabled = true,
                onBack = onBack
            )
        }

        item {
            Text(
                text = stringResource(Res.string.registration_personal_information_title),
                style = typography.headerSmall
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        item {
            TextInputAJ(
                modifier = Modifier.fillMaxWidth(),
                value = fullName,
                onValueChange = {
                    viewModel.onEvent(ReservationRegisterEvent.ValueChanged(it, FieldRegistration.FULL_NAME))
                },
                inputType = InputType.TEXT_SPECIAL,
                label = stringResource(Res.string.fullname_input)
            )
        }

        item {
            DropDownAJ(
                modifier = Modifier.fillMaxWidth(),
                label = stringResource(Res.string.sex_input),
                options = Sex.options,
                selectedOption = sex,
                onOptionSelected = {
                    viewModel.onEvent(ReservationRegisterEvent.ValueChanged(it, FieldRegistration.SEX))
                }
            )
        }

        item {
            DropDownAJ(
                modifier = Modifier.fillMaxWidth(),
                label = stringResource(Res.string.country_of_residence_input),
                options = countryCatalog.map { it.name },
                selectedOption = country,
                onOptionSelected = {
                    viewModel.onEvent(ReservationRegisterEvent.ValueChanged(it, FieldRegistration.COUNTRY_OF_RESIDENCE))
                },
                enabled = countryCatalog.isNotEmpty()
            )
        }

        item {
            DropDownAJ(
                modifier = Modifier.fillMaxWidth(),
                label = stringResource(Res.string.region_of_residence_input),
                options = regionCatalog.map { it.name },
                selectedOption = region,
                onOptionSelected = {
                    viewModel.onEvent(ReservationRegisterEvent.ValueChanged(it, FieldRegistration.REGION_OF_RESIDENCE))
                },
                enabled = regionCatalog.isNotEmpty()
            )
        }

        item {
            DropDownAJ(
                modifier = Modifier.fillMaxWidth(),
                label = stringResource(Res.string.document_type_input),
                options = DocumentType.options,
                selectedOption = documentType,
                onOptionSelected = {
                    viewModel.onEvent(ReservationRegisterEvent.ValueChanged(it, FieldRegistration.DOCUMENT_TYPE))
                }
            )
        }

        item {
            TextInputAJ(
                modifier = Modifier.fillMaxWidth(),
                value = documentNumber,
                onValueChange = {
                    viewModel.onEvent(ReservationRegisterEvent.ValueChanged(it, FieldRegistration.DOCUMENT_NUMBER))
                },
                inputType = InputType.DOCUMENT,
                label = stringResource(Res.string.document_number_input)
            )
        }

        item {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(Res.string.stay_detail_information_title),
                style = typography.headerSmall
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        item {
            TextInputAJ(
                modifier = Modifier.fillMaxWidth(),
                value = checkInDate,
                readOnly = true,
                enableClickable = true,
                onClick = {
                    showDatePicker = Pair(FieldRegistration.CHECK_IN_DATE, true)
                },
                label = stringResource(Res.string.check_in_date_input)
            )
        }

        item {
            TextInputAJ(
                modifier = Modifier.fillMaxWidth(),
                value = checkOutDate,
                readOnly = true,
                enableClickable = true,
                onClick = {
                    showDatePicker = Pair(FieldRegistration.CHECK_OUT_DATE, true)
                },
                label = stringResource(Res.string.check_out_date_input)
            )
        }

        item {
            DropDownAJ(
                modifier = Modifier.fillMaxWidth(),
                label = stringResource(Res.string.type_room_input),
                options = typeRoomsCatalog.map { it.description },
                selectedOption = typeRoom,
                onOptionSelected = {
                    viewModel.onEvent(ReservationRegisterEvent.ValueChanged(it, FieldRegistration.TYPE_ROOM))
                }
            )
        }

        item {
            TextInputAJ(
                modifier = Modifier.fillMaxWidth(),
                value = room,
                onValueChange = {
                    viewModel.onEvent(ReservationRegisterEvent.ValueChanged(it, FieldRegistration.ROOM))
                },
                inputType = InputType.NUMBER,
                label = stringResource(Res.string.room_input)
            )
        }

        item {
            TextInputAJ(
                modifier = Modifier.fillMaxWidth(),
                value = rate,
                onValueChange = {
                    viewModel.onEvent(ReservationRegisterEvent.ValueChanged(it, FieldRegistration.RATE))
                },
                inputType = InputType.DECIMAL,
                label = stringResource(Res.string.rate_input)
            )
        }

        item {
            TextInputAJ(
                modifier = Modifier.fillMaxWidth(),
                value = observation,
                onValueChange = {
                    viewModel.onEvent(ReservationRegisterEvent.ValueChanged(it, FieldRegistration.OBSERVATION))
                },
                label = stringResource(Res.string.observation_input)
            )
        }

        item{
            Box(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)){
                ButtonAJ(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Guardar",
                    enabled = enableButton,
                    onClick = {
                        isLoading = true
                        viewModel.onEvent(ReservationRegisterEvent.SaveData)
                    }
                )
            }
        }
    }
}

private fun validateRegion(country: String, region: String): Boolean{
    if(country != "Perú") return true
    return region.isNotEmpty()
}
