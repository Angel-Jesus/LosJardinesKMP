package com.pe.losjardines.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.pe.losjardines.components.textInput.TextInputAJ
import com.pe.losjardines.values.AppTypography
import com.pe.losjardines.values.BlackTextColor
import com.pe.losjardines.values.LocalAppTypographyCore
import com.pe.losjardines.values.SoftTextColor
import losjardineskmp.feature.login.ui.generated.resources.Res
import org.jetbrains.compose.resources.painterResource

@Composable
fun LoginScreen(
    typography: AppTypography = LocalAppTypographyCore.current
){

    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = 8.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier.fillMaxWidth(0.5f),
            painter = painterResource(resource = Res.drawable.logohotel),
            contentDescription = null
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Bienvenido a Los Jardines",
            textAlign = TextAlign.Center,
            style = typography.titleLarge,
            color = BlackTextColor
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Inicie sesión para continuar",
            textAlign = TextAlign.Center,
            style = typography.titleMedium,
            color = SoftTextColor
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextInputAJ(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            value = "",
            label = "Nombre de usuario",
            onValueChange = {  }
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextInputAJ(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            value = "",
            label = "Contraseña",
            onValueChange = {  },
            isTypePassword = true
        )

        Spacer(modifier = Modifier.height(16.dp))



    }
}