package com.example.nba.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.nba.data.local.dao.GameMatchDao
import com.example.nba.data.local.dao.GameMatchKeysDao
import com.example.nba.data.local.dao.TeamDao
import com.example.nba.data.local.entity.GameMatchEntity
import com.example.nba.data.local.entity.GameMatchKeysEntity
import com.example.nba.data.local.entity.TeamEntity

@Database(
    entities = [TeamEntity::class, GameMatchEntity::class, GameMatchKeysEntity::class],
    version = 8,
    exportSchema = false
)
abstract class NbaDatabase: RoomDatabase() {

    abstract val dao: TeamDao

    abstract val gameMatchDao: GameMatchDao
    abstract fun gameMatchKeysDao(): GameMatchKeysDao
}