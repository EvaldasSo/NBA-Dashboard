package com.example.nba.presentation.home_screen

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.nba.R
import com.example.nba.domain.model.Team
import com.example.nba.presentation.home_screen.search_screen.PlayerScreen
import com.example.nba.presentation.home_screen.user_preferences_dialog.UserPrefsDialog
import kotlinx.coroutines.launch

data class TabItem(
    val title: Int,
    val screen: @Composable (onTeamClick: (Int) -> Unit) -> Unit
)

val tabs = listOf(
    TabItem(
        title = R.string.home,
    ) { onTeamClick ->
        HomeScreen(
            onTeamClick = onTeamClick,
            title = R.string.home,
        )
    },
    TabItem(
        title = R.string.players,
    ) { onTeamClick ->
        PlayerScreen(
            onTeamClick = onTeamClick,
            title = R.string.players
        )
    }
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeRoute(
    onTeamClick: (Int) -> Unit,
) {
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxHeight()
    ) {
        HorizontalPager(
            pageCount = tabs.size,
            state = pagerState,
            modifier = Modifier.fillMaxHeight(1f).weight(1f)
        ) {
            tabs[pagerState.currentPage].screen(onTeamClick)
        }

        TabRow(
            selectedTabIndex = pagerState.currentPage,
        ) {
            tabs.forEachIndexed { index, item ->
                Tab(
                    selected = index == pagerState.currentPage,
                    text = { Text(text = stringResource(item.title)) },
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                )
            }
        }
    }
}
@Composable
fun list(
    onTeamClick: (Int) -> Unit = {},
    teams: LazyPagingItems<Team>,
) {
    Column(
        modifier = Modifier.fillMaxHeight(1f)
    ) {
        // Column titles
        TeamInfoRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            name = stringResource(R.string.name),
            city = stringResource(R.string.city),
            conference = stringResource(R.string.conference),
            showImage = false,
        )

        Divider(Modifier.padding(8.dp))
        // User list
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(teams) { team ->
                if (team != null) {
                    TeamInfoRow(
                        modifier = Modifier.fillMaxWidth(),
                        name = team.fullName,
                        city = team.city,
                        conference = team.conference,
                        showImage = true,
                        onTeamClick = { onTeamClick(team.id) }
                    )
                }
            }
            item {
                if(teams.loadState.append is LoadState.Loading) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@Composable
fun TeamInfoRow(
    modifier: Modifier = Modifier,
    name: String,
    city: String,
    conference: String,
    showImage: Boolean = false,
    onTeamClick: () -> Unit = {},
) {
    Row(
        modifier = modifier.clickable { if(showImage) onTeamClick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,

    ) {
        Text(
            text = name,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )
        Text(
            text = city,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )
        Text(
            text = conference,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )

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

@Preview(
    showBackground = true,
    backgroundColor = 0x989a82
)
@Composable
fun homeHeader(
    modifier: Modifier = Modifier,
    title: Int = R.string.unknown,
    buttonTitle: String = "unknown",
) {
    val showDialog = remember { mutableStateOf(false) }

    if (showDialog.value) {
        UserPrefsDialog(onDismiss = { showDialog.value = false })
    }

    Row(
        modifier = modifier.fillMaxWidth(1f),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(title),
            modifier = Modifier
                .alignByBaseline()
                .weight(1f, fill = false),
            style = MaterialTheme.typography.titleLarge,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,

            )

        Button(
            modifier = Modifier
                .padding(8.dp)
                .alignByBaseline(),
            onClick = { showDialog.value = true },
            colors = buttonColors(Color.Gray),
        ) {
            Text(text = buttonTitle)
        }
    }

}

@Preview
@Composable
fun HomeScreen(
    onTeamClick: (Int) -> Unit = {},
    title: Int = R.string.unknown,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val teams = viewModel.teamPagingFlow.collectAsLazyPagingItems()

    val context = LocalContext.current
    LaunchedEffect(key1 = teams.loadState) {
        if(teams.loadState.refresh is LoadState.Error) {
            Toast.makeText(
                context,
                "Error: " + (teams.loadState.refresh as LoadState.Error).error.message,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        Row {
            homeHeader(title = title)
        }
        if(teams.loadState.refresh is LoadState.Loading) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        } else {
            list(
                onTeamClick = onTeamClick,
                teams = teams,
            )
        }

    }
}


