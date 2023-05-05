package com.example.nba.presentation.home_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun HomeRoute(
    onTeamClick: (String) -> Unit,
){

    HomeScreen(
        onTeamClick = onTeamClick,
    )
}


@Composable
fun HomeScreen(
    onTeamClick: (String) -> Unit,
){
    Column {
        TextButton(onClick = { onTeamClick("1") }) {
            Text(text = "1")
        }
        TextButton(onClick = { onTeamClick("2") }) {
            Text(text = "2")
        }
    }
}


