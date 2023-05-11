package com.example.nba.data.remote.dto

import com.example.nba.data.remote.dto.player.DataBean

data class SearchTeam(
    val data: List<DataBean>?,
    val meta: Meta
)