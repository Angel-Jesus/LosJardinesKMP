package com.pe.losjardines.navigation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pe.losjardines.components.progress.CircularLoader
import losjardineskmp.composeapp.generated.resources.Res
import losjardineskmp.composeapp.generated.resources.logoaj
import org.jetbrains.compose.resources.painterResource

@Composable
fun SplashScreen(){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier.size(300.dp),
            painter = painterResource(resource = Res.drawable.logoaj),
            contentDescription = null
        )

        Spacer(modifier = Modifier.height(8.dp))

        CircularLoader(
            size = 48.dp,
            segmentHeight = 12.dp,
            segmentCount = 12
        )
    }
}