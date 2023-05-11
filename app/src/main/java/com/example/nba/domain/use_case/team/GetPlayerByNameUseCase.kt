package com.example.nba.domain.use_case.team

import com.example.nba.domain.repository.TeamRepository

class GetPlayerByNameUseCase(
    private val teamRepository: TeamRepository
) {
    operator fun invoke(query: String) = teamRepository.searchPlayer(query)
}