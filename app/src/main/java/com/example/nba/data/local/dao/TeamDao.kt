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

    //TODO::Tight coupling
    @Query("""
    SELECT * FROM team_table
    ORDER BY
        NULLIF(TRIM(CASE :sortBy
            WHEN 'city' THEN city
            WHEN 'conference' THEN conference
            WHEN 'fullName' THEN fullName
            WHEN 'none' THEN null
        END), '') IS NULL ASC,
        TRIM(CASE :sortBy
            WHEN 'city' THEN city
            WHEN 'conference' THEN conference
            WHEN 'fullName' THEN fullName
            WHEN 'none' THEN null
        END)
""")
    fun pagingSource(sortBy: String): PagingSource<Int, TeamEntity>

    @Query("DELETE FROM $TEAM_TABLE")
    suspend fun clearAll()

    @Query("SELECT * FROM $TEAM_TABLE WHERE id = :id")
    fun getTeamEntity(id: Int): Flow<TeamEntity>
}