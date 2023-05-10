package com.example.nba.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.nba.data.local.entity.GameMatchKeysEntity
import com.example.nba.data.util.Constants.GAME_MATCH_KEYS

@Dao
interface GameMatchKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(keys: GameMatchKeysEntity)

    @Query("SELECT * FROM $GAME_MATCH_KEYS WHERE id = :key")
    suspend fun getRemoteKey(key: Int): GameMatchKeysEntity

    @Query("DELETE FROM $GAME_MATCH_KEYS WHERE nextPageKey = :key")
    suspend fun deleteByKey(key: Int)

}