package com.pe.losjardines.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.pe.losjardines.core.components.button.EndingButton
import com.pe.losjardines.core.components.input_text.InputText
import com.pe.losjardines.core.components.picker.Picker
import com.pe.losjardines.core.components.text.TextTitle
import com.pe.losjardines.core.values.BackgroundBrandColor
import com.pe.losjardines.core.values.DefaultTextColor
import com.pe.losjardines.core.values.RegisterString
import com.pe.losjardines.presentation.constance.RegisterKey
import com.pe.losjardines.presentation.constance.RegisterKey.DATE
import com.pe.losjardines.presentation.constance.RegisterKey.TIME
import com.pe.losjardines.presentation.contract.registration.RegistrationEvent.OnValueChanged
import com.pe.losjardines.presentation.contract.registration.RegistrationEvent.ResetForm
import com.pe.losjardines.presentation.contract.registration.RegistrationEvent.SendForm
import com.pe.losjardines.presentation.viewmodel.RegistrationViewModel
import losjardineskmp.composeapp.generated.resources.Res
import losjardineskmp.composeapp.generated.resources.logohotel
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

class RegisterScreen: Screen {
    @OptIn(KoinExperimentalAPI::class)
    @Composable
    override fun Content() {
        val viewModel = koinViewModel<RegistrationViewModel>()
        val navigator = LocalNavigator.currentOrThrow

        LaunchedEffect(Unit){
            viewModel.onEvent(ResetForm)
        }

        val registrationState by viewModel.uiState.collectAsState()
        val isShowDatePicker = remember { mutableStateOf(false) }
        val isShowTimePicker = remember { mutableStateOf(false) }

        Picker.date(
            isShow = isShowDatePicker,
            onDateSelected = {
                viewModel.onEvent(OnValueChanged(DATE, it))
            }
        )

        Picker.time(
            isShow = isShowTimePicker,
            onTimeSelected = {
                viewModel.onEvent(OnValueChanged(TIME, it))
            }
        )

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = { TopAppBarRegister(onBack = { navigator.push(HomeScreen()) } ) }
        ) {innerPadding ->
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(innerPadding).padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                item {
                    Image(
                        painter = painterResource(resource = Res.drawable.logohotel),
                        contentDescription = "Logo_AJ",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .padding(top = 20.dp)
                    )
                }

                items(RegisterKey.entries.toTypedArray().copyOfRange(0, 4)){ key ->
                    val field = registrationState.registerMap[key]!!
                    InputText(
                        textTitle = field.title,
                        defaultText = field.value,
                        onValueChange = {
                            viewModel.onEvent(OnValueChanged(key, it))
                        },
                        keyboardOptions = field.type
                    )
                }

                item {
                    val fieldDate = registrationState.registerMap[DATE]!!
                    val fieldTime = registrationState.registerMap[TIME]!!

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        InputText(
                            modifier = Modifier.weight(1f),
                            textTitle = fieldDate.title,
                            defaultText = fieldDate.value,
                            onClick = {
                                isShowDatePicker.value = true
                            },
                            clickable = true,
                            keyboardOptions = fieldDate.type
                        )

                        InputText(
                            modifier = Modifier.weight(1f),
                            textTitle = fieldTime.title,
                            defaultText = fieldTime.value,
                            onClick = {
                                isShowTimePicker.value = true
                            },
                            clickable = true,
                            keyboardOptions = fieldTime.type
                        )
                    }
                }

                items(RegisterKey.entries.toTypedArray().copyOfRange(6, 8)){ key ->
                    val field = registrationState.registerMap[key]!!

                    InputText(
                        textTitle = field.title,
                        defaultText = field.value,
                        onValueChange = {
                            viewModel.onEvent(OnValueChanged(key, it))
                        },
                        keyboardOptions = field.type
                    )
                }

                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 24.dp, bottom = 12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        EndingButton(
                            modifier = Modifier.fillMaxWidth(0.5f),
                            text = RegisterString.DONE_REGISTER,
                            onClick = {
                                viewModel.onEvent(SendForm)
                            }
                        )
                    }
                }
            }
        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun TopAppBarRegister(onBack: () -> Unit) {
        TopAppBar(
            title = { TextTitle(text = RegisterString.TITLE_REGISTER) },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        tint = DefaultTextColor,
                        contentDescription = "Go back Home from Register"
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(BackgroundBrandColor)
        )
    }
}