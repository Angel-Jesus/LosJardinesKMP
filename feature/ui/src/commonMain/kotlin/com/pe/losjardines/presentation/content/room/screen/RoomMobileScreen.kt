package com.pe.losjardines.presentation.content.room.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import com.pe.losjardines.components.loading.LoadingAJ
import com.pe.losjardines.presentation.components.HeaderComponent
import com.pe.losjardines.presentation.content.room.contract.RoomEvent
import com.pe.losjardines.presentation.content.room.viewmodel.RoomViewModel
import com.pe.losjardines.usecases.model.RoomDto
import com.pe.losjardines.values.AppTheme
import com.pe.losjardines.values.AppTypography
import com.pe.losjardines.values.BackgroundAvailableRoom
import com.pe.losjardines.values.BackgroundUnavailableRoom
import com.pe.losjardines.values.BorderAvailableRoom
import com.pe.losjardines.values.BorderUnavailableRoom
import com.pe.losjardines.values.LocalAppTypographyCore
import losjardineskmp.feature.ui.generated.resources.Res
import losjardineskmp.feature.ui.generated.resources.ic_room
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun RoomMobileScreen(
    title: String,
    viewModel: RoomViewModel = koinViewModel(),
    typography: AppTypography = LocalAppTypographyCore.current
){
    val uiState by viewModel.uiState.collectAsState()

    LifecycleEventEffect(Lifecycle.Event.ON_RESUME){
        viewModel.onEvent(RoomEvent.GetRoomState)
    }

    if(uiState.loading){
        LoadingAJ(
            title = "Información de Habitaciones",
            subtitle = "Obteniendo información"
        )
    }

    Column(modifier = Modifier.fillMaxSize()) {
        HeaderComponent(
            modifier = Modifier.fillMaxWidth().height(48.dp).padding(horizontal = 16.dp),
            title = title
        )

        Spacer(modifier = Modifier.size(16.dp))

        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 160.dp),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                items = uiState.rooms,
                key = { it.room }
            ) { item ->
                RoomCard(
                    data = item,
                    onClick = {
                        viewModel.onEvent(RoomEvent.UpdateStateRoom(item.copy(state = !item.state)))
                    },
                    typography = typography
                )
            }
        }
    }
}

@Composable
fun RoomCard(
    modifier: Modifier = Modifier,
    data: RoomDto,
    onClick: () -> Unit = {},
    typography: AppTypography
){
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = if (data.state) BackgroundAvailableRoom else BackgroundUnavailableRoom),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.elevatedCardElevation(0.dp),
        border = BorderStroke(2.dp, if (data.state) BorderAvailableRoom else BorderUnavailableRoom),
        onClick = onClick
    ){
        Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                modifier = Modifier.size(64.dp).align(Alignment.CenterHorizontally),
                painter = painterResource(Res.drawable.ic_room),
                contentDescription = null,
            )

            Spacer(modifier = Modifier.size(8.dp))

            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = typography.bodyLarge,
                text = "Habitación: ${data.room}"
            )

            Spacer(modifier = Modifier.size(8.dp))

            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = typography.bottomNavDefault,
                text = if (data.state) "Disponible" else "Ocupado",
                color = if (data.state) BorderAvailableRoom else BorderUnavailableRoom
            )

            Spacer(modifier = Modifier.size(8.dp))

            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = typography.bottomNavSelected,
                text = "Precio: S/${data.price}"
            )
        }
    }
}

@Preview
@Composable
private fun RoomCardPreview(){
    AppTheme {
        RoomCard(
            data = RoomDto("105",35, false),
            typography = LocalAppTypographyCore.current
        )
    }
}