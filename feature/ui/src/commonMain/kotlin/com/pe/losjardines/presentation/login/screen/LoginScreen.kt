package com.pe.losjardines.presentation.login.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.pe.losjardines.components.buttom.ButtonAJ
import com.pe.losjardines.components.textInput.InputType
import com.pe.losjardines.components.textInput.TextInputAJ
import com.pe.losjardines.presentation.login.contract.LoginEffect
import com.pe.losjardines.presentation.login.contract.LoginEvent
import com.pe.losjardines.presentation.login.contract.LoginState
import com.pe.losjardines.presentation.login.viewmodel.FieldType
import com.pe.losjardines.presentation.login.viewmodel.LoginViewModel
import com.pe.losjardines.values.AppTheme
import com.pe.losjardines.values.AppTypography
import com.pe.losjardines.values.BrandTextColor
import com.pe.losjardines.values.LocalAppTypographyCore
import com.pe.losjardines.values.SoftTextColor
import kotlinx.coroutines.flow.collectLatest
import losjardineskmp.feature.ui.generated.resources.Res
import losjardineskmp.feature.ui.generated.resources.logoaj
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
fun LoginScreen(
    viewModel: LoginViewModel = koinViewModel(),
    isMobile: Boolean,
    onPrincipalScreen: () -> Unit
){
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(true){
        viewModel.onEvent(LoginEvent.CheckSession)

        viewModel.effect.collectLatest { effect ->
            when(effect){
                LoginEffect.LoginError -> TODO()
                LoginEffect.LoginSuccess -> onPrincipalScreen()
            }
        }
    }

    LoginContent(
        isMobile = isMobile,
        uiState = uiState,
        dispatcherEvent = viewModel::onEvent
    )

}

@Composable
private fun LoginContent(
    isMobile: Boolean,
    uiState: LoginState,
    dispatcherEvent: (LoginEvent) -> Unit,
    typography: AppTypography = LocalAppTypographyCore.current,
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp).offset(y = (-24).dp),
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier.size(200.dp).align(Alignment.CenterHorizontally),
            painter = painterResource(resource = Res.drawable.logoaj),
            contentDescription = null
        )

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Bienvenido a Los Jardines",
            textAlign = TextAlign.Center,
            style = typography.headerLarge,
            color = BrandTextColor
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Inicie sesión para continuar",
            textAlign = TextAlign.Center,
            style = typography.titleMedium,
            color = SoftTextColor
        )

        Spacer(modifier = Modifier.height(24.dp))

        TextInputAJ(
            modifier = Modifier.fillMaxWidth(1f.takeIf { isMobile } ?: 0.4f),
            value = uiState.email,
            label = "Nombre de usuario",
            inputType = InputType.EMAIL,
            onValueChange = { dispatcherEvent(LoginEvent.UpdateValue(it, FieldType.EMAIL)) }
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextInputAJ(
            modifier = Modifier.fillMaxWidth(1f.takeIf { isMobile } ?: 0.4f),
            value = uiState.password,
            label = "Contraseña",
            onValueChange = { dispatcherEvent(LoginEvent.UpdateValue(it, FieldType.PASSWORD)) },
            isTypePassword = true
        )

        Spacer(modifier = Modifier.height(32.dp))

        ButtonAJ(
            modifier = Modifier.fillMaxWidth(1f.takeIf { isMobile } ?: 0.4f),
            text = "Iniciar sesión",
            onClick = {
                dispatcherEvent(LoginEvent.EnterLogin)
            },
            enabled = uiState.email.isNotBlank() && uiState.password.isNotBlank()
        )
    }
}

@Preview
@Composable
private fun LoginPreview(){
    AppTheme {
        Box(modifier = Modifier.background(Color.White)){
            LoginContent(
                isMobile = true,
                uiState = LoginState(
                    email = "",
                    password = ""
                ),
                dispatcherEvent = {}
            )
        }
    }
}