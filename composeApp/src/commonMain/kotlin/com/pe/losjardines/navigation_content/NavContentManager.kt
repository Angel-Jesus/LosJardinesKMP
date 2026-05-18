package com.pe.losjardines.navigation_content

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pe.losjardines.navigation.isMobile
import com.pe.losjardines.navigation_content.items.ItemsContentNavScreen
import com.pe.losjardines.navigation_content.ui.NavigationBarScreen
import com.pe.losjardines.presentation.content.consultation.screen.ConsultationMobileScreen
import com.pe.losjardines.presentation.content.home.screen.HomeMobileScreen
import com.pe.losjardines.presentation.content.registration.screen.RegistrationMobileScreen
import com.pe.losjardines.presentation.content.room.screen.RoomMobileScreen
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
fun NavContentManager(
    onLogout: () -> Unit
){
    val navController = rememberNavController()

    NavigationBarScreen(
        isMobile = isMobile(),
        navController = navController,
        content = {
            NavHost(
                navController = navController,
                startDestination = ItemsContentNavScreen.HomeNavScreen.route
            ){
                composable(ItemsContentNavScreen.HomeNavScreen.route){
                    HomeMobileScreen(
                        title = ItemsContentNavScreen.HomeNavScreen.title,
                        onLogout = onLogout
                    )
                }

                composable(ItemsContentNavScreen.RegistrationNavScreen.route){
                    RegistrationMobileScreen(
                        title = ItemsContentNavScreen.RegistrationNavScreen.title,
                    )
                }

                composable(ItemsContentNavScreen.ConsultNavScreen.route){
                    ConsultationMobileScreen(
                        title = ItemsContentNavScreen.ConsultNavScreen.title,
                    )
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