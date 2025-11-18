package com.projeto.sas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.projeto.sas.features.components.BottomNavBar
import com.projeto.sas.features.components.BottomNavItem
import com.projeto.sas.features.components.calendar.CalendarScreen
import com.projeto.sas.features.employees.Deliveries.presentation.DonationsScreen
import com.projeto.sas.navigation.Screen
import com.projeto.sas.ui.theme.DarkGreen
import com.projeto.sas.ui.theme.Green

@Composable
fun MainApp() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val currentRoute = currentDestination?.route ?: Screen.Home.route
    val navItems = listOf(
        BottomNavItem(
            route = Screen.Home.route,
            label = stringResource(R.string.home),
            icon = Icons.Outlined.Home
        ),
        BottomNavItem(
            route = Screen.Donations.route,
            label = stringResource(R.string.products),
            icon = Icons.Outlined.Call
        ),
        BottomNavItem(
            route = Screen.Products.route,
            label = stringResource(R.string.products),
            icon = Icons.Outlined.CheckCircle
        ),
        BottomNavItem(
            route = Screen.Profile.route,
            label = stringResource(R.string.home),
            icon = Icons.Outlined.Call
        )
    )

    Scaffold(
        bottomBar = {
            BottomNavBar(
                items = navItems,
                selectedRoute = currentRoute,
                onItemSelected = { route ->
                    navController.navigate(route) {
                        popUpTo(Screen.Home.route) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                backgroundColor = Green,
                selectedBackgroundColor = DarkGreen,
                selectedContentColor = Color.White
            )
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Screen.Home.route) { HomeScreen(navController) }
            composable(Screen.Donations.route) { DonationsScreen() }
            composable(Screen.Products.route) { CalendarScreen() }
            composable(Screen.Profile.route) { ProfileScreen() }
        }
    }
}

@Composable
fun HomeScreen(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE0F7FA)),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Home Screen")
    }
}

@Composable
fun TasksScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFC8E6C9)),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Products Screen")
    }
}

@Composable
fun ProfileScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFCCBC)),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Profile Screen")
    }
}
