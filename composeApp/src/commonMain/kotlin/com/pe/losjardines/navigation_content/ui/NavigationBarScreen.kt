package com.pe.losjardines.navigation_content.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.pe.losjardines.navigation_content.items.ItemsContentNavScreen
import com.pe.losjardines.values.AppTypography
import com.pe.losjardines.values.BackgroundBrandColor
import com.pe.losjardines.values.LocalAppTypographyCore
import losjardineskmp.composeapp.generated.resources.Res
import losjardineskmp.composeapp.generated.resources.logoaj
import org.jetbrains.compose.resources.painterResource

@Composable
fun NavigationBarScreen(
    navController: NavHostController,
    onRegisterClick: (String) -> Unit,
    content: @Composable (() -> Unit)
){
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomNavBar(navController = navController)
        },
        floatingActionButton = {
            RegisterFloatingActionButton(
                currentRoute = currentRoute,
                onRegisterClick = onRegisterClick
            )
        },
        content = {
            Box(
                modifier = Modifier.padding(it).fillMaxSize(),
                content = { content() }
            )
        }
    )
}

@Composable
private fun RegisterFloatingActionButton(
    currentRoute: String?,
    onRegisterClick: (String) -> Unit
){
    val registerRoute = when (currentRoute) {
        ItemsContentNavScreen.ConsultNavScreen.route -> ItemsContentNavScreen.RegistrationNavScreen.route
        ItemsContentNavScreen.ReservationNavScreen.route -> ItemsContentNavScreen.ReservationRegisterNavScreen.route
        else -> null
    } ?: return

    FloatingActionButton(
        onClick = { onRegisterClick(registerRoute) },
        containerColor = BackgroundBrandColor,
        contentColor = Color.White
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Registrar"
        )
    }
}


@Composable
private fun BottomNavBar(
    navController: NavHostController,
    typography: AppTypography = LocalAppTypographyCore.current
){
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route ?: ItemsContentNavScreen.DashboardNavScreen.route

    val screens = listOf(
        ItemsContentNavScreen.DashboardNavScreen,
        ItemsContentNavScreen.ReservationNavScreen,
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
                            painter = painterResource(resource = if (isSelected) {
                                item.selectedIcon ?: Res.drawable.logoaj
                            } else {
                                item.defaultIcon ?: Res.drawable.logoaj
                            }),
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
                    onClick = { navigateBottomBar(navController, item.route) }
                )
            }
        }
    }

}

private val NavController.shouldShowBottomBar: Boolean
    get() = currentBackStackEntry?.destination?.route in listOf(
        ItemsContentNavScreen.DashboardNavScreen.route,
        ItemsContentNavScreen.ReservationNavScreen.route,
        ItemsContentNavScreen.ConsultNavScreen.route,
        ItemsContentNavScreen.RoomStatusNavScreen.route
    )

fun navigateBottomBar(navController: NavController, destination: String) {
    navController.navigate(destination) {
        popUpTo(navController.graph.findStartDestination().route.orEmpty()) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}