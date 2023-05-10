package com.example.nba.data.mapper

import com.example.nba.data.local.entity.GameMatchEntity
import com.example.nba.data.remote.dto.Data
import com.example.nba.domain.model.GameMatch

fun Data.toGameMatchEntity(): GameMatchEntity {
    return GameMatchEntity(
        id = id,
        teamId = home_team.id,
        homeName = home_team.name,
        homeScore = home_team_score,
        visitorName = visitor_team.name,
        visitorScore = visitor_team_score
    )
}

fun GameMatchEntity.toGameMatch(): GameMatch {
    return GameMatch(
        id = id,
        homeName = homeName,
        homeScore = homeScore,
        visitorName = visitorName,
        visitorScore = visitorScore
    )
}

