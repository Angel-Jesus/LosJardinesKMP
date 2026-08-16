package com.pe.losjardines.presentation.content.reservation.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CalendarViewWeek
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.FilterCenterFocus
import androidx.compose.material.icons.filled.TransitEnterexit
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import com.pe.losjardines.components.chip.ChipAJItem
import com.pe.losjardines.components.chip.ChipAJList
import com.pe.losjardines.components.chip.model.ChipAJDefaults
import com.pe.losjardines.components.dialog.FieldUpdateDialog
import com.pe.losjardines.components.dialog.ResultActionDialog
import com.pe.losjardines.components.dialog.ResultDialog
import com.pe.losjardines.components.dialog.TypeField
import com.pe.losjardines.components.empty_state.EmptyStateAJ
import com.pe.losjardines.components.loading.LoadingAJ
import com.pe.losjardines.presentation.components.HeaderComponent
import com.pe.losjardines.presentation.components.table.TableColumn
import com.pe.losjardines.presentation.components.table.UpdateFieldParams
import com.pe.losjardines.presentation.components.table.registrationColumn
import com.pe.losjardines.presentation.components.table.tableSection
import com.pe.losjardines.presentation.components.table.updateFieldParamsOf
import com.pe.losjardines.presentation.content.reservation.contract.ReservationEvent
import com.pe.losjardines.presentation.content.reservation.contract.ReservationState
import com.pe.losjardines.presentation.content.reservation.viewmodel.ReservationViewModel
import com.pe.losjardines.presentation.content.utils.DocumentType
import com.pe.losjardines.presentation.content.utils.FieldRegistration
import com.pe.losjardines.presentation.content.utils.Sex
import com.pe.losjardines.usecases.model.ReservationDto
import com.pe.losjardines.usecases.model.ReservationStatus
import com.pe.losjardines.utils.companions.EMPTY
import com.pe.losjardines.values.AppTheme
import com.pe.losjardines.values.AppTypography
import com.pe.losjardines.values.BackgroundBrandColor
import com.pe.losjardines.values.LocalAppTypographyCore
import losjardineskmp.feature.ui.generated.resources.Res
import losjardineskmp.feature.ui.generated.resources.description_not_reservation_found
import losjardineskmp.feature.ui.generated.resources.ic_not_found
import losjardineskmp.feature.ui.generated.resources.title_not_reservation_found
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

val chipFilters = listOf(
    ChipAJItem(
        text = ReservationStatus.CHECK_IN.description,
        style = ChipAJDefaults.default()
    ),
    ChipAJItem(
        text = ReservationStatus.OCCUPIED.description,
        style = ChipAJDefaults.orange()
    ),
    ChipAJItem(
        text = ReservationStatus.CANCELED.description,
        style = ChipAJDefaults.red()
    )
)

@Composable
fun ReservationMobileScreen(
    title: String,
    onCheckIn: (ReservationDto) -> Unit = {},
    viewModel: ReservationViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val catalog by viewModel.catalogState.collectAsState()

    LifecycleEventEffect(Lifecycle.Event.ON_RESUME){
        viewModel.onEvent(ReservationEvent.GetReservations())
    }

    if(uiState.loading){
        LoadingAJ(
            title = "Reservas",
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

    ReservationContent(
        dispatcherEvent = viewModel::onEvent,
        title = title,
        uiState = uiState,
        catalog = catalog,
        onCheckIn = onCheckIn
    )
}

@Composable
private fun ReservationContent(
    dispatcherEvent: (ReservationEvent) -> Unit,
    title: String,
    uiState: ReservationState,
    catalog: ReservationViewModel.CatalogState,
    onCheckIn: (ReservationDto) -> Unit = {}
) {
    val sharedScrollState = rememberScrollState()
    var updateFieldParams by remember { mutableStateOf(UpdateFieldParams()) }
    var deleteParams by remember { mutableStateOf(Triple(false, 0L, String.EMPTY)) }


    if(deleteParams.first){
        ResultActionDialog(
            modifier = Modifier,
            title = "Eliminar reserva",
            description = "Esta por eliminar la reserva. ¿Desea continuar?",
            primaryButtonText = "Eliminar",
            secondaryButtonText = "Cancelar",
            onPrimaryClick = {
                dispatcherEvent(ReservationEvent.DeleteReservationInformation(deleteParams.second, deleteParams.third))
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
                    ReservationEvent.UpdateReservationInformation(
                        value,
                        updateFieldParams
                    )
                )
                updateFieldParams = UpdateFieldParams()
            }
        )
    }

    val columns = reservationColumns(catalog)

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
            Text(
                modifier = Modifier.padding(top = 14.dp, bottom = 8.dp),
                text = "Filtrar por",
                style = LocalAppTypographyCore.current.titleSmall
            )
        }

        item {
            ChipAJList(
                modifier = Modifier.fillMaxWidth(),
                chips = chipFilters,
                selectedChip = chipFilters.find { it.text == uiState.reservationStatus.description },
                onChipSelected = {
                    dispatcherEvent(ReservationEvent.FilterReservations(ReservationStatus.getByDescription(it.text)))
                }
            )
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

        tableSection(
            columns = columns,
            rows = uiState.reservations,
            rowKey = { it.id ?: 0L },
            scrollState = sharedScrollState,
            actionWidth = ACTION_COLUMN_WIDTH,
            onUpdateClick = { if(uiState.reservationStatus.description == ReservationStatus.CHECK_IN.description) updateFieldParams = it },
            emptyContent = {
                EmptyStateAJ(
                    modifier = Modifier.fillMaxWidth(),
                    image = Res.drawable.ic_not_found,
                    title = stringResource(Res.string.title_not_reservation_found),
                    description = stringResource(Res.string.description_not_reservation_found)
                )
            },
            rowAction = { reservation ->
                if(reservation.attentionState == ReservationStatus.CHECK_IN.value){
                    CheckInAction(
                        onClick = { onCheckIn(reservation) }
                    )
                }

                Spacer(modifier = Modifier.width(4.dp))

                Icon(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .clickable(
                            onClick = { deleteParams = Triple(true, reservation.id ?: 0L, reservation.idFirebase) }
                        ),
                    imageVector = Icons.Default.DeleteOutline,
                    contentDescription = null,
                    tint = BackgroundBrandColor
                )
            }
        )
    }
}

private fun reservationColumns(
    catalog: ReservationViewModel.CatalogState
): List<TableColumn<ReservationDto>> = listOf(
    registrationColumn(
        field = FieldRegistration.ROOM,
        value = { it.room },
        update = {
            updateFieldParamsOf(FieldRegistration.ROOM, it.id ?: 0L, it.collection, it.idFirebase, it.room, TypeField.NUMBER)
        }
    ),
    registrationColumn(
        field = FieldRegistration.FULL_NAME,
        value = { it.userName },
        update = {
            updateFieldParamsOf(FieldRegistration.FULL_NAME, it.id ?: 0L, it.collection, it.idFirebase, it.userName, TypeField.TEXT_SPECIAL)
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
    ),
    TableColumn(
        title = "Estado R.",
        width = STATE_COLUMN_WIDTH,
        value = { ReservationStatus.getDescriptions(it.attentionState) }
    )
)

@Composable
private fun CheckInAction(onClick: () -> Unit){
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(14.dp))
            .border(BorderStroke(1.dp, BackgroundBrandColor), RoundedCornerShape(14.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Icon(
            modifier = Modifier.size(20.dp),
            imageVector = Icons.Default.CalendarMonth,
            contentDescription = null,
            tint = BackgroundBrandColor
        )

        Text(
            text = "Check-in",
            color = BackgroundBrandColor,
            style = LocalAppTypographyCore.current.bodyLarge
        )
    }
}

private val ACTION_COLUMN_WIDTH = 140.dp
private val STATE_COLUMN_WIDTH = 100.dp

@Preview
@Composable
private fun CheckInActionPreview() {
    AppTheme {
        Box(modifier = Modifier.background(Color.White)){
            CheckInAction(onClick = {})
        }
    }
}

@Preview
@Composable
private fun ReservationMobileScreenPreview() {
    AppTheme {
        Box(modifier = Modifier.background(Color.White)){
            ReservationContent(
                title = "Reservas",
                dispatcherEvent = {},
                uiState = ReservationState(),
                catalog = ReservationViewModel.CatalogState()
            )
        }
    }
}
