package com.example.nba.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.nba.data.local.entity.TeamKeysEntity
import com.example.nba.data.util.Constants.TEAM_KEYS

@Dao
interface TeamKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(keys: TeamKeysEntity)

    @Query("SELECT * FROM $TEAM_KEYS")
    suspend fun getRemoteKey(): TeamKeysEntity?

    @Query("DELETE FROM $TEAM_KEYS")
    suspend fun clearAll()

}