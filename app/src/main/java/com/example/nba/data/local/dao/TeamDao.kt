package com.example.nba.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.nba.data.local.entity.TeamEntity

@Dao
interface TeamDao {

    @Upsert
    suspend fun upsertAll(teams: List<TeamEntity>)

    @Query("SELECT * FROM teamentity")
    fun pagingSource(): PagingSource<Int, TeamEntity>

    @Query("DELETE FROM teamentity")
    suspend fun clearAll()
}