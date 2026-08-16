package com.pe.losjardines.presentation.content.consultation.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowCircleDown
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import com.pe.losjardines.components.buttom.ButtonAJ
import com.pe.losjardines.components.buttom.IconButtonAJ
import com.pe.losjardines.components.dialog.FieldUpdateDialog
import com.pe.losjardines.components.dialog.ResultActionDialog
import com.pe.losjardines.components.dialog.ResultDialog
import com.pe.losjardines.components.dialog.TypeField
import com.pe.losjardines.components.dropdown.DropDownAJ
import com.pe.losjardines.components.empty_state.EmptyStateAJ
import com.pe.losjardines.components.loading.LoadingAJ
import com.pe.losjardines.components.textInput.InputType
import com.pe.losjardines.components.textInput.TextInputAJ
import com.pe.losjardines.presentation.components.HeaderComponent
import com.pe.losjardines.presentation.components.table.TableColumn
import com.pe.losjardines.presentation.components.table.registrationColumn
import com.pe.losjardines.presentation.components.table.tableSection
import com.pe.losjardines.presentation.components.table.updateFieldParamsOf
import com.pe.losjardines.presentation.content.consultation.contract.ConsultationEvent
import com.pe.losjardines.presentation.content.consultation.contract.ConsultationState
import com.pe.losjardines.presentation.components.table.UpdateFieldParams
import com.pe.losjardines.presentation.content.consultation.viewmodel.ConsultationViewModel
import com.pe.losjardines.presentation.content.utils.DocumentType
import com.pe.losjardines.presentation.content.utils.FieldFilter
import com.pe.losjardines.presentation.content.utils.FieldRegistration
import com.pe.losjardines.presentation.content.utils.Sex
import com.pe.losjardines.usecases.model.RegistrationDto
import com.pe.losjardines.utils.companions.EMPTY
import com.pe.losjardines.utils.constance.MonthFilter
import com.pe.losjardines.values.AppTheme
import com.pe.losjardines.values.AppTypography
import com.pe.losjardines.values.BackgroundBrandColor
import com.pe.losjardines.values.DividerColor
import com.pe.losjardines.values.LocalAppTypographyCore
import losjardineskmp.feature.ui.generated.resources.Res
import losjardineskmp.feature.ui.generated.resources.description_not_register_found
import losjardineskmp.feature.ui.generated.resources.filter_button
import losjardineskmp.feature.ui.generated.resources.filter_title
import losjardineskmp.feature.ui.generated.resources.ic_not_found
import losjardineskmp.feature.ui.generated.resources.month_filter_title
import losjardineskmp.feature.ui.generated.resources.search_dni_input
import losjardineskmp.feature.ui.generated.resources.title_not_register_found
import losjardineskmp.feature.ui.generated.resources.year_filter_title
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ConsultationMobileScreen(
    title: String,
    viewModel: ConsultationViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val catalog by viewModel.catalogState.collectAsState()

    LifecycleEventEffect(Lifecycle.Event.ON_RESUME){
        viewModel.onEvent(ConsultationEvent.GetClientsRegister())
    }

    if(uiState.loading){
        LoadingAJ(
            title = "Información del cliente",
            subtitle = "Obteniendo información"
        )
    }

    ResultDialog(
        modifier = Modifier,
        title = "Sucedió un inconveniente",
        description = uiState.errorMessage,
        isSuccess = false,
        visibility = uiState.errorMessage != null,
        onDismiss = {
            viewModel.hideErrorMessage()
        }
    )

    ConsultationContent(
        dispatcherEvent = viewModel::onEvent,
        title = title,
        uiState = uiState,
        catalog = catalog
    )

}

@Composable
private fun ConsultationContent(
    dispatcherEvent: (ConsultationEvent) -> Unit,
    title: String,
    uiState: ConsultationState,
    catalog: ConsultationViewModel.CatalogState,
    typography: AppTypography = LocalAppTypographyCore.current
) {
    val sharedScrollState = rememberScrollState()
    var isExpanded by rememberSaveable { mutableStateOf(false) }
    var updateFieldParams by remember { mutableStateOf(UpdateFieldParams()) }
    var deleteParams by remember { mutableStateOf(Triple(false, 0L, String.EMPTY)) }

    val rotationState by animateFloatAsState(
        targetValue = if (isExpanded) 180f else 0f,
        animationSpec = tween(durationMillis = 300),
        label = "rotation"
    )

    if(deleteParams.first){
        ResultActionDialog(
            modifier = Modifier,
            title = "Eliminar registro",
            description = "Esta por eliminar el registro. ¿Desea continuar?",
            primaryButtonText = "Eliminar",
            secondaryButtonText = "Cancelar",
            onPrimaryClick = {
                dispatcherEvent(ConsultationEvent.DeleteClientInformation(deleteParams.second, deleteParams.third))
                deleteParams = Triple(false, 0L, String.EMPTY)
            },
            onSecondaryClick = {
                deleteParams = Triple(false, 0L, String.EMPTY)
            },
            onDismiss = {
                deleteParams = Triple(false, 0L, String.EMPTY)
            }
        )
    }

    if (updateFieldParams.show) {
        FieldUpdateDialog(
            title = updateFieldParams.title,
            descriptiion = updateFieldParams.description,
            value = updateFieldParams.value,
            typeField = updateFieldParams.typeField,
            listOption = updateFieldParams.listOption,
            aceptedEmpty = updateFieldParams.field == FieldRegistration.OBSERVATION,
            onDismiss = {
                updateFieldParams = UpdateFieldParams()
            },
            onConfirm = { value ->
                dispatcherEvent(
                    ConsultationEvent.UpdateClientInformation(
                        value,
                        updateFieldParams
                    )
                )
                updateFieldParams = UpdateFieldParams()
            }
        )
    }

    val columns = registrationColumns(catalog)

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)
    ) {
        item {
            HeaderComponent(
                modifier = Modifier.fillMaxWidth().height(48.dp),
                title = title
            )
        }

        item {
            FilterSectionComponent(
                typography = typography,
                isExpanded = isExpanded,
                rotationState = rotationState,
                onChangeExpanded = { isExpanded = it },
                onChangeValue = { value, field ->
                    dispatcherEvent(ConsultationEvent.ValueChanged(value, field))
                },
                onFilterClick = {
                    dispatcherEvent(ConsultationEvent.Filter)
                },
                onClearFilterClick = {
                    dispatcherEvent(ConsultationEvent.ClearFilter)
                },
                uiState = uiState
            )
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

        tableSection(
            columns = columns,
            rows = uiState.clientsRegister,
            rowKey = { it.id ?: 0L },
            scrollState = sharedScrollState,
            actionWidth = ACTION_COLUMN_WIDTH,
            onUpdateClick = { updateFieldParams = it },
            emptyContent = {
                EmptyStateAJ(
                    modifier = Modifier.fillMaxWidth(),
                    image = Res.drawable.ic_not_found,
                    title = stringResource(Res.string.title_not_register_found),
                    description = stringResource(Res.string.description_not_register_found)
                )
            },
            rowAction = { client ->
                Icon(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .clickable(
                            onClick = { deleteParams = Triple(true, client.id ?: 0L, client.idFirebase) }
                        ),
                    imageVector = Icons.Default.DeleteOutline,
                    contentDescription = null,
                    tint = BackgroundBrandColor
                )
            }
        )
    }
}

private fun registrationColumns(
    catalog: ConsultationViewModel.CatalogState
): List<TableColumn<RegistrationDto>> = listOf(
    registrationColumn(
        field = FieldRegistration.ROOM,
        value = { it.room },
        update = {
            updateFieldParamsOf(FieldRegistration.ROOM, it.id ?: 0L, it.collection, it.idFirebase, it.room, TypeField.NUMBER)
        }
    ),
    registrationColumn(
        field = FieldRegistration.FULL_NAME,
        value = { it.name },
        update = {
            updateFieldParamsOf(FieldRegistration.FULL_NAME, it.id ?: 0L, it.collection, it.idFirebase, it.name, TypeField.TEXT_SPECIAL)
        }
    ),
    registrationColumn(
        field = FieldRegistration.SEX,
        value = { Sex.getAbbreviations(it.sex) },
        update = {
            updateFieldParamsOf(FieldRegistration.SEX, it.id ?: 0L, it.collection, it.idFirebase, it.sex, TypeField.DROP_DOWN, Sex.options)
        }
    ),
    registrationColumn(
        field = FieldRegistration.CHECK_IN_DATE,
        value = { it.dateEnter },
        update = {
            updateFieldParamsOf(FieldRegistration.CHECK_IN_DATE, it.id ?: 0L, it.collection, it.idFirebase, it.dateEnter, TypeField.DATE_PICKER)
        }
    ),
    registrationColumn(
        field = FieldRegistration.CHECK_OUT_DATE,
        value = { it.dateExit },
        update = {
            updateFieldParamsOf(FieldRegistration.CHECK_OUT_DATE, it.id ?: 0L, it.collection, it.idFirebase, it.dateExit, TypeField.DATE_PICKER)
        }
    ),
    registrationColumn(
        field = FieldRegistration.DOCUMENT_TYPE,
        value = { it.typeDocument },
        update = {
            updateFieldParamsOf(FieldRegistration.DOCUMENT_TYPE, it.id ?: 0L, it.collection, it.idFirebase, it.typeDocument, TypeField.DROP_DOWN, DocumentType.options)
        }
    ),
    registrationColumn(
        field = FieldRegistration.DOCUMENT_NUMBER,
        value = { it.numberDocument },
        update = {
            updateFieldParamsOf(FieldRegistration.DOCUMENT_NUMBER, it.id ?: 0L, it.collection, it.idFirebase, it.numberDocument, TypeField.DOCUMENT)
        }
    ),
    registrationColumn(
        field = FieldRegistration.COUNTRY_OF_RESIDENCE,
        value = { it.country },
        update = {
            updateFieldParamsOf(FieldRegistration.COUNTRY_OF_RESIDENCE, it.id ?: 0L, it.collection, it.idFirebase, it.country, TypeField.DROP_DOWN, catalog.countries.map { country -> country.name })
        }
    ),
    registrationColumn(
        field = FieldRegistration.REGION_OF_RESIDENCE,
        value = { it.region },
        enabled = { it.region.isNotEmpty() },
        update = {
            updateFieldParamsOf(FieldRegistration.REGION_OF_RESIDENCE, it.id ?: 0L, it.collection, it.idFirebase, it.region, TypeField.DROP_DOWN, catalog.regions.map { region -> region.name })
        }
    ),
    registrationColumn(
        field = FieldRegistration.TRAVEL_REASON,
        value = { it.reasonTravel },
        update = {
            updateFieldParamsOf(FieldRegistration.TRAVEL_REASON, it.id ?: 0L, it.collection, it.idFirebase, it.reasonTravel, TypeField.DROP_DOWN, catalog.travelReasons.map { reason -> reason.description })
        }
    ),
    registrationColumn(
        field = FieldRegistration.RATE,
        value = { "S/ ${it.fee}" },
        update = {
            updateFieldParamsOf(FieldRegistration.RATE, it.id ?: 0L, it.collection, it.idFirebase, it.fee, TypeField.NUMBER)
        }
    ),
    registrationColumn(
        field = FieldRegistration.OBSERVATION,
        value = { it.observation },
        update = {
            updateFieldParamsOf(FieldRegistration.OBSERVATION, it.id ?: 0L, it.collection, it.idFirebase, it.observation, TypeField.TEXT)
        }
    )
)

@Composable
private fun FilterSectionComponent(
    typography: AppTypography,
    isExpanded: Boolean,
    rotationState: Float,
    onChangeExpanded: (Boolean) -> Unit,
    uiState: ConsultationState,
    onChangeValue: (String, FieldFilter) -> Unit = { _, _ -> },
    onFilterClick: () -> Unit = {},
    onClearFilterClick: () -> Unit = {}
) {
    Column(modifier = Modifier.padding(bottom = 8.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = stringResource(Res.string.filter_title),
                style = typography.titleMedium
            )

            Spacer(modifier = Modifier.weight(1f))

            IconButton(
                onClick = { onChangeExpanded(!isExpanded) }
            ) {
                Icon(
                    modifier = Modifier.rotate(rotationState),
                    imageVector = Icons.Default.ArrowCircleDown,
                    contentDescription = null
                )
            }
        }
        AnimatedVisibility(
            visible = isExpanded,
            enter = expandVertically() + fadeIn(),
            exit = shrinkVertically() + fadeOut()
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                HorizontalDivider(modifier = Modifier.fillMaxWidth(), color = DividerColor)

                Row {
                    DropDownAJ(
                        modifier = Modifier.fillMaxWidth(0.5f),
                        label = stringResource(Res.string.month_filter_title),
                        options = MonthFilter.entries.toList().map { it.displayName },
                        selectedOption = uiState.monthFilter,
                        onOptionSelected = {
                            onChangeValue(it, FieldFilter.MONTH_FILTER)
                        }
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    TextInputAJ(
                        modifier = Modifier.fillMaxWidth(),
                        value = uiState.yearFilter,
                        onValueChange = {
                            onChangeValue(it, FieldFilter.YEAR_FILTER)
                        },
                        inputType = InputType.YEAR,
                        label = stringResource(Res.string.year_filter_title)
                    )
                }

                TextInputAJ(
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                    value = uiState.searchDni,
                    onValueChange = {
                        onChangeValue(it, FieldFilter.SEARCH_DNI_FILTER)
                    },
                    leadingIcon = Icons.Default.Search,
                    inputType = InputType.NUMBER,
                    placeholder = stringResource(Res.string.search_dni_input)
                )

                Row(modifier = Modifier.padding(top = 8.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    ButtonAJ(
                        modifier = Modifier.weight(1f),
                        text = stringResource(Res.string.filter_button),
                        onClick = onFilterClick
                    )

                    AnimatedVisibility(
                        visible = uiState.showClearFilter,
                        enter = expandHorizontally() + fadeIn(),
                        exit = shrinkHorizontally() + fadeOut()
                    ) {
                        IconButtonAJ(
                            modifier = Modifier.height(52.dp),
                            icon = Icons.Default.Delete,
                            onClick = onClearFilterClick
                        )
                    }
                }

            }
        }
    }
}

private val ACTION_COLUMN_WIDTH = 64.dp

@Preview
@Composable
fun ConsultationMobileScreenPreview() {
    AppTheme {
        Box(modifier = Modifier.background(Color.White)){
            ConsultationContent(
                title = "Consulta",
                dispatcherEvent = {},
                uiState = ConsultationState(),
                catalog = ConsultationViewModel.CatalogState()
            )
        }
    }
}
