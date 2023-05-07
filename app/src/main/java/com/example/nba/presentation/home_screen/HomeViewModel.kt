package com.example.nba.presentation.home_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.nba.data.local.entity.TeamEntity
import com.example.nba.data.mapper.toTeam
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    pager: Pager<Int, TeamEntity> //can't map through domain layer needs direct access of data.
): ViewModel() {

    val teamPagingFlow = pager
        .flow
        .map { pagingData ->
            pagingData.map { it.toTeam() }
        }
        .cachedIn(viewModelScope)
}