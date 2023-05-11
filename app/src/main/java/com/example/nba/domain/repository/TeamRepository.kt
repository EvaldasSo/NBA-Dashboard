package com.example.nba.domain.repository


import androidx.paging.PagingData
import com.example.nba.data.local.entity.TeamEntity
import com.example.nba.domain.model.Player
import com.example.nba.domain.model.Team
import com.example.nba.domain.model.TeamSort
import kotlinx.coroutines.flow.Flow

interface TeamRepository {

    fun getTeamById(id: Int): Flow<Team>
    fun getTeamPagingData(sortBy: TeamSort): Flow<PagingData<TeamEntity>>
    fun searchPlayer(query: String): Flow<PagingData<Player>>
}