package com.example.nba.presentation.home_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.nba.data.mapper.toTeam
import com.example.nba.domain.model.UserPreferences
import com.example.nba.domain.repository.UserDataRepository
import com.example.nba.domain.use_case.team.TeamUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    teamUseCases: TeamUseCases,
    userDataRepository: UserDataRepository,
) : ViewModel() {

    @OptIn(ExperimentalCoroutinesApi::class)
    val teamPagingFlow = userDataRepository.userData.flatMapLatest { teamSort ->
        teamUseCases.getTeamsUseCase(teamSort.teamSortConfig)
            .map { pagingData ->
                pagingData.map { it.toTeam() }

            }
            .cachedIn(viewModelScope)
    }

    val preferenceUiState: StateFlow<PreferenceUiState> =
        userDataRepository.userData
            .map { userData ->
                PreferenceUiState.Success(
                    userPreferences = UserPreferences(
                        teamSortBy = userData.teamSortConfig,
                    ),
                )
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Eagerly,
                initialValue = PreferenceUiState.Loading,
            )

}

sealed interface PreferenceUiState {
    object Loading : PreferenceUiState
    data class Success(val userPreferences: UserPreferences) : PreferenceUiState
}