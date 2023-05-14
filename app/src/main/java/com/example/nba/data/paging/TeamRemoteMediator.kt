package com.example.nba.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.nba.data.local.database.NbaDatabase
import com.example.nba.data.local.entity.TeamEntity
import com.example.nba.data.local.entity.TeamKeysEntity
import com.example.nba.data.mapper.toTeamEntity
import com.example.nba.data.remote.NbaApi
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class TeamRemoteMediator(
    private val nbaDb: NbaDatabase,
    private val nbaApi: NbaApi
) : RemoteMediator<Int, TeamEntity>() {

    private val teamKeysDao = nbaDb.teamKeysDao()

    override suspend fun initialize(): InitializeAction {
        // Require that remote REFRESH is launched on initial load and succeeds before launching
        // remote PREPEND / APPEND.
        return InitializeAction.SKIP_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, TeamEntity>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true
                )

                LoadType.APPEND -> {
                    val remoteKey = nbaDb.withTransaction {
                        teamKeysDao.getRemoteKey()
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

            val teams = nbaApi.getTeams(
                page = loadKey,
                pageCount = state.config.pageSize
            )

            nbaDb.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    nbaDb.dao.clearAll()
                    teamKeysDao.clearAll()
                }
                val teamEntities = teams.data.map { it.toTeamEntity() }
                nbaDb.dao.upsertAll(teamEntities)
                teamKeysDao.insert(TeamKeysEntity(id = 1, nextPageKey = teams.meta.next_page))
            }

            MediatorResult.Success(
                endOfPaginationReached = teams.data.isEmpty()
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

}