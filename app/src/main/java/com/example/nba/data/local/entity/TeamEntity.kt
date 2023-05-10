package com.example.nba.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.nba.data.util.Constants

@Entity(tableName = Constants.TEAM_TABLE)
data class TeamEntity(
    @PrimaryKey
    val id: Int,
    val city: String,
    val conference: String,
    val fullName: String,
)