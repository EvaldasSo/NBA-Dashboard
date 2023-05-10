package com.example.nba.presentation.home_screen.user_preferences_dialog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nba.domain.repository.UserDataRepository
import com.example.nba.domain.model.TeamSort
import com.example.nba.domain.model.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserPrefsViewModel @Inject constructor(
    private val userDataRepository: UserDataRepository,
) : ViewModel() {

    val uiState: StateFlow<UiState> =
        userDataRepository.userData
            .map { userData ->
                UiState.Success(
                    userPreferences = UserPreferences(
                        teamSortBy = userData.teamSortConfig,
                    ),
                )
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Eagerly,
                initialValue = UiState.Loading,
            )

    fun updateTeamSort(sortBy: TeamSort) {
        viewModelScope.launch {
            userDataRepository.setTeamSort(sortBy)
        }
    }
}

sealed interface UiState {
    object Loading : UiState
    data class Success(val userPreferences: UserPreferences) : UiState
}
