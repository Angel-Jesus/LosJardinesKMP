package com.pe.losjardines.navigation_content.items

import losjardineskmp.composeapp.generated.resources.Res
import losjardineskmp.composeapp.generated.resources.home_default
import losjardineskmp.composeapp.generated.resources.home_selected
import losjardineskmp.composeapp.generated.resources.registration_default
import losjardineskmp.composeapp.generated.resources.registration_selected
import losjardineskmp.composeapp.generated.resources.room_default
import losjardineskmp.composeapp.generated.resources.room_selected
import losjardineskmp.composeapp.generated.resources.search_default
import losjardineskmp.composeapp.generated.resources.search_selected
import org.jetbrains.compose.resources.DrawableResource

sealed class ItemsContentNavScreen(
    val route: String,
    val title: String,
    val defaultIcon: DrawableResource,
    val selectedIcon: DrawableResource,
) {
    data object HomeNavScreen : ItemsContentNavScreen(
        route = "Home",
        title = "Inicio",
        defaultIcon = Res.drawable.home_default,
        selectedIcon = Res.drawable.home_selected
    )
    data object RegistrationNavScreen : ItemsContentNavScreen(
        route = "Registration",
        title = "Registro",
        defaultIcon = Res.drawable.registration_default,
        selectedIcon = Res.drawable.registration_selected
    )
    data object ConsultNavScreen : ItemsContentNavScreen(
        route = "Consult",
        title = "Consulta",
        defaultIcon = Res.drawable.search_default,
        selectedIcon = Res.drawable.search_selected
    )
    data object RoomStatusNavScreen : ItemsContentNavScreen(
        route = "RoomStatus",
        title = "Habitaciones",
        defaultIcon = Res.drawable.room_default,
        selectedIcon = Res.drawable.room_selected
    )
}