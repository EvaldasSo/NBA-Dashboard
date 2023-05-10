package com.example.nba.presentation.team_detail_screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.nba.R
import com.example.nba.data.local.entity.GameMatchEntity
import com.example.nba.domain.model.GameMatch
import com.example.nba.domain.model.Team
import com.example.nba.presentation.home_screen.NbaIcon
import com.example.nba.presentation.home_screen.TeamInfoRow
import com.example.nba.presentation.home_screen.homeHeader
import com.example.nba.presentation.home_screen.list

@Composable
fun TeamDetailRoute(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    viewModel: TeamDetailViewModel = hiltViewModel(),
) {
    val gameMatches = viewModel.gameMatchPagingFlow.collectAsLazyPagingItems()
    val teamDetailUiState by viewModel.teamDetailUiState.collectAsStateWithLifecycle()

    TeamDetailScreen(
        modifier = modifier,
        onBackClick = onBackClick,
        gameMatches = gameMatches,
        teamDetailUiState = teamDetailUiState,
    )
}

@Composable
fun BackIconAndText(
    modifier: Modifier = Modifier,
    title: String,
    onBackClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth(1f),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .clickable { onBackClick() },
            verticalAlignment = Alignment.CenterVertically,
        ) {
            NbaIcon(
                modifier = Modifier
                    .fillMaxHeight(1f),
                imageVector = Icons.Filled.KeyboardArrowLeft,
                tint = Color(0, 0, 0, 255),
                imageDescription = "Back to home"
            )
            Text(
                text = "Home",
                textAlign = TextAlign.Center
            )
        }

        Text(
            text = title,
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(0.8f),
        )
    }
}

@Composable
fun TeamDetailScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    gameMatches: LazyPagingItems<GameMatch>,
    teamDetailUiState: TeamDetailUiState,
) {

    val context = LocalContext.current
    LaunchedEffect(key1 = gameMatches.loadState) {
        if (gameMatches.loadState.refresh is LoadState.Error) {
            Toast.makeText(
                context,
                "Error: " + (gameMatches.loadState.refresh as LoadState.Error).error.message,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        Row {
            BackIconAndText(
                title = when (teamDetailUiState) {
                    is TeamDetailUiState.Success -> teamDetailUiState.team.fullName
                    is TeamDetailUiState.Loading -> ""
                },
                onBackClick = onBackClick
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        if (gameMatches.loadState.refresh is LoadState.Loading) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        } else {
            DetailBody(gameMatches = gameMatches)
        }

    }
}

@Composable
fun DetailBody(
    gameMatches: LazyPagingItems<GameMatch>,
) {
    Column(
        modifier = Modifier.fillMaxHeight(1f)
    ) {
        GameRowInfo(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            columNames = listOf(
                stringResource(R.string.home_name),
                stringResource(R.string.home_score),
                stringResource(R.string.visitor_name),
                stringResource(R.string.visitor_score),
            )
        )
        Divider(Modifier.padding(8.dp))
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(
                items = gameMatches,
                key = { it.id }
            ) { gameMatch ->
                if (gameMatch != null) {
                    GameRowInfo(
                        modifier = Modifier.fillMaxWidth(), columNames = listOf(
                            gameMatch.homeName,
                            gameMatch.homeScore.toString(),
                            gameMatch.visitorName,
                            gameMatch.homeScore.toString()
                        )
                    )
                }
            }
            item {
                if (gameMatches.loadState.append is LoadState.Loading) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@Composable
fun GameRowInfo(
    modifier: Modifier = Modifier,
    columNames: List<String>,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        columNames.forEach {
            Text(
                text = it,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
        }
    }
}
