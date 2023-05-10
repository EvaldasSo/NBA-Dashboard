package com.example.nba.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.nba.data.local.database.NbaDatabase
import com.example.nba.data.local.entity.GameMatchEntity
import com.example.nba.data.local.entity.GameMatchKeysEntity
import com.example.nba.data.mapper.toGameMatchEntity
import com.example.nba.data.remote.NbaApi
import com.example.nba.data.util.Constants.GAME_MATCH_PER_PAGE
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class GameMatchMediator(
    private val teamId: Int,
    private val nbaDB: NbaDatabase,
    private val nbaApi: NbaApi
) : RemoteMediator<Int, GameMatchEntity>() {

    private val gameMatchDao = nbaDB.gameMatchDao
    private val gamesMatchKeysDao = nbaDB.gameMatchKeysDao()

    override suspend fun initialize(): InitializeAction {
        // Require that remote REFRESH is launched on initial load and succeeds before launching
        // remote PREPEND / APPEND.
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, GameMatchEntity>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    // Query DB for SubredditRemoteKey for the subreddit.
                    // SubredditRemoteKey is a wrapper object we use to keep track of page keys we
                    // receive from the Reddit API to fetch the next or previous page.
                    val remoteKey = nbaDB.withTransaction {
                        gamesMatchKeysDao.getRemoteKey(teamId)
                    }

                    // We must explicitly check if the page key is null when appending, since the
                    // Reddit API informs the end of the list by returning null for page key, but
                    // passing a null key to Reddit API will fetch the initial page.
                    if (remoteKey.nextPageKey == null) {
                        return MediatorResult.Success(endOfPaginationReached = true)
                    }

                    remoteKey.nextPageKey
                }
            }

            val response = nbaApi.getGameMatches(
                teamId = teamId,
                page = page,
                pageCount = GAME_MATCH_PER_PAGE
            )

            nbaDB.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    gameMatchDao.deleteByMatchId(teamId)
                    gamesMatchKeysDao.deleteByKey(teamId)
                }

                val gameMatchEntities = response.data.map { it.toGameMatchEntity() }
                gameMatchDao.insertAll(gameMatches = gameMatchEntities)
                gamesMatchKeysDao.insert(GameMatchKeysEntity(id = teamId, response.meta.next_page))
            }

            MediatorResult.Success(
                endOfPaginationReached = response.data.isEmpty()
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}
