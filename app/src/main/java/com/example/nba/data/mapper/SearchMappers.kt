package com.example.nba.data.mapper

import com.example.nba.data.remote.dto.player.DataBean
import com.example.nba.domain.model.Player


fun DataBean.toPlayer(): Player {
    return Player(
        id = id,
        teamId = team.id,
        lastName = last_name,
        firstName = first_name,
        teamName = team.name,
    )
}