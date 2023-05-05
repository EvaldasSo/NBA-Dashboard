package com.example.nba.presentation.home_screen.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.nba.presentation.home_screen.HomeRoute

const val homeNavigationRoute = "home_route"

fun NavController.navigateToHome(navOptions: NavOptions? = null) {
    this.navigate(homeNavigationRoute, navOptions)
}

fun NavGraphBuilder.homeScreen(
    onTeamClick: (String) -> Unit
) {
    composable(route = homeNavigationRoute) {
        HomeRoute(onTeamClick)
    }
}
