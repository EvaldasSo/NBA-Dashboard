package com.example.nba.presentation.team_detail_screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.nba.common.decoder.StringDecoder
import com.example.nba.presentation.team_detail_screen.navigation.TeamDetailArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TeamDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    stringDecoder: StringDecoder,
) : ViewModel() {

    private val teamDetailArgs: TeamDetailArgs = TeamDetailArgs(savedStateHandle, stringDecoder)

    val teamId = teamDetailArgs.teamId
}