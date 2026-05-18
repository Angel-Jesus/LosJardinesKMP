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
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowCircleDown
import androidx.compose.material.icons.filled.Delete
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import com.pe.losjardines.components.buttom.ButtonAJ
import com.pe.losjardines.components.buttom.IconButtonAJ
import com.pe.losjardines.components.dialog.FieldUpdateDialog
import com.pe.losjardines.components.dialog.TypeField
import com.pe.losjardines.components.dropdown.DropDownAJ
import com.pe.losjardines.components.empty_state.EmptyStateAJ
import com.pe.losjardines.components.loading.LoadingAJ
import com.pe.losjardines.components.textInput.InputType
import com.pe.losjardines.components.textInput.TextInputAJ
import com.pe.losjardines.presentation.components.HeaderComponent
import com.pe.losjardines.presentation.content.consultation.contract.ConsultationEvent
import com.pe.losjardines.presentation.content.consultation.contract.ConsultationState
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
import com.pe.losjardines.values.BackgroundOverlay
import com.pe.losjardines.values.BackgroundTableBrand
import com.pe.losjardines.values.DefaultTextColor
import com.pe.losjardines.values.DividerColor
import com.pe.losjardines.values.DividerGrayColor
import com.pe.losjardines.values.LocalAppTypographyCore
import com.pe.losjardines.values.SoftTextColor
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

data class UpdateFieldParams(
    val show: Boolean = false,
    val id: Long = 0L,
    val collection: String = "",
    val idNetwork: String = "",
    val field: FieldRegistration? = null,
    val title: String = "",
    val description: String = "",
    val value: String = "",
    val typeField: TypeField = TypeField.TEXT,
    val listOption: List<String> = emptyList()
)

@Composable
fun ConsultationMobileScreen(
    title: String,
    viewModel: ConsultationViewModel = koinViewModel(),
    typography: AppTypography = LocalAppTypographyCore.current
) {
    val sharedScrollState = rememberScrollState()
    var isExpanded by rememberSaveable { mutableStateOf(false) }
    val uiState by viewModel.uiState.collectAsState()
    var updateFieldParams by remember { mutableStateOf(UpdateFieldParams()) }
    var deleteParams by remember { mutableStateOf(Triple(false, 0L, String.EMPTY)) }
    val catalog by viewModel.catalogState.collectAsState()

    val rotationState by animateFloatAsState(
        targetValue = if (isExpanded) 180f else 0f,
        animationSpec = tween(durationMillis = 300),
        label = "rotation"
    )

    LifecycleEventEffect(Lifecycle.Event.ON_RESUME){
        viewModel.onEvent(ConsultationEvent.GetClientsRegister())
    }

    if(uiState.loading){
        LoadingAJ(
            title = "Información del cliente",
            subtitle = "Obteniendo información"
        )
    }

    if(deleteParams.first){

    }

    if(updateFieldParams.show){
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
                viewModel.onEvent(ConsultationEvent.UpdateClientInformation(value, updateFieldParams))
                updateFieldParams = UpdateFieldParams()
            }
        )
    }

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
                    viewModel.onEvent(ConsultationEvent.ValueChanged(value, field))
                },
                onFilterClick = {
                    viewModel.onEvent(ConsultationEvent.Filter)
                },
                onClearFilterClick = {
                    viewModel.onEvent(ConsultationEvent.ClearFilter)
                },
                uiState = uiState
            )
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))

            HeaderTableContent(
                modifier = Modifier.fillMaxWidth().horizontalScroll(sharedScrollState)
            )
        }

        items(items = uiState.clientsRegister, key = { it.id ?: 0L }) { client ->
            InformationTableContent(
                modifier = Modifier.fillMaxWidth().horizontalScroll(sharedScrollState),
                client = client,
                catalog = catalog,
                onUpdateClick = {
                    updateFieldParams = it
                },
                onDeleteClick = { id, idFirebase ->

                }
            )
        }

        if (uiState.clientsRegister.isEmpty()) {
            item {
                EmptyStateAJ(
                    modifier = Modifier.fillMaxWidth(),
                    image = Res.drawable.ic_not_found,
                    title = stringResource(Res.string.title_not_register_found),
                    description = stringResource(Res.string.description_not_register_found)
                )
            }
        }
    }

}

@Composable
private fun HeaderTableContent(
    modifier: Modifier = Modifier,
    typography: AppTypography = LocalAppTypographyCore.current
) {
    Row(
        modifier = modifier
            .background(BackgroundTableBrand)
            .lineDividerSectionTable()
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.width(FieldRegistration.ROOM.dimensionWidth),
            text = FieldRegistration.ROOM.displayName,
            color = DefaultTextColor,
            style = typography.bodyLarge
        )

        Text(
            modifier = Modifier.width(FieldRegistration.FULL_NAME.dimensionWidth),
            text = FieldRegistration.FULL_NAME.displayName,
            color = DefaultTextColor,
            style = typography.bodyLarge
        )

        Text(
            modifier = Modifier.width(FieldRegistration.SEX.dimensionWidth),
            text = FieldRegistration.SEX.displayName,
            color = DefaultTextColor,
            style = typography.bodyLarge
        )

        Text(
            modifier = Modifier.width(FieldRegistration.CHECK_IN_DATE.dimensionWidth),
            text = FieldRegistration.CHECK_IN_DATE.displayName,
            color = DefaultTextColor,
            style = typography.bodyLarge
        )

        Text(
            modifier = Modifier.width(FieldRegistration.CHECK_OUT_DATE.dimensionWidth),
            text = FieldRegistration.CHECK_OUT_DATE.displayName,
            color = DefaultTextColor,
            style = typography.bodyLarge
        )

        Text(
            modifier = Modifier.width(FieldRegistration.DOCUMENT_TYPE.dimensionWidth),
            text = FieldRegistration.DOCUMENT_TYPE.displayName,
            color = DefaultTextColor,
            style = typography.bodyLarge
        )

        Text(
            modifier = Modifier.width(FieldRegistration.DOCUMENT_NUMBER.dimensionWidth),
            text = FieldRegistration.DOCUMENT_NUMBER.displayName,
            color = DefaultTextColor,
            style = typography.bodyLarge
        )

        Text(
            modifier = Modifier.width(FieldRegistration.COUNTRY_OF_RESIDENCE.dimensionWidth),
            text = FieldRegistration.COUNTRY_OF_RESIDENCE.displayName,
            color = DefaultTextColor,
            style = typography.bodyLarge
        )

        Text(
            modifier = Modifier.width(FieldRegistration.REGION_OF_RESIDENCE.dimensionWidth),
            text = FieldRegistration.REGION_OF_RESIDENCE.displayName,
            color = DefaultTextColor,
            style = typography.bodyLarge
        )

        Text(
            modifier = Modifier.width(FieldRegistration.TRAVEL_REASON.dimensionWidth),
            text = FieldRegistration.TRAVEL_REASON.displayName,
            color = DefaultTextColor,
            style = typography.bodyLarge
        )

        Text(
            modifier = Modifier.width(FieldRegistration.RATE.dimensionWidth),
            text = FieldRegistration.RATE.displayName,
            color = DefaultTextColor,
            style = typography.bodyLarge
        )

        Text(
            modifier = Modifier.width(FieldRegistration.OBSERVATION.dimensionWidth),
            text = FieldRegistration.OBSERVATION.displayName,
            color = DefaultTextColor,
            style = typography.bodyLarge
        )

        Text(
            modifier = Modifier.width(64.dp),
            text = "Acción",
            color = DefaultTextColor,
            style = typography.bodyLarge
        )
    }
}

@Composable
private fun InformationTableContent(
    modifier: Modifier = Modifier,
    client: RegistrationDto,
    catalog: ConsultationViewModel.CatalogState = ConsultationViewModel.CatalogState(),
    onUpdateClick: (UpdateFieldParams) -> Unit = {},
    onDeleteClick: (Long, String) -> Unit = {_,_ ->},
    typography: AppTypography = LocalAppTypographyCore.current
){
    Row(
        modifier = modifier
            .background(BackgroundOverlay)
            .lineDividerSectionTable()
            .padding(horizontal = 8.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .width(FieldRegistration.ROOM.dimensionWidth)
                .clickable{
                    val updateFieldParams = UpdateFieldParams(
                        show = true,
                        id = client.id ?: 0L,
                        collection = client.collection,
                        idNetwork = client.idFirebase,
                        field = FieldRegistration.ROOM,
                        title = "Actualizar ${FieldRegistration.ROOM.displayName}",
                        description = "Ingresa el nuevo valor del campo ${FieldRegistration.ROOM.displayName}",
                        value = client.room,
                        typeField = TypeField.NUMBER
                    )
                    onUpdateClick(updateFieldParams)
                }
            ,
            text = client.room,
            color = SoftTextColor,
            style = typography.bodyLarge
        )

        Text(
            modifier = Modifier
                .width(FieldRegistration.FULL_NAME.dimensionWidth)
                .clickable{
                    val updateFieldParams = UpdateFieldParams(
                        show = true,
                        id = client.id ?: 0L,
                        collection = client.collection,
                        idNetwork = client.idFirebase,
                        field = FieldRegistration.FULL_NAME,
                        title = "Actualizar ${FieldRegistration.FULL_NAME.displayName}",
                        description = "Ingresa el nuevo valor del campo ${FieldRegistration.FULL_NAME.displayName}",
                        value = client.name,
                        typeField = TypeField.TEXT_SPECIAL
                    )
                    onUpdateClick(updateFieldParams)
                }
            ,
            text = client.name,
            color = SoftTextColor,
            style = typography.bodyLarge
        )

        Text(
            modifier = Modifier
                .width(FieldRegistration.SEX.dimensionWidth)
                .clickable{
                    val updateFieldParams = UpdateFieldParams(
                        show = true,
                        field = FieldRegistration.SEX,
                        id = client.id ?: 0L,
                        collection = client.collection,
                        idNetwork = client.idFirebase,
                        title = "Actualizar ${FieldRegistration.SEX.displayName}",
                        description = "Ingresa el nuevo valor del campo ${FieldRegistration.SEX.displayName}",
                        value = client.sex,
                        listOption = Sex.options,
                        typeField = TypeField.DROP_DOWN
                    )
                    onUpdateClick(updateFieldParams)
                }
            ,
            text = Sex.getAbbreviations(client.sex),
            color = SoftTextColor,
            style = typography.bodyLarge
        )

        Text(
            modifier = Modifier
                .width(FieldRegistration.CHECK_IN_DATE.dimensionWidth)
                .clickable{
                    val updateFieldParams = UpdateFieldParams(
                        show = true,
                        id = client.id ?: 0L,
                        collection = client.collection,
                        idNetwork = client.idFirebase,
                        field = FieldRegistration.CHECK_IN_DATE,
                        title = "Actualizar ${FieldRegistration.CHECK_IN_DATE.displayName}",
                        description = "Ingresa el nuevo valor del campo ${FieldRegistration.CHECK_IN_DATE.displayName}",
                        value = client.dateEnter,
                        typeField = TypeField.DATE_PICKER
                    )
                    onUpdateClick(updateFieldParams)
                }
            ,
            text = client.dateEnter,
            color = SoftTextColor,
            style = typography.bodyLarge
        )

        Text(
            modifier = Modifier
                .width(FieldRegistration.CHECK_OUT_DATE.dimensionWidth)
                .clickable{
                    val updateFieldParams = UpdateFieldParams(
                        show = true,
                        id = client.id ?: 0L,
                        collection = client.collection,
                        idNetwork = client.idFirebase,
                        field = FieldRegistration.CHECK_OUT_DATE,
                        title = "Actualizar ${FieldRegistration.CHECK_OUT_DATE.displayName}",
                        description = "Ingresa el nuevo valor del campo ${FieldRegistration.CHECK_OUT_DATE.displayName}",
                        value = client.dateExit,
                        typeField = TypeField.DATE_PICKER
                    )
                    onUpdateClick(updateFieldParams)
                }
            ,
            text = client.dateExit,
            color = SoftTextColor,
            style = typography.bodyLarge
        )

        Text(
            modifier = Modifier
                .width(FieldRegistration.DOCUMENT_TYPE.dimensionWidth)
                .clickable{
                    val updateFieldParams = UpdateFieldParams(
                        show = true,
                        id = client.id ?: 0L,
                        collection = client.collection,
                        idNetwork = client.idFirebase,
                        field = FieldRegistration.DOCUMENT_TYPE,
                        title = "Actualizar ${FieldRegistration.DOCUMENT_TYPE.displayName}",
                        description = "Ingresa el nuevo valor del campo ${FieldRegistration.DOCUMENT_TYPE.displayName}",
                        value = client.typeDocument,
                        typeField = TypeField.DROP_DOWN,
                        listOption = DocumentType.options
                    )
                    onUpdateClick(updateFieldParams)
                }
            ,
            text = client.typeDocument,
            color = SoftTextColor,
            style = typography.bodyLarge
        )

        Text(
            modifier = Modifier
                .width(FieldRegistration.DOCUMENT_NUMBER.dimensionWidth)
                .clickable{
                    val updateFieldParams = UpdateFieldParams(
                        show = true,
                        id = client.id ?: 0L,
                        collection = client.collection,
                        idNetwork = client.idFirebase,
                        field = FieldRegistration.DOCUMENT_NUMBER,
                        title = "Actualizar ${FieldRegistration.DOCUMENT_NUMBER.displayName}",
                        description = "Ingresa el nuevo valor del campo ${FieldRegistration.DOCUMENT_NUMBER.displayName}",
                        value = client.numberDocument,
                        typeField = TypeField.TEXT_SPECIAL
                    )
                    onUpdateClick(updateFieldParams)
                }
            ,
            text = client.numberDocument,
            color = SoftTextColor,
            style = typography.bodyLarge
        )

        Text(
            modifier = Modifier
                .width(FieldRegistration.COUNTRY_OF_RESIDENCE.dimensionWidth)
                .clickable{
                    val updateFieldParams = UpdateFieldParams(
                        show = true,
                        id = client.id ?: 0L,
                        collection = client.collection,
                        idNetwork = client.idFirebase,
                        field = FieldRegistration.COUNTRY_OF_RESIDENCE,
                        title = "Actualizar ${FieldRegistration.COUNTRY_OF_RESIDENCE.displayName}",
                        description = "Ingresa el nuevo valor del campo ${FieldRegistration.COUNTRY_OF_RESIDENCE.displayName}",
                        value = client.country,
                        typeField = TypeField.DROP_DOWN,
                        listOption = catalog.countries.map { it.name }
                    )
                    onUpdateClick(updateFieldParams)
                }
            ,
            text = client.country,
            color = SoftTextColor,
            style = typography.bodyLarge
        )

        Text(
            modifier = Modifier
                .width(FieldRegistration.REGION_OF_RESIDENCE.dimensionWidth)
                .clickable(enabled = client.region.isNotEmpty()){
                    val updateFieldParams = UpdateFieldParams(
                        show = true,
                        id = client.id ?: 0L,
                        collection = client.collection,
                        idNetwork = client.idFirebase,
                        field = FieldRegistration.REGION_OF_RESIDENCE,
                        title = "Actualizar ${FieldRegistration.COUNTRY_OF_RESIDENCE.displayName}",
                        description = "Ingresa el nuevo valor del campo ${FieldRegistration.COUNTRY_OF_RESIDENCE.displayName}",
                        value = client.region,
                        typeField = TypeField.DROP_DOWN,
                        listOption = catalog.regions.map { it.name }
                    )
                    onUpdateClick(updateFieldParams)
                }
            ,
            text = client.region,
            color = SoftTextColor,
            style = typography.bodyLarge
        )

        Text(
            modifier = Modifier
                .width(FieldRegistration.TRAVEL_REASON.dimensionWidth)
                .clickable{
                    val updateFieldParams = UpdateFieldParams(
                        show = true,
                        id = client.id ?: 0L,
                        collection = client.collection,
                        idNetwork = client.idFirebase,
                        field = FieldRegistration.TRAVEL_REASON,
                        title = "Actualizar ${FieldRegistration.TRAVEL_REASON.displayName}",
                        description = "Ingresa el nuevo valor del campo ${FieldRegistration.TRAVEL_REASON.displayName}",
                        value = client.reasonTravel,
                        typeField = TypeField.DROP_DOWN,
                        listOption = catalog.travelReasons.map { it.description }
                    )
                    onUpdateClick(updateFieldParams)
                }
            ,
            text = client.reasonTravel,
            color = SoftTextColor,
            style = typography.bodyLarge
        )

        Text(
            modifier = Modifier
                .width(FieldRegistration.RATE.dimensionWidth)
                .clickable{
                    val updateFieldParams = UpdateFieldParams(
                        show = true,
                        id = client.id ?: 0L,
                        collection = client.collection,
                        idNetwork = client.idFirebase,
                        field = FieldRegistration.RATE,
                        title = "Actualizar ${FieldRegistration.RATE.displayName}",
                        description = "Ingresa el nuevo valor del campo ${FieldRegistration.RATE.displayName}",
                        value = client.fee,
                        typeField = TypeField.NUMBER
                    )
                    onUpdateClick(updateFieldParams)
                }
            ,
            text = "S/${client.fee}",
            color = SoftTextColor,
            style = typography.bodyLarge
        )

        Text(
            modifier = Modifier
                .width(FieldRegistration.OBSERVATION.dimensionWidth)
                .clickable{
                    val updateFieldParams = UpdateFieldParams(
                        show = true,
                        id = client.id ?: 0L,
                        collection = client.collection,
                        idNetwork = client.idFirebase,
                        field = FieldRegistration.OBSERVATION,
                        title = "Actualizar ${FieldRegistration.OBSERVATION.displayName}",
                        description = "Ingresa el nuevo valor del campo ${FieldRegistration.OBSERVATION.displayName}",
                        value = client.observation,
                        typeField = TypeField.TEXT
                    )
                    onUpdateClick(updateFieldParams)
                }
            ,
            text = client.observation,
            color = SoftTextColor,
            style = typography.bodyLarge
        )

        Box(modifier = Modifier.width(64.dp), contentAlignment = Alignment.Center){
            IconButtonAJ(
                modifier = Modifier.size(48.dp),
                icon = Icons.Default.Delete,
                onClick = { onDeleteClick(client.id ?: 0L, client.idFirebase) }
            )
        }
    }
}

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

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
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

@Composable
private fun Modifier.lineDividerSectionTable(): Modifier {
    return this.drawBehind(onDraw = {
        drawLine(
            color = DividerGrayColor,
            start = Offset(0f, size.height),
            end = Offset(size.width, size.height),
            strokeWidth = 2f
        )
    })
}

@Preview
@Composable
fun ConsultationMobileScreenPreview() {
    AppTheme {
        ConsultationMobileScreen(title = "Consulta")
    }
}