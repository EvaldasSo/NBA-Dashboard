package com.example.nba.domain.model

data class Player(
    val id: Int,
    val teamId: Int,
    val firstName: String,
    val lastName: String,
    val teamName: String,
)