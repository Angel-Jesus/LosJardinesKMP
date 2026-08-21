package com.pe.losjardines.presentation.content.home.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import com.pe.losjardines.components.buttom.ButtonAJ
import com.pe.losjardines.components.loading.LoadingAJ
import com.pe.losjardines.presentation.components.HeaderComponent
import com.pe.losjardines.presentation.components.ImageDescriptionItem
import com.pe.losjardines.presentation.components.ItemCard
import com.pe.losjardines.presentation.content.home.contract.HomeEvent
import com.pe.losjardines.presentation.content.home.contract.HomeUiState
import com.pe.losjardines.presentation.content.home.viewmodel.HomeViewModel
import com.pe.losjardines.usecases.model.ReservationDto
import com.pe.losjardines.usecases.model.occupiedDays
import com.pe.losjardines.utils.orZero
import com.pe.losjardines.values.AppTheme
import com.pe.losjardines.values.AppTypography
import com.pe.losjardines.values.BackgroundBrandColor
import com.pe.losjardines.values.LocalAppTypographyCore
import com.pe.losjardines.values.SidebarBorderDark
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeMobileScreen(
    title: String,
    onLogout: () -> Unit,
    onCheckIn: (ReservationDto) -> Unit = {},
    viewModel: HomeViewModel = koinViewModel(),
){
    val uiState by viewModel.uiState.collectAsState()

    LifecycleEventEffect(Lifecycle.Event.ON_RESUME){
        viewModel.onEvent(HomeEvent.GetSummary)
    }

    if(uiState.isLoading){
        LoadingAJ(
            title = "Actualización",
            subtitle = "Obteniendo información de los clientes"
        )
    }

    HomeMobileContent(
        title = title,
        dispatcher = viewModel::onEvent,
        uiState = uiState,
        onLogout = onLogout,
        onCheckIn = onCheckIn
    )
}

@Composable
private fun HomeMobileContent(
    uiState: HomeUiState,
    title: String,
    dispatcher: (HomeEvent) -> Unit,
    onLogout: () -> Unit,
    onCheckIn: (ReservationDto) -> Unit = {},
    typography: AppTypography = LocalAppTypographyCore.current
) {
    Column(modifier = Modifier.fillMaxSize().padding(start = 16.dp, end = 16.dp, bottom = 16.dp)) {
        HeaderComponent(
            modifier = Modifier.fillMaxWidth().height(48.dp),
            title = title,
            logoutEnabled = true,
            onLogout = onLogout
        )

        Spacer(modifier = Modifier.height(16.dp))

        SummaryRegister(
            reservation = uiState.reservation,
            available =  uiState.available,
            reservationNow = uiState.reservationNow,
            dispatcher = dispatcher
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Ultimas reservas",
            style = typography.headerSmall
        )

        Spacer(modifier = Modifier.height(12.dp))

        ReservationSection(
            modifier = Modifier.weight(1f),
            reservations = uiState.reservations,
            onCheckIn = onCheckIn
        )

        Spacer(modifier = Modifier.height(16.dp))

        ButtonAJ(
            modifier = Modifier.fillMaxWidth(),
            text = "Generar reporte",
            onClick = {
                dispatcher(HomeEvent.GenerateReport())
            }
        )
    }
}

@Composable
private fun ReservationSection(
    modifier: Modifier = Modifier,
    typography: AppTypography = LocalAppTypographyCore.current,
    reservations: List<ReservationDto>,
    onCheckIn: (ReservationDto) -> Unit = {}
){
    if(reservations.isNotEmpty()){
        LazyColumn(modifier = modifier) {
            items(reservations){ reservation ->
                ImageDescriptionItem(
                    modifier = Modifier.fillMaxWidth(),
                    name = reservation.userName,
                    room = reservation.room,
                    days = reservation.occupiedDays().toString(),
                    date = reservation.dateEnter,
                    onClick = {
                        onCheckIn(reservation)
                    }
                )
            }
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .border(BorderStroke(1.dp, SidebarBorderDark), RoundedCornerShape(23.dp))
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            Icon(
                imageVector = Icons.Default.CalendarMonth,
                contentDescription = null
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "No se encontraron reservas disponibles",
                style = typography.bodyMedium
            )
        }
    }
}

@Composable
private fun SummaryRegister(
    reservation: String,
    available: String,
    reservationNow: String,
    dispatcher: (HomeEvent) -> Unit = {},
    typography: AppTypography = LocalAppTypographyCore.current
) {

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(
            text = "Resumen del mes",
            style = typography.headerSmall
        )

        Text(
            text = "Actualizar",
            style = typography.buttonMedium,
            color = BackgroundBrandColor,
            modifier = Modifier.clickable {
                dispatcher(HomeEvent.UpdateRegister)
            }
        )
    }

    Spacer(modifier = Modifier.height(16.dp))

    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        ItemCard(
            modifier = Modifier.weight(1f),
            text = "Reservaciones",
            value = reservation.orZero()
        )

        ItemCard(
            modifier = Modifier.weight(1f),
            text = "Disponibles hoy",
            value = available.orZero()
        )
    }

    Spacer(modifier = Modifier.height(16.dp))

    Row {
        ItemCard(
            modifier = Modifier.weight(0.5f),
            text = "Reservas para hoy",
            value = reservationNow.orZero()
        )
    }
}

@Preview
@Composable
private fun HomeMobileContentPreview(){
    val reservationMocks = listOf(
        ReservationDto(
            collection = "reservations",
            id = 1L,
            idFirebase = "abc123XYZ",
            userName = "Carlos Mendoza",
            dateEnter = "2024-12-01",
            dateExit = "2024-12-05",
            fee = "250.00",
            sex = "M",
            typeDocument = "DNI",
            numberDocument = "12345678",
            country = "Perú",
            region = "Lima",
            typeRoom = "Matrimonial",
            room = "101",
            observation = "Cliente frecuente, prefiere vista al jardín",
            companions = listOf("Ana Mendoza", "Luis Mendoza")
        ),
        ReservationDto(
            collection = "reservations",
            id = 2L,
            idFirebase = "def456ABC",
            userName = "María Torres",
            dateEnter = "2024-12-10",
            dateExit = "2024-12-12",
            fee = "180.00",
            sex = "F",
            typeDocument = "Pasaporte",
            numberDocument = "PE987654",
            country = "Colombia",
            region = "Bogotá",
            typeRoom = "Doble",
            room = "205",
            observation = "Llegada tardía después de las 10pm",
            companions = listOf("Jorge Torres")
        ),
        ReservationDto(
            collection = "reservations",
            id = 3L,
            idFirebase = "ghi789DEF",
            userName = "Roberto Silva",
            dateEnter = "2024-12-15",
            dateExit = "2024-12-20",
            fee = "320.00",
            sex = "M",
            typeDocument = "Carnet de Extranjería",
            numberDocument = "CE456123",
            country = "Argentina",
            region = "Buenos Aires",
            typeRoom = "Simple",
            room = "308",
            observation = "Sin observaciones",
            companions = emptyList()
        ),
        ReservationDto(
            collection = "reservations",
            id = 4L,
            idFirebase = "jkl012GHI",
            userName = "Lucía Ramírez",
            dateEnter = "2024-12-22",
            dateExit = "2024-12-26",
            fee = "410.00",
            sex = "F",
            typeDocument = "DNI",
            numberDocument = "87654321",
            country = "Perú",
            region = "Cusco",
            typeRoom = "Triple",
            room = "402",
            observation = "Requiere cuna adicional para bebé",
            companions = listOf("Pedro Ramírez", "Sofia Ramírez", "Miguel Ramírez")
        )
    )

    AppTheme{
        Box(modifier = Modifier.background(Color.White)){
            HomeMobileContent(
                title = "Home",
                dispatcher = {},
                onLogout = {},
                uiState = HomeUiState(
                    reservation = "30",
                    reservationNow = "10",
                    available = "10",
                    reservations = emptyList()
                )
            )
        }
    }
}