package com.example.nba.presentation.home_screen.search_screen


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.ExperimentalPagingApi
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.nba.R
import com.example.nba.domain.model.Player
import com.example.nba.presentation.home_screen.components.TeamInfoRow


@OptIn(ExperimentalPagingApi::class)
@Composable
fun PlayerScreen(
    onTeamClick: (Int) -> Unit,
    title: Int = R.string.unknown,
    searchViewModel: PlayerViewModel = hiltViewModel()
) {
    val searchQuery by searchViewModel.searchQuery
    val searchedImages = searchViewModel.searchedImages.collectAsLazyPagingItems()

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = stringResource(title),
            fontSize = 32.sp,
            fontWeight = FontWeight.SemiBold,
        )
        Spacer(modifier = Modifier.height(16.dp))
        SearchWidget(
            text = searchQuery,
            onTextChange = {
                searchViewModel.updateSearchQuery(query = it)
            },
            onSearchClicked = {
                searchViewModel.searchHeroes(query = it)
            },
            onCloseClicked = {
                { }
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        PlayerBodyContent(items = searchedImages, onTeamClick = onTeamClick)
    }

}

@Composable
fun PlayerBodyContent(
    items: LazyPagingItems<Player>,
    onTeamClick: (Int) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxHeight(1f)
    ) {
        // Column titles
        TeamInfoRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            columnNames = listOf(
                stringResource(R.string.first_name),
                stringResource(R.string.last_name),
                stringResource(R.string.team),
            ),
            showImage = false,
        )

        Divider(Modifier.padding(8.dp))
        // User list
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(items) { item ->
                if (item != null) {
                    TeamInfoRow(
                        modifier = Modifier.fillMaxWidth(),
                        columnNames = listOf(
                            item.firstName,
                            item.lastName,
                            item.teamName,
                        ),
                        showImage = true,
                        onTeamClick = { onTeamClick(item.teamId) }
                    )
                }
            }
        }
    }
}
