package com.example.nba.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.nba.data.util.Constants.TEAM_KEYS

@Entity(tableName = TEAM_KEYS)
data class TeamKeysEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val nextPageKey: Int?,
)