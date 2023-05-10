package com.example.nba.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.nba.data.local.entity.TeamEntity
import com.example.nba.data.util.Constants.TEAM_TABLE
import kotlinx.coroutines.flow.Flow

@Dao
interface TeamDao {

    @Upsert
    suspend fun upsertAll(teams: List<TeamEntity>)

    @Query("SELECT * FROM $TEAM_TABLE")
    fun pagingSource(): PagingSource<Int, TeamEntity>

    @Query("DELETE FROM $TEAM_TABLE")
    suspend fun clearAll()

    @Query("SELECT * FROM $TEAM_TABLE WHERE id = :id")
    fun getTeamEntity(id: Int): Flow<TeamEntity>
}