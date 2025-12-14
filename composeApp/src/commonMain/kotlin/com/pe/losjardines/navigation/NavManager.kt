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
import com.pe.losjardines.presentation.login.screen.LoginScreen
import com.pe.losjardines.presentation.registration.screen.RegistrationScreen
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
                route = ItemsNavScreen.LogiScreenNav.toString(),
                builder = {
                    popUpTo(ItemsNavScreen.SplashScreenNav.toString()){
                        inclusive = true
                    }
                }
            )
            stateScreen.CONTENT -> navController.navigate(
                route = ItemsNavScreen.RegistrationScreenNav.toString(),
                builder = {
                    popUpTo(ItemsNavScreen.SplashScreenNav.toString()){
                        inclusive = true
                    }
                }
            )
            else -> Unit
        }
    }

    NavHost(
        navController = navController,
        startDestination = ItemsNavScreen.SplashScreenNav.toString()
    ){
        composable(ItemsNavScreen.SplashScreenNav.toString()){
            SplashScreen()
        }

        composable(ItemsNavScreen.LogiScreenNav.toString()){
            LoginScreen(
                onPrincipalScreen = {
                    navController.navigate(
                        route = ItemsNavScreen.RegistrationScreenNav.toString(),
                        builder = {
                            popUpTo(ItemsNavScreen.LogiScreenNav.toString()){
                                inclusive = true
                            }
                        }
                    )
                }
            )
        }

        composable(ItemsNavScreen.RegistrationScreenNav.toString()){
            RegistrationScreen()
        }
    }

}