package com.pe.losjardines.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun LoginScreen(){
    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = 8.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Bienvenido a Los Jardines", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Inicie sesión para continuar", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Email o usuario")
    }
}