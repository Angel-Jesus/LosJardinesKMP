package com.pe.losjardines.navigation.items

sealed class ItemsNavScreen {
    data object SplashScreenNav: ItemsNavScreen()
    data object LogiScreenNav: ItemsNavScreen()
    data object RegistrationScreenNav: ItemsNavScreen()
}