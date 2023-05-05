package com.example.nba.presentation.team_detail_screen

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun TeamDetailRoute(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    viewModel: TeamDetailViewModel = hiltViewModel(),
){
    Text(text = "team id = ${viewModel.teamId}")

    TeamDetailScreen(
        modifier = modifier,
        onBackClick = onBackClick,
    )
}


@Composable
fun TeamDetailScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
){

    Button(onClick = onBackClick) {
        Text(text = "Back")
    }
}
