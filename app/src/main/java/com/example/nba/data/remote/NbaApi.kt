package com.example.nba.data.remote


import com.example.nba.data.remote.dto.TeamDto
import retrofit2.http.GET
import retrofit2.http.Query

interface NbaApi {

    @GET("teams")
    suspend fun getTeams(
        @Query("page") page: Int,
        @Query("per_page") pageCount: Int
    ): TeamDto

    companion object {
        const val BASE_URL = "https://www.balldontlie.io/api/v1/"
    }
}