package com.example.nba.domain.use_case.game_match

import com.example.nba.domain.repository.GameMatchRepository

class GetMatchGamesUseCase(
    private val matchGameMatchRepository: GameMatchRepository
) {
    operator fun invoke(gameId: Int) = matchGameMatchRepository.getPagingDataGameMatches(gameId)
}