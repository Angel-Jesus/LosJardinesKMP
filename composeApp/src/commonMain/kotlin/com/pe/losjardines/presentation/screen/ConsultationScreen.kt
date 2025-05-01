package com.pe.losjardines.presentation.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import com.pe.losjardines.core.components.text.TextTitle
import com.pe.losjardines.core.values.BackgroundBrandColor
import com.pe.losjardines.core.values.ConsultString
import com.pe.losjardines.core.values.DefaultTextColor

class ConsultationScreen: Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = { TopAppBarConsult(navigator) }
        ) {

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