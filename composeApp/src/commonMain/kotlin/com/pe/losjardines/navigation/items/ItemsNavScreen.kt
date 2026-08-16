package com.pe.losjardines.navigation.items

sealed class ItemsNavScreen(val route: String) {
    data object SplashNavScreen : ItemsNavScreen(route = "Splash")
    data object LoginNavScreen : ItemsNavScreen(route = "Login")
    data object ContentNavScreen : ItemsNavScreen(route = "Content") {
        const val ARG_START_TAB = "startTab"

        /** Ruta que declara el argumento opcional `startTab` (pestaña inicial del NavHost interno). */
        val routeWithArgs = "$route?$ARG_START_TAB={$ARG_START_TAB}"

        /** Construye la ruta de navegación. Con [startTab] abre esa pestaña como destino inicial. */
        fun createRoute(startTab: String? = null): String =
            if (startTab != null) "$route?$ARG_START_TAB=$startTab" else route
    }
}