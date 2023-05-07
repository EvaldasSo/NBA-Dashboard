package com.example.nba.datastore

import androidx.datastore.core.DataStore
import com.example.nba.domain.model.TeamSort
import com.example.nba.domain.model.UserData
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NbaPreferencesDataSource @Inject constructor(
    private val userPreferences: DataStore<UserPreferences>,
) {
    val userData = userPreferences.data
        .map {
            UserData(
                teamSortConfig = when (it.teamSort) {
                    null,
                    TeamSortProto.UNSPECIFIED,
                    TeamSortProto.NONE,
                    TeamSortProto.UNRECOGNIZED,
                    TeamSortProto.BY_NAME -> TeamSort.NAME
                    TeamSortProto.BY_CITY -> TeamSort.CITY
                    TeamSortProto.BY_CONFERENCE -> TeamSort.CONFERENCE
                },
            )
        }

    suspend fun setTeamSort(teamSort: TeamSort) {
        userPreferences.updateData { currentPreferences ->
            currentPreferences.toBuilder().apply {
                this.teamSort = when (teamSort) {
                    TeamSort.NAME -> TeamSortProto.BY_NAME
                    TeamSort.CITY -> TeamSortProto.BY_CITY
                    TeamSort.CONFERENCE -> TeamSortProto.BY_CONFERENCE
                }
            }.build()
        }
    }

}