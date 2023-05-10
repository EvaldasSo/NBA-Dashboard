package com.example.nba.presentation.home_screen.search_screen


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.nba.R

@Preview
@Composable
fun PlayerScreen(
    onTeamClick: (Int) -> Unit = {},
    title: Int = R.string.unknown,
) {
    Column(modifier = Modifier.fillMaxSize(1f)) {
        Box(
            modifier = Modifier.weight(1f).fillMaxSize()
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = "Player screen"
            )
        }

        Row() {
            Text(text = stringResource(title))
        }



    }

}
