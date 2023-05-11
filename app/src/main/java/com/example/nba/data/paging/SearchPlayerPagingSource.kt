package com.example.nba.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.nba.data.mapper.toPlayer
import com.example.nba.data.remote.NbaApi
import com.example.nba.data.util.Constants.SEARCH_TEAM_PER_PAGE
import com.example.nba.domain.model.Player

class SearchPlayerPagingSource(
    private val nbaApi: NbaApi,
    private val query: String
) : PagingSource<Int, Player>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Player> {
        val currentPage = params.key ?: 1
        return try {
            val response = nbaApi.searchTeam(query = query, perPage = SEARCH_TEAM_PER_PAGE, page = currentPage)
            val data = response.data ?: emptyList()
            val prevKey = if (currentPage == 1) null else currentPage - 1
            val nextKey = if (data.isEmpty()) null else currentPage + 1
            if (data.isNotEmpty()) {
                LoadResult.Page(
                    data = data.map { it.toPlayer() },
                    prevKey = prevKey,
                    nextKey = nextKey
                )
            } else {
                LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null
                )
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
    override fun getRefreshKey(state: PagingState<Int, Player>): Int? {
        return state.anchorPosition
    }
}