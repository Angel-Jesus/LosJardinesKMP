package com.pe.losjardines.navigation_content.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.pe.losjardines.navigation_content.items.ItemsContentNavScreen
import com.pe.losjardines.values.AppTypography
import com.pe.losjardines.values.LocalAppTypographyCore
import org.jetbrains.compose.resources.painterResource

@Composable
fun NavigationBarScreen(
    isMobile: Boolean,
    navController: NavHostController,
    content: @Composable (() -> Unit)
){
    if(isMobile){
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                BottomNavBar(navController = navController)
            },
            content = {
                Box(
                    modifier = Modifier.padding(it).fillMaxSize(),
                    content = { content() }
                )
            }
        )
    } else {

    }
}


@Composable
private fun BottomNavBar(
    navController: NavHostController,
    typography: AppTypography = LocalAppTypographyCore.current
){
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route ?: ItemsContentNavScreen.HomeNavScreen.route

    val screens = listOf(
        ItemsContentNavScreen.HomeNavScreen,
        ItemsContentNavScreen.RegistrationNavScreen,
        ItemsContentNavScreen.ConsultNavScreen,
        ItemsContentNavScreen.RoomStatusNavScreen
    )

    if (navController.shouldShowBottomBar) {
        NavigationBar {
            screens.forEach { item ->
                val isSelected = currentRoute == item.route
                NavigationBarItem(
                    icon = {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            painter = painterResource(resource = if (isSelected) item.selectedIcon else item.defaultIcon),
                            contentDescription = item.title
                        )
                    },
                    label = {
                        Text(
                            item.title,
                            style = if (isSelected) typography.bottomNavSelected else typography.bottomNavDefault
                        )
                    },
                    selected = isSelected,
                    onClick = { navigateBottomBar(navController, item.route, currentRoute) }
                )
            }
        }
    }

}

private val NavController.shouldShowBottomBar: Boolean
    get() = currentBackStackEntry?.destination?.route in listOf(
        ItemsContentNavScreen.HomeNavScreen.route,
        ItemsContentNavScreen.RegistrationNavScreen.route,
        ItemsContentNavScreen.ConsultNavScreen.route,
        ItemsContentNavScreen.RoomStatusNavScreen.route
    )

fun navigateBottomBar(navController: NavController, destination: String, current: String) {
    navController.navigate(destination) {
        popUpTo(current) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}