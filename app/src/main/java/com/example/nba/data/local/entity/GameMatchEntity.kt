package com.example.nba.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.nba.data.util.Constants

@Entity(tableName = Constants.GAME_MATCH_TABLE)
data class GameMatchEntity(
    @PrimaryKey
    val id: Int,
    val teamId: Int,
    val homeName: String,
    val homeScore: Int,
    val visitorName: String,
    val visitorScore: Int,
)