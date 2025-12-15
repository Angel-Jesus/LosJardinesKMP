package com.pe.losjardines.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pe.losjardines.navigation.items.ItemsNavScreen
import com.pe.losjardines.navigation.ui.SplashScreen
import com.pe.losjardines.navigation_content.NavContentManager
import com.pe.losjardines.presentation.login.screen.LoginScreen
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
fun NavManager(
    navigationViewModel: NavigationViewModel = koinViewModel()
){
    val navController = rememberNavController()
    val checkState by navigationViewModel.isLoggedIn.collectAsState()

    LaunchedEffect(true){
        navigationViewModel.checkSession()
    }

    LaunchedEffect(checkState){
        when(checkState){
            stateScreen.LOGIN -> navController.navigate(
                route = ItemsNavScreen.LoginNavScreen.route,
                builder = {
                    popUpTo(ItemsNavScreen.SplashNavScreen.route){
                        inclusive = true
                    }
                }
            )
            stateScreen.CONTENT -> navController.navigate(
                route = ItemsNavScreen.ContentNavScreen.route,
                builder = {
                    popUpTo(ItemsNavScreen.SplashNavScreen.route){
                        inclusive = true
                    }
                }
            )
            else -> Unit
        }
    }

    NavHost(
        navController = navController,
        startDestination = ItemsNavScreen.SplashNavScreen.route
    ){
        composable(ItemsNavScreen.SplashNavScreen.route){
            SplashScreen()
        }

        composable(ItemsNavScreen.LoginNavScreen.route){
            LoginScreen(
                isMobile = isMobile(),
                onPrincipalScreen = {
                    navController.navigate(
                        route = ItemsNavScreen.ContentNavScreen.route,
                        builder = {
                            popUpTo(ItemsNavScreen.LoginNavScreen.route){
                                inclusive = true
                            }
                        }
                    )
                }
            )
        }

        composable(ItemsNavScreen.ContentNavScreen.route){
            NavContentManager(
                onLogout = {
                    navigationViewModel.logout()
                    navController.navigate(
                        route = ItemsNavScreen.LoginNavScreen.route,
                        builder = {
                            popUpTo(ItemsNavScreen.ContentNavScreen.route){
                                inclusive = true
                            }
                        }
                    )
                }
            )
        }
    }
}

expect fun isMobile(): Boolean