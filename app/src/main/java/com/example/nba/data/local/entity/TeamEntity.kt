package com.example.nba.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TeamEntity(
    @PrimaryKey
    val id: Int,
    val city: String,
    val conference: String,
    val fullName: String,
)