package com.pe.losjardines.navigation_content

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pe.losjardines.navigation.isMobile
import com.pe.losjardines.navigation.items.ItemsNavScreen
import com.pe.losjardines.navigation_content.items.ItemsContentNavScreen
import com.pe.losjardines.navigation_content.ui.NavigationBarScreen
import com.pe.losjardines.presentation.content.consultation.screen.ConsultationMobileScreen
import com.pe.losjardines.presentation.content.home.screen.HomeMobileScreen
import com.pe.losjardines.presentation.content.registration.screen.RegistrationMobileScreen
import com.pe.losjardines.presentation.content.room.screen.RoomMobileScreen

fun NavGraphBuilder.navContentMobileManager(
    onLogout: () -> Unit
){

    composable(ItemsContentNavScreen.RegistrationNavScreen.route){
        RegistrationMobileScreen(
            title = ItemsContentNavScreen.RegistrationNavScreen.title,
        )
    }

    composable(ItemsNavScreen.ContentNavScreen.route){
        val contentNavController = rememberNavController()
        NavigationBarScreen(
            isMobile = true,
            navController = contentNavController,
            content = {
                NavHost(
                    navController = contentNavController,
                    startDestination = ItemsContentNavScreen.DashboardNavScreen.route
                ){
                    composable(ItemsContentNavScreen.DashboardNavScreen.route){
                        HomeMobileScreen(
                            title = ItemsContentNavScreen.DashboardNavScreen.title,
                            onLogout = onLogout
                        )
                    }

                    composable(ItemsContentNavScreen.ConsultNavScreen.route){
                        ConsultationMobileScreen(
                            title = ItemsContentNavScreen.ConsultNavScreen.title,
                        )
                    }

                    composable(ItemsContentNavScreen.ReservationNavScreen.route){

                    }

                    composable(ItemsContentNavScreen.RoomStatusNavScreen.route){
                        RoomMobileScreen(
                            title = ItemsContentNavScreen.RoomStatusNavScreen.title,
                        )
                    }
                }
            }
        )
    }
}