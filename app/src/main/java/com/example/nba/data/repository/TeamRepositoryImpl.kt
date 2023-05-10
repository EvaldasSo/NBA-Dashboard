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


import com.example.nba.data.local.database.NbaDatabase
import com.example.nba.data.mapper.toTeam
import com.example.nba.domain.model.Team
import com.example.nba.domain.repository.TeamRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TeamRepositoryImpl @Inject constructor(
    private val nbaDb: NbaDatabase,
) : TeamRepository {

    override fun getTeamById(id: Int): Flow<Team> {
        return nbaDb.dao.getTeamEntity(id).map {
            it.toTeam()
        }
    }
}