package com.pe.losjardines.navigation_content.items

import losjardineskmp.composeapp.generated.resources.Res
import losjardineskmp.composeapp.generated.resources.dashboard_default
import losjardineskmp.composeapp.generated.resources.dashboard_selected
import losjardineskmp.composeapp.generated.resources.reservation_default
import losjardineskmp.composeapp.generated.resources.reservation_selected
import losjardineskmp.composeapp.generated.resources.room_default
import losjardineskmp.composeapp.generated.resources.room_selected
import losjardineskmp.composeapp.generated.resources.search_default
import losjardineskmp.composeapp.generated.resources.search_selected
import org.jetbrains.compose.resources.DrawableResource

sealed class ItemsContentNavScreen(
    val route: String,
    val title: String,
    val defaultIcon: DrawableResource? = null,
    val selectedIcon: DrawableResource? = null
) {
    data object DashboardNavScreen : ItemsContentNavScreen(
        route = "Dashboard",
        title = "Panel",
        defaultIcon = Res.drawable.dashboard_default,
        selectedIcon = Res.drawable.dashboard_selected
    )
    data object ConsultNavScreen : ItemsContentNavScreen(
        route = "Consult",
        title = "Huéspedes",
        defaultIcon = Res.drawable.search_default,
        selectedIcon = Res.drawable.search_selected
    )
    data object ReservationNavScreen : ItemsContentNavScreen(
        route = "Reservation",
        title = "Reservas",
        defaultIcon = Res.drawable.reservation_default,
        selectedIcon = Res.drawable.reservation_selected
    )

    data object RegistrationNavScreen : ItemsContentNavScreen(
        route = "Registration",
        title = "Registro"
    )

    data object ReservationRegisterNavScreen : ItemsContentNavScreen(
        route = "ReservationRegister",
        title = "Registro de reserva"
    )

    data object RoomStatusNavScreen : ItemsContentNavScreen(
        route = "RoomStatus",
        title = "Habitaciones",
        defaultIcon = Res.drawable.room_default,
        selectedIcon = Res.drawable.room_selected
    )
}