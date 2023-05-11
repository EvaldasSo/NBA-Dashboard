package com.example.nba.presentation.home_screen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign

@Composable
fun TeamInfoRow(
    modifier: Modifier = Modifier,
    columnNames: List<String>,
    showImage: Boolean = false,
    onTeamClick: () -> Unit = {},
) {
    Row(
        modifier = modifier.clickable { if (showImage) onTeamClick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        columnNames.forEach {
            Text(
                text = it,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
        }

        if (showImage) {
            NbaIcon(
                modifier = Modifier
                    .fillMaxHeight(1f),
                imageVector = Icons.Filled.KeyboardArrowRight,
                tint = Color(0, 0, 0, 255),
                imageDescription = "Back"
            )
        } else {
            NbaIcon(
                modifier = Modifier
                    .fillMaxHeight(1f),
                imageVector = Icons.Filled.KeyboardArrowRight,
                tint = Color(0, 0, 0, 0),
                imageDescription = ""
            )
        }

    }
}

@Composable
fun NbaIcon(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    imageDescription: String,
    tint: Color,
) {
    Icon(
        modifier = modifier,
        imageVector = imageVector,
        tint = tint,
        contentDescription = imageDescription
    )
}