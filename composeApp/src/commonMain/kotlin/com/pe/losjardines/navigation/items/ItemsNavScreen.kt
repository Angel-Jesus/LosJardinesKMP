package com.pe.losjardines.navigation.items

sealed class ItemsNavScreen(val route: String) {
    data object SplashNavScreen : ItemsNavScreen(route = "Splash")
    data object LoginNavScreen : ItemsNavScreen(route = "Login")
    data object ContentNavScreen : ItemsNavScreen(route = "Content")
}