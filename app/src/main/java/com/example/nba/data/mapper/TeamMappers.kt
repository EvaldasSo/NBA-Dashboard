package com.example.nba.data.mapper

import com.example.nba.data.local.entity.TeamEntity
import com.example.nba.data.remote.dto.DataDto
import com.example.nba.domain.model.Team

fun DataDto.toTeamEntity(): TeamEntity {
    return TeamEntity(
        id = id,
        city = city,
        conference = conference,
        fullName = full_name
    )
}

fun TeamEntity.toTeam(): Team {
    return Team(
        id = id,
        city = city,
        conference = conference,
        fullName = fullName
    )
}