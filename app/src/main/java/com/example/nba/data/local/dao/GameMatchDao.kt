package com.example.nba.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.nba.data.local.entity.GameMatchEntity
import com.example.nba.data.util.Constants.GAME_MATCH_TABLE

@Dao
interface GameMatchDao {
    @Query("SELECT * FROM $GAME_MATCH_TABLE WHERE teamId = :matchId")
    fun getPagingSource(matchId: Int): PagingSource<Int, GameMatchEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(gameMatches: List<GameMatchEntity>)

    @Query("DELETE FROM $GAME_MATCH_TABLE WHERE teamId = :matchId")
    suspend fun deleteByMatchId(matchId: Int)

}
