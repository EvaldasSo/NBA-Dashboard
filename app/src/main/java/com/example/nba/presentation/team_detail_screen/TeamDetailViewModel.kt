package com.example.nba.presentation.team_detail_screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.nba.common.decoder.StringDecoder
import com.example.nba.data.mapper.toGameMatch
import com.example.nba.domain.model.Team
import com.example.nba.domain.use_case.game_match.GameMatchUseCases
import com.example.nba.domain.use_case.team.TeamUseCases
import com.example.nba.presentation.team_detail_screen.navigation.TeamDetailArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class TeamDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    stringDecoder: StringDecoder,
    gameMatchUseCases: GameMatchUseCases,
    teamUseCases: TeamUseCases,
) : ViewModel() {

    private val teamDetailArgs: TeamDetailArgs = TeamDetailArgs(savedStateHandle, stringDecoder)

    val teamId = teamDetailArgs.teamId

    val gameMatchPagingFlow = gameMatchUseCases.getMatchGamesUseCase(teamId)
        .map { pagingData ->
            pagingData.map { it.toGameMatch() }
        }
        .cachedIn(viewModelScope)


    val teamDetailUiState: StateFlow<TeamDetailUiState> =
        teamUseCases.getTeamByIdUseCase(teamId)
            .map { team ->
                TeamDetailUiState.Success(team = team)
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = TeamDetailUiState.Loading
            )

}

sealed interface TeamDetailUiState {
    data class Success(val team: Team) : TeamDetailUiState
    object Loading : TeamDetailUiState
}