package com.pe.losjardines.navigation_content

import androidx.navigation.NavHostController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.pe.losjardines.navigation.items.ItemsNavScreen
import com.pe.losjardines.navigation_content.items.ItemsContentNavScreen
import com.pe.losjardines.navigation_content.ui.NavigationBarScreen
import com.pe.losjardines.presentation.content.consultation.screen.ConsultationMobileScreen
import com.pe.losjardines.presentation.content.home.screen.HomeMobileScreen
import com.pe.losjardines.presentation.content.registration.screen.RegistrationMobileScreen
import com.pe.losjardines.presentation.content.reservation.register.screen.ReservationRegisterMobileScreen
import com.pe.losjardines.presentation.content.reservation.screen.ReservationMobileScreen
import com.pe.losjardines.presentation.content.room.screen.RoomMobileScreen

fun NavGraphBuilder.navContentMobileManager(
    navController: NavHostController,
    onLogout: () -> Unit
){

    composable(
        route = ItemsContentNavScreen.RegistrationNavScreen.routeWithArgs,
        arguments = listOf(
            navArgument(ItemsContentNavScreen.RegistrationNavScreen.ARG_RESERVATION_ID) {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            }
        )
    ){ backStackEntry ->
        val reservationId = backStackEntry.arguments
            ?.getString(ItemsContentNavScreen.RegistrationNavScreen.ARG_RESERVATION_ID)
            ?.toLongOrNull()

        RegistrationMobileScreen(
            title = ItemsContentNavScreen.RegistrationNavScreen.title,
            reservationId = reservationId,
            onBack = { navController.popBackStack() },
            onCompleted = {
                navController.navigate(
                    ItemsNavScreen.ContentNavScreen.createRoute(
                        ItemsContentNavScreen.ConsultNavScreen.route
                    )
                ) {
                    popUpTo(ItemsNavScreen.ContentNavScreen.route) { inclusive = true }
                    launchSingleTop = true
                }
            }
        )
    }

    composable(ItemsContentNavScreen.ReservationRegisterNavScreen.route){
        ReservationRegisterMobileScreen(
            title = ItemsContentNavScreen.ReservationRegisterNavScreen.title,
            onBack = { navController.popBackStack() }
        )
    }

    composable(
        route = ItemsNavScreen.ContentNavScreen.routeWithArgs,
        arguments = listOf(
            navArgument(ItemsNavScreen.ContentNavScreen.ARG_START_TAB) {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            }
        )
    ){ backStackEntry ->
        val startTab = backStackEntry.arguments
            ?.getString(ItemsNavScreen.ContentNavScreen.ARG_START_TAB)
            ?: ItemsContentNavScreen.DashboardNavScreen.route

        val contentNavController = rememberNavController()
        NavigationBarScreen(
            navController = contentNavController,
            onRegisterClick = { route ->
                navController.navigate(route) {
                    launchSingleTop = true
                }
            },
            content = {
                NavHost(
                    navController = contentNavController,
                    startDestination = startTab
                ){
                    composable(ItemsContentNavScreen.DashboardNavScreen.route){
                        HomeMobileScreen(
                            title = ItemsContentNavScreen.DashboardNavScreen.title,
                            onLogout = onLogout,
                            onCheckIn = { reservation ->
                                navController.navigate(
                                    ItemsContentNavScreen.RegistrationNavScreen.createRoute(reservation.id)
                                ) {
                                    launchSingleTop = true
                                }
                            }
                        )
                    }

                    composable(ItemsContentNavScreen.ConsultNavScreen.route){
                        ConsultationMobileScreen(title = ItemsContentNavScreen.ConsultNavScreen.title)
                    }

                    composable(ItemsContentNavScreen.ReservationNavScreen.route){
                        ReservationMobileScreen(
                            title = ItemsContentNavScreen.ReservationNavScreen.title,
                            onCheckIn = { reservation ->
                                navController.navigate(
                                    ItemsContentNavScreen.RegistrationNavScreen.createRoute(reservation.id)
                                ) {
                                    launchSingleTop = true
                                }
                            }
                        )
                    }

                    composable(ItemsContentNavScreen.RoomStatusNavScreen.route){
                        RoomMobileScreen(title = ItemsContentNavScreen.RoomStatusNavScreen.title)
                    }
                }
            }
        )
    }
}