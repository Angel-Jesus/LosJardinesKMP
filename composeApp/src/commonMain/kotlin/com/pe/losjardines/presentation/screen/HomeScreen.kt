package com.pe.losjardines.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.pe.losjardines.core.components.button.EndingButton
import com.pe.losjardines.core.values.HomeString
import losjardineskmp.composeapp.generated.resources.Res
import losjardineskmp.composeapp.generated.resources.logohotel
import org.jetbrains.compose.resources.painterResource

class HomeScreen: Screen{
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            Image(
                modifier = Modifier.fillMaxWidth(0.5f).fillMaxHeight(0.3f),
                painter = painterResource(resource = Res.drawable.logohotel),
                contentDescription = null
            )

            Spacer(modifier = Modifier.height(18.dp))

            EndingButton(
                modifier = Modifier.fillMaxWidth(0.8f),
                text = HomeString.REGISTER,
                onClick = { navigator?.push(RegisterScreen()) }
            )

            Spacer(modifier = Modifier.height(14.dp))

            EndingButton(
                modifier = Modifier.fillMaxWidth(0.8f),
                text = HomeString.CONSULT,
                onClick = { navigator?.push(ConsultationScreen()) }
            )
        }
    }

}