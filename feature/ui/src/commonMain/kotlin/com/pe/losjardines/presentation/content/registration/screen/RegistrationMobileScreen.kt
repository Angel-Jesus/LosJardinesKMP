package com.pe.losjardines.presentation.content.registration.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.pe.losjardines.components.dropdown.DropDownAJ
import com.pe.losjardines.components.picker.nativeDatePicker
import com.pe.losjardines.components.textInput.TextInputAJ
import com.pe.losjardines.presentation.content.registration.contract.RegistrationEvent
import com.pe.losjardines.presentation.content.registration.viewmodel.RegistrationViewModel
import com.pe.losjardines.presentation.content.utils.DOCUMENT_TYPE
import com.pe.losjardines.presentation.content.utils.FieldRegistration
import com.pe.losjardines.presentation.content.utils.SEX
import com.pe.losjardines.utils.getDayNow
import com.pe.losjardines.values.AppTypography
import com.pe.losjardines.values.LocalAppTypographyCore
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
import losjardineskmp.feature.ui.generated.resources.travel_reason_input
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
fun RegistrationMobileScreen(
    viewModel: RegistrationViewModel = koinViewModel(),
    typography: AppTypography = LocalAppTypographyCore.current
){
    var showDatePicker by remember { mutableStateOf<Pair<FieldRegistration?, Boolean>>(Pair(null, false)) }

    val countryCatalog by viewModel.countryCatalog.collectAsState()
    val regionCatalog by viewModel.regionCatalog.collectAsState()
    val travelReasonCatalog by viewModel.reasonTravelCatalog.collectAsState()

    val fullName by viewModel.fullName.collectAsState()
    val sex by viewModel.sex.collectAsState()
    val country by viewModel.country.collectAsState()
    val region by viewModel.region.collectAsState()
    val travelReason by viewModel.travelReason.collectAsState()
    val documentType by viewModel.documentType.collectAsState(DOCUMENT_TYPE.DNI.type)
    val documentNumber by viewModel.documentNumber.collectAsState()
    val checkInDate by viewModel.checkInDate.collectAsState()
    val checkOutDate by viewModel.checkOutDate.collectAsState()
    val room by viewModel.room.collectAsState()
    val rate by viewModel.rate.collectAsState()
    val observation by viewModel.observation.collectAsState()

    LaunchedEffect(true){
        viewModel.onEvent(RegistrationEvent.GetCatalogInformation)
    }

    if(showDatePicker.second){
        nativeDatePicker(
            initialDate = getDayNow(),
            onDismiss = {
                showDatePicker = Pair(null, false)
            },
            onDateSelected = {
                when (showDatePicker.first) {
                    FieldRegistration.CHECK_IN_DATE -> {

                    }
                    FieldRegistration.CHECK_OUT_DATE -> {

                    }
                    else -> {
                        println("Error: Field not found")
                    }
                }
            }
        )
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
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
                    viewModel.onEvent(RegistrationEvent.ValueChanged(it, FieldRegistration.FULL_NAME))
                },
                label = stringResource(Res.string.fullname_input)
            )
        }

        item {
            DropDownAJ(
                modifier = Modifier.fillMaxWidth(),
                label = stringResource(Res.string.sex_input),
                options = SEX.options,
                selectedOption = sex,
                onOptionSelected = {
                    viewModel.onEvent(RegistrationEvent.ValueChanged(it, FieldRegistration.SEX))
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
                    viewModel.onEvent(RegistrationEvent.ValueChanged(it, FieldRegistration.COUNTRY_OF_RESIDENCE))
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
                    viewModel.onEvent(RegistrationEvent.ValueChanged(it, FieldRegistration.REGION_OF_RESIDENCE))
                },
                enabled = regionCatalog.isNotEmpty()
            )
        }

        item {
            DropDownAJ(
                modifier = Modifier.fillMaxWidth(),
                label = stringResource(Res.string.travel_reason_input),
                options = travelReasonCatalog.map { it.description },
                selectedOption = travelReason,
                onOptionSelected = {
                    viewModel.onEvent(RegistrationEvent.ValueChanged(it, FieldRegistration.TRAVEL_REASON))
                },
                enabled = travelReasonCatalog.isNotEmpty()
            )
        }

        item {
            DropDownAJ(
                modifier = Modifier.fillMaxWidth(),
                label = stringResource(Res.string.document_type_input),
                options = DOCUMENT_TYPE.options,
                selectedOption = documentType,
                onOptionSelected = {
                    viewModel.onEvent(RegistrationEvent.ValueChanged(it, FieldRegistration.DOCUMENT_TYPE))
                }
            )
        }

        item {
            TextInputAJ(
                modifier = Modifier.fillMaxWidth(),
                value = documentNumber,
                onValueChange = {
                    viewModel.onEvent(RegistrationEvent.ValueChanged(it, FieldRegistration.DOCUMENT_NUMBER))
                },
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
            TextInputAJ(
                modifier = Modifier.fillMaxWidth(),
                value = room,
                onValueChange = {
                    viewModel.onEvent(RegistrationEvent.ValueChanged(it, FieldRegistration.ROOM))
                },
                keyboardType = KeyboardType.Number,
                label = stringResource(Res.string.room_input)
            )
        }

        item {
            TextInputAJ(
                modifier = Modifier.fillMaxWidth(),
                value = rate,
                onValueChange = {
                    viewModel.onEvent(RegistrationEvent.ValueChanged(it, FieldRegistration.RATE))
                },
                keyboardType = KeyboardType.Number,
                label = stringResource(Res.string.rate_input)
            )
        }

        item {
            TextInputAJ(
                modifier = Modifier.fillMaxWidth(),
                value = observation,
                onValueChange = {
                    viewModel.onEvent(RegistrationEvent.ValueChanged(it, FieldRegistration.OBSERVATION))
                },
                label = stringResource(Res.string.observation_input)
            )
        }
    }
}