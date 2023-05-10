package com.example.nba.domain.model

data class GameMatch(
    val id: Int,
    val homeName: String,
    val homeScore: Int,
    val visitorName: String,
    val visitorScore: Int,
)