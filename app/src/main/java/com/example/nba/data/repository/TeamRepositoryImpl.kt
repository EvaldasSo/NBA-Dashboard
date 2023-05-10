/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.nba.data.repository


import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.nba.data.local.database.NbaDatabase
import com.example.nba.data.local.entity.GameMatchEntity
import com.example.nba.data.local.entity.TeamEntity
import com.example.nba.data.mapper.toTeam
import com.example.nba.data.paging.GameMatchMediator
import com.example.nba.data.paging.TeamRemoteMediator
import com.example.nba.data.remote.NbaApi
import com.example.nba.data.util.Constants
import com.example.nba.domain.model.Team
import com.example.nba.domain.model.TeamSort
import com.example.nba.domain.repository.TeamRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TeamRepositoryImpl @Inject constructor(
    private val nbaDb: NbaDatabase,
    private val nbaApi: NbaApi
) : TeamRepository {

    override fun getTeamById(id: Int): Flow<Team> {
        return nbaDb.dao.getTeamEntity(id).map {
            it.toTeam()
        }
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getTeamPagingData(sortBy: TeamSort): Flow<PagingData<TeamEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = Constants.TEAM_ITEMS_PER_PAGE,
                enablePlaceholders = false
            ),
            remoteMediator = TeamRemoteMediator(
                nbaDb = nbaDb,
                nbaApi = nbaApi
            )
        ){
            val teamSort = when(sortBy) {
                TeamSort.NONE -> "none"
                TeamSort.NAME -> "fullName"
                TeamSort.CITY -> "city"
                TeamSort.CONFERENCE -> "conference"
            }

            nbaDb.dao.pagingSource(teamSort)
        }.flow
    }
}