package com.example.nba.domain.use_case.team

import com.example.nba.domain.model.TeamSort
import com.example.nba.domain.repository.TeamRepository

class GetTeamsUseCase(
    private val teamRepository: TeamRepository
) {
    operator fun invoke(sortBy: TeamSort) = teamRepository.getTeamPagingData(sortBy)
}