package com.example.nba.data.remote.dto

data class Visitor_team(
    val id: Int,
    val abbreviation: String,
    val city: String,
    val conference: String,
    val division: String,
    val full_name: String,
    val name: String
)