package com.example.nba.data.remote.dto

data class Data(
    val id: Int,
    val date: String,
    val home_team: Home_team,
    val home_team_score: Int,
    val period: Int,
    val postseason: Boolean,
    val season: Int,
    val status: String,
    val time: String,
    val visitor_team: Visitor_team,
    val visitor_team_score: Int
)