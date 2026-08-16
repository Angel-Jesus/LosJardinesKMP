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
    ) {
        const val ARG_RESERVATION_ID = "reservationId"

        /** Ruta que declara el argumento opcional `reservationId` (modo check-in). */
        val routeWithArgs = "$route?$ARG_RESERVATION_ID={$ARG_RESERVATION_ID}"

        /** Construye la ruta de navegación. Con [reservationId] entra en modo check-in. */
        fun createRoute(reservationId: Long? = null): String =
            if (reservationId != null) "$route?$ARG_RESERVATION_ID=$reservationId" else route
    }

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