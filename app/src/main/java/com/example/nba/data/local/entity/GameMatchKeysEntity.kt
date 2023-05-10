package com.example.nba.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.nba.data.util.Constants.GAME_MATCH_KEYS

@Entity(tableName = GAME_MATCH_KEYS)
data class GameMatchKeysEntity(
    @PrimaryKey
    val id: Int,
    val nextPageKey: Int?,
)