/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.nba.presentation.team_detail_screen.navigation

import android.net.Uri
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.nba.common.decoder.StringDecoder
import com.example.nba.presentation.team_detail_screen.TeamDetailRoute

@VisibleForTesting
internal const val teamIdArg = "teamId"

internal class TeamDetailArgs(val teamId: String) {
    constructor(savedStateHandle: SavedStateHandle, stringDecoder: StringDecoder) :
        this(stringDecoder.decodeString(checkNotNull(savedStateHandle[teamIdArg])))
}

fun NavController.navigateToTeamDetail(teamId: String) {
    val encodedId = Uri.encode(teamId)
    this.navigate("team_detail_route/$encodedId")
}

fun NavGraphBuilder.teamDetailScreen(
    onBackClick: () -> Unit,
) {
    composable(
        route = "team_detail_route/{$teamIdArg}",
        arguments = listOf(
            navArgument(teamIdArg) { type = NavType.StringType },
        ),
    ) {
        TeamDetailRoute(onBackClick = onBackClick)
    }
}
