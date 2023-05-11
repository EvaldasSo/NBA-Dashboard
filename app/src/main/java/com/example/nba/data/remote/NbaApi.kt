package com.example.nba.data.remote


import com.example.nba.data.remote.dto.GameMatchDto
import com.example.nba.data.remote.dto.SearchTeam
import com.example.nba.data.remote.dto.TeamDto
import retrofit2.http.GET
import retrofit2.http.Query

interface NbaApi {

    @GET("teams")
    suspend fun getTeams(
        @Query("page") page: Int? = null,
        @Query("per_page") pageCount: Int
    ): TeamDto

    @GET("games")
    suspend fun getGameMatches(
        @Query("page") page: Int? = null,
        @Query("per_page") pageCount: Int? = null,
        @Query("team_ids[]") teamId: Int,
    ): GameMatchDto

    @GET("players")
    suspend fun searchTeam(
        @Query("search") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): SearchTeam

}