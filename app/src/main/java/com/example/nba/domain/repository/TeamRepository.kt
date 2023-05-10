package com.example.nba.domain.repository


import com.example.nba.domain.model.Team
import kotlinx.coroutines.flow.Flow

interface TeamRepository {

    fun getTeamById(id: Int): Flow<Team>
}