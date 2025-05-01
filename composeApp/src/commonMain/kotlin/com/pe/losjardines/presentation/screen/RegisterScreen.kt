package com.pe.losjardines.presentation.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import com.pe.losjardines.core.components.input_text.InputText
import com.pe.losjardines.core.components.text.TextTitle
import com.pe.losjardines.core.values.BackgroundBrandColor
import com.pe.losjardines.core.values.ConsultString
import com.pe.losjardines.core.values.DefaultTextColor
import com.pe.losjardines.presentation.viewmodel.RegistrationViewModel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

class RegisterScreen: Screen {
    @OptIn(KoinExperimentalAPI::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val viewModel = koinViewModel<RegistrationViewModel>()
        var name by remember { mutableStateOf("") }

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = { TopAppBarRegister(navigator) }
        ) {
            LazyColumn(modifier = Modifier.fillMaxSize().padding(vertical = 8.dp, horizontal = 12.dp)) {
                item {
                    InputText(
                        textTitle = "Nombres",
                        defaultText = name,
                        onValueChange = { textChange ->
                            name = textChange
                        }
                    )
                }
            }
        }
    }


    @Composable
    private fun TopAppBarRegister(navigator: Navigator?) {
        TopAppBar(
            title = { TextTitle(text = ConsultString.TITLE_REGISTER) },
            navigationIcon = {
                IconButton(onClick = { navigator?.pop() }) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        tint = DefaultTextColor,
                        contentDescription = "Go back Home from Register"
                    )
                }
            },
            backgroundColor = BackgroundBrandColor
        )
    }
}