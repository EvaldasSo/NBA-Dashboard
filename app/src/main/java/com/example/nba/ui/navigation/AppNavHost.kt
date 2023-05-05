package com.example.nba.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.example.nba.presentation.home_screen.navigation.homeNavigationRoute
import com.example.nba.presentation.home_screen.navigation.homeScreen
import com.example.nba.presentation.team_detail_screen.navigation.navigateToTeamDetail
import com.example.nba.presentation.team_detail_screen.navigation.teamDetailScreen
import com.example.nba.ui.state.NbaAppState

@Composable
fun AppNavHost(
    appState: NbaAppState,
    modifier: Modifier = Modifier,
    startDestination: String = homeNavigationRoute,
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        homeScreen(
            onTeamClick = { teamId ->
                navController.navigateToTeamDetail(teamId)
            }
        )
        teamDetailScreen(
            onBackClick = navController::popBackStack,
        )
    }
}
