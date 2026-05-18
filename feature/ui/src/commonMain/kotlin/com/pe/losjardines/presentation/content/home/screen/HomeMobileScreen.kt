package com.pe.losjardines.presentation.content.home.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pe.losjardines.presentation.components.HeaderComponent
import com.pe.losjardines.values.AppTypography
import com.pe.losjardines.values.LocalAppTypographyCore

@Composable
fun HomeMobileScreen(
    title: String,
    onLogout: () -> Unit,
    typography: AppTypography = LocalAppTypographyCore.current
){
    Column(modifier = Modifier.fillMaxSize()) {
        HeaderComponent(
            modifier = Modifier.fillMaxWidth()
                .height(48.dp)
                .padding(horizontal = 16.dp),
            title = title,
            typography = typography,
            logoutEnabled = true,
            onLogout = onLogout
        )
    }
}