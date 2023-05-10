package com.example.nba.domain.use_case.team

import com.example.nba.domain.model.Team
import com.example.nba.domain.repository.TeamRepository
import kotlinx.coroutines.flow.Flow

class GetTeamByIdUseCase(
    private val teamRepository: TeamRepository
) {
    operator fun invoke(id: Int): Flow<Team> {
        return teamRepository.getTeamById(id)
    }
}