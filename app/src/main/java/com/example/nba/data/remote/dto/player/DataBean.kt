package com.example.nba.data.remote.dto.player

import com.example.nba.data.remote.dto.Team

data class DataBean(
    val id: Int,
    val first_name: String,
    val last_name: String,
    val position: String,
    val team: Team
)