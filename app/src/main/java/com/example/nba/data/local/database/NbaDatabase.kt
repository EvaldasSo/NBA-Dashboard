package com.example.nba.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.nba.data.local.dao.GameMatchDao
import com.example.nba.data.local.dao.GameMatchKeysDao
import com.example.nba.data.local.dao.TeamDao
import com.example.nba.data.local.dao.TeamKeysDao
import com.example.nba.data.local.entity.GameMatchEntity
import com.example.nba.data.local.entity.GameMatchKeysEntity
import com.example.nba.data.local.entity.TeamEntity
import com.example.nba.data.local.entity.TeamKeysEntity

@Database(
    entities = [TeamEntity::class, GameMatchEntity::class, GameMatchKeysEntity::class, TeamKeysEntity::class],
    version = 9,
    exportSchema = false
)
abstract class NbaDatabase: RoomDatabase() {

    abstract val dao: TeamDao
    abstract fun teamKeysDao(): TeamKeysDao

    abstract val gameMatchDao: GameMatchDao
    abstract fun gameMatchKeysDao(): GameMatchKeysDao
}