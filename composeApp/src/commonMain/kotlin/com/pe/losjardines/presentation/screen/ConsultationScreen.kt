package com.pe.losjardines.presentation.screen

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import com.pe.losjardines.core.components.button.EndingButton
import com.pe.losjardines.core.components.dialog.AlertDialogAJ
import com.pe.losjardines.core.components.dialog.DialogInput
import com.pe.losjardines.core.components.dropdown.DropDownOption
import com.pe.losjardines.core.components.input_text.InputText
import com.pe.losjardines.core.components.table.HeaderTable
import com.pe.losjardines.core.components.table.UserInformationTable
import com.pe.losjardines.core.components.text.TextTitle
import com.pe.losjardines.core.values.AppTypography
import com.pe.losjardines.core.values.BackgroundBrandColor
import com.pe.losjardines.core.values.ConsultString
import com.pe.losjardines.core.values.ConsultString.BTN_APPLY_FILTER
import com.pe.losjardines.core.values.ConsultString.BTN_CLEAR_FILTER
import com.pe.losjardines.core.values.ConsultString.FILTER_DNI
import com.pe.losjardines.core.values.ConsultString.FILTER_INIT
import com.pe.losjardines.core.values.ConsultString.FILTER_MONTH
import com.pe.losjardines.core.values.ConsultString.FILTER_ORIGIN
import com.pe.losjardines.core.values.ConsultString.TITLE_FILTER
import com.pe.losjardines.core.values.DefaultTextColor
import com.pe.losjardines.core.values.LocalAppTypography
import com.pe.losjardines.domain.model.ClientFilter
import com.pe.losjardines.domain.model.ClientModel
import com.pe.losjardines.presentation.constance.TableConstance
import com.pe.losjardines.presentation.contract.consult.ConsultEvent
import com.pe.losjardines.presentation.contract.consult.ConsultState
import com.pe.losjardines.presentation.enums.StateConsult
import com.pe.losjardines.presentation.enums.TitleClient
import com.pe.losjardines.presentation.model.DataUpdate
import com.pe.losjardines.presentation.viewmodel.ConsultationViewModel
import com.pe.losjardines.utils.EMPTY
import com.pe.losjardines.utils.MonthFilter
import losjardineskmp.composeapp.generated.resources.Res
import losjardineskmp.composeapp.generated.resources.error_internet
import losjardineskmp.composeapp.generated.resources.logohotel
import losjardineskmp.composeapp.generated.resources.no_data
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

class ConsultationScreen: Screen {
    @OptIn(KoinExperimentalAPI::class)
    @Composable
    override fun Content() {
        val viewModel = koinViewModel<ConsultationViewModel>()
        val navigator = LocalNavigator.current
        val scrollRow = rememberScrollState()
        val typography = LocalAppTypography.current

        val consultationState by viewModel.uiState.collectAsState()

        var filterOption by remember { mutableStateOf(FILTER_INIT) }
        var filterMonth by remember { mutableStateOf(MonthFilter.NONE.displayName) }
        var filterInput by remember { mutableStateOf(String.EMPTY) }
        var showUpdateData by rememberSaveable{ mutableStateOf(false) }
        var showDeleteDialog by rememberSaveable{ mutableStateOf(false) }
        var valueUpdate by rememberSaveable { mutableStateOf(DataUpdate()) }
        var clientDelete by rememberSaveable { mutableStateOf(ClientModel()) }

        LaunchedEffect(Unit){
            viewModel.onEvent(ConsultEvent.GetClient())
        }

        if(showUpdateData){
            DialogInput(
                modifier = Modifier.fillMaxWidth(0.8f),
                title = valueUpdate.type.descripction,
                defaultText = valueUpdate.value,
                keyboardType = valueUpdate.keyboardOptions,
                typeValue = valueUpdate.type,
                isClickableInput = valueUpdate.type == TitleClient.DATE || valueUpdate.type == TitleClient.TIME,
                onDismiss = { showUpdateData = false },
                onSave = { valueChanged ->
                    viewModel.onEvent(ConsultEvent.UpdateForm(valueUpdate.copy(value = valueChanged)))
                    showUpdateData = false
                }
            )
        }

        if(showDeleteDialog){
            AlertDialogAJ(
                title = "Eliminar cliente",
                message = "Desea eliminar al cliente ${clientDelete.name} de la fecha ${clientDelete.date}?",
                icon = Icons.Filled.Delete,
                onConfirm = {
                    viewModel.onEvent(ConsultEvent.DeleteForm(clientDelete.id))
                    showDeleteDialog = false
                },
                onDismiss = {
                    clientDelete = ClientModel()
                    showDeleteDialog = false
                }
            )
        }

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = { TopAppBarConsult(navigator) }
        ) { innerPadding ->
            LazyColumn(modifier = Modifier.fillMaxSize().padding(innerPadding), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                item {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Image(
                            painter = painterResource(resource = Res.drawable.logohotel),
                            contentDescription = "Logo_AJ",
                            modifier = Modifier
                                .fillMaxWidth(0.5f)
                                .height(120.dp)
                                .padding(top = 20.dp)
                        )
                    }
                }

                item {
                    Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
                        DropDownOption(
                            modifier = Modifier.weight(0.5f).padding(end = 10.dp),
                            title = TITLE_FILTER,
                            style = typography.titleLarge,
                            itemDefault = filterOption,
                            itemList = listOf(FILTER_INIT, FILTER_ORIGIN, FILTER_MONTH, FILTER_DNI),
                            itemSelected = {
                                filterOption = it
                            }
                        )

                        Spacer(modifier = Modifier.weight(0.5f))
                    }
                }

                if(filterOption != FILTER_INIT){
                    item {
                        Row(modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp, top = 8.dp)) {
                            InputText(
                                modifier = Modifier.weight(0.5f),
                                defaultText = String.EMPTY,
                                onValueChange = { newValue ->
                                    filterInput = newValue
                                }
                            )

                            Spacer(modifier = Modifier.width(16.dp))

                            DropDownOption(
                                modifier = Modifier.weight(0.5f),
                                itemDefault = filterMonth,
                                itemList = MonthFilter.entries.toList().map { it.displayName },
                                itemSelected = {
                                    filterMonth = it
                                }
                            )
                        }
                    }

                    item{
                        Row(modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp, top = 8.dp)) {
                            EndingButton(
                                modifier = Modifier,
                                text = BTN_APPLY_FILTER,
                                leadingIcon = Icons.Filled.Search,
                                style = typography.bodyLarge,
                                onClick = {
                                    when(filterOption){
                                        FILTER_MONTH -> viewModel.onEvent(ConsultEvent.GetClient(ClientFilter.Month(filterMonth)))
                                        FILTER_ORIGIN -> viewModel.onEvent(ConsultEvent.GetClient(ClientFilter.Origin(filterInput, filterMonth)))
                                        FILTER_DNI -> viewModel.onEvent(ConsultEvent.GetClient(ClientFilter.DNI(filterInput, filterMonth)))
                                    }
                                }
                            )

                            Spacer(modifier = Modifier.width(16.dp))

                            EndingButton(
                                modifier = Modifier,
                                text = BTN_CLEAR_FILTER,
                                leadingIcon = Icons.Filled.Delete,
                                style = typography.bodyLarge,
                                onClick = {
                                    filterMonth = MonthFilter.NONE.displayName
                                    filterInput = String.EMPTY
                                    filterOption = FILTER_INIT
                                }
                            )
                        }
                    }
                }

                item {
                    HeaderTable(
                        headerTitle = TableConstance.headerTitleText,
                        headerWidth = TableConstance.headerTitleWidth,
                        scrollRow = scrollRow,
                        typography = typography
                    )
                }

                items(consultationState.clients){ userInformation ->
                    UserInformationTable(
                        typography = typography,
                        scrollRow = scrollRow,
                        user = userInformation,
                        onClick = { dataUpdate ->
                            valueUpdate = dataUpdate
                            showUpdateData = true
                        },
                        onDelete = {
                            clientDelete = it
                            showDeleteDialog = true
                        }
                    )
                }

                when(consultationState.state){
                    StateConsult.LOADING -> loadingCase()
                    StateConsult.SUCCESS -> emptyCase(consultationState, typography)
                    StateConsult.ERROR -> errorCase(typography)
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }

            }
        }
    }

    private fun LazyListScope.errorCase(typography: AppTypography) {
        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(10.dp))

                Image(
                    modifier = Modifier.fillMaxWidth(0.6f).height(200.dp),
                    painter = painterResource(Res.drawable.error_internet),
                    contentDescription = null
                )

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Error de conexión",
                    style = typography.bodyLarge,
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )

            }
        }
    }

    private fun LazyListScope.emptyCase(
        consultationState: ConsultState,
        typography: AppTypography
    ) {
        if (consultationState.clients.isEmpty()) {
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(10.dp))

                    Image(
                        modifier = Modifier.fillMaxWidth(0.6f).height(200.dp),
                        painter = painterResource(Res.drawable.no_data),
                        contentDescription = null
                    )

                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "No se encontraron clientes registrado",
                        style = typography.bodyLarge,
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )

                }
            }
        }
    }

    private fun LazyListScope.loadingCase() {
        item {
            Column(
                modifier = Modifier.fillMaxWidth().padding(start = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(24.dp))

                CircularProgressIndicator(
                    modifier = Modifier.size(80.dp),
                    color = BackgroundBrandColor,
                    strokeWidth = 6.dp
                )
            }
        }
    }

    @Composable
    private fun TopAppBarConsult(navigator: Navigator?) {
        TopAppBar(
            title = { TextTitle(text = ConsultString.TITLE_CONSULT) },
            navigationIcon = {
                IconButton(onClick = { navigator?.pop() }) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        tint = DefaultTextColor,
                        contentDescription = "Go back Home from consult"
                    )
                }
            },
            backgroundColor = BackgroundBrandColor
        )
    }
}