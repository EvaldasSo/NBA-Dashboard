package com.example.nba.data.paging

import android.util.Log
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
import kotlinx.coroutines.delay
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
        return InitializeAction.SKIP_INITIAL_REFRESH
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
                    val remoteKey = nbaDB.withTransaction {
                        gamesMatchKeysDao.getRemoteKey(teamId)
                    }
                    when {
                        remoteKey == null -> null
                        remoteKey.nextPageKey == null -> return MediatorResult.Success(
                            endOfPaginationReached = true
                        )

                        else -> remoteKey.nextPageKey
                    }
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

                val gameMatchEntities = response.data.map { it.toGameMatchEntity(teamId) }
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
