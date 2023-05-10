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
import com.example.nba.data.local.entity.TeamEntity
import com.example.nba.data.mapper.toGameMatchEntity
import com.example.nba.data.mapper.toTeamEntity
import com.example.nba.data.remote.NbaApi
import com.example.nba.data.util.Constants
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class TeamRemoteMediator(
    private val nbaDb: NbaDatabase,
    private val nbaApi: NbaApi
) : RemoteMediator<Int, TeamEntity>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, TeamEntity>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true
                )

                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    val itemsLoaded = state.pages.sumOf { it.data.size }
                    val nextPage = (itemsLoaded / state.config.pageSize) + 1
                    nextPage
                }
            }

            val teams = nbaApi.getTeams(
                page = loadKey,
                pageCount = state.config.pageSize
            )

            nbaDb.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    nbaDb.dao.clearAll()
                }
                val teamEntities = teams.data.map { it.toTeamEntity() }
                nbaDb.dao.upsertAll(teamEntities)
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