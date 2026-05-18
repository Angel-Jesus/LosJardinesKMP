package com.pe.losjardines.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pe.losjardines.values.AppTypography
import com.pe.losjardines.values.LocalAppTypographyCore
import losjardineskmp.feature.ui.generated.resources.Res
import losjardineskmp.feature.ui.generated.resources.logoaj
import losjardineskmp.feature.ui.generated.resources.logout
import org.jetbrains.compose.resources.painterResource

@Composable
fun HeaderComponent(
    modifier: Modifier = Modifier,
    title: String,
    typography: AppTypography = LocalAppTypographyCore.current,
    logoutEnabled: Boolean = false,
    onLogout: () -> Unit = {}
){
    Box(
        modifier = modifier
    ) {
        Image(
            modifier = Modifier.size(48.dp).align(alignment = Alignment.CenterStart),
            painter = painterResource(resource = Res.drawable.logoaj),
            contentDescription = "logo",
        )

        Text(
            modifier = Modifier.align(alignment = Alignment.Center),
            text = title,
            style = typography.headerMedium
        )

        if(logoutEnabled){
            IconButton(
                modifier = Modifier.align(alignment = Alignment.CenterEnd),
                onClick = onLogout
            ){
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(Res.drawable.logout),
                    contentDescription = "logout"
                )
            }
        }
    }
}