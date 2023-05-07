package com.example.nba.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.nba.data.local.dao.TeamDao
import com.example.nba.data.local.entity.TeamEntity

@Database(
    entities = [TeamEntity::class],
    version = 1,
    exportSchema = false
)
abstract class NbaDatabase: RoomDatabase() {

    abstract val dao: TeamDao
}