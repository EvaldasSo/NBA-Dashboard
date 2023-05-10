package com.example.nba.presentation.home_screen.user_preferences_dialog

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.nba.R
import com.example.nba.domain.model.TeamSort
import com.example.nba.domain.model.UserPreferences

@Composable
fun UserPrefsDialog(
    onDismiss: () -> Unit,
    viewModel: UserPrefsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    UserPrefsDialog(
        uiState = uiState,
        onDismiss = onDismiss,
        onChangeTeamSort = viewModel::updateTeamSort,
    )
}

@Composable
private fun UserPrefsDialog(
    uiState: UiState,
    onDismiss: () -> Unit,
    onChangeTeamSort: (sortBy: TeamSort) -> Unit,
) {
    val configuration = LocalConfiguration.current

    AlertDialog(
        properties = DialogProperties(usePlatformDefaultWidth = false),
        modifier = Modifier.widthIn(max = configuration.screenWidthDp.dp - 80.dp),
        onDismissRequest = { onDismiss() },
        title = {
            Text(
                text = stringResource(R.string.sort),
                style = MaterialTheme.typography.titleLarge,
            )
        },
        text = {
            Divider()
            Column(Modifier.verticalScroll(rememberScrollState())) {
                when (uiState) {
                    UiState.Loading -> {
                        Text(
                            text = stringResource(R.string.loading),
                            modifier = Modifier.padding(vertical = 16.dp),
                        )
                    }

                    is UiState.Success -> {
                        SettingsPanel(
                            userPreferences = uiState.userPreferences,
                            onChangeTeamSort = onChangeTeamSort,
                        )
                    }
                }
            }
        },
        confirmButton = {
            Text(
                text = stringResource(R.string.dismiss_dialog_button_text),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .clickable { onDismiss() },
            )
        },
    )
}

@Composable
private fun SettingsDialogSectionTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
    )
}

@Composable
fun SettingsDialogThemeChooserRow(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    Row(
        Modifier
            .fillMaxWidth()
            .selectable(
                selected = selected,
                role = Role.RadioButton,
                onClick = onClick,
            )
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        RadioButton(
            selected = selected,
            onClick = null,
        )
        Spacer(Modifier.width(8.dp))
        Text(text)
    }
}

@Composable
private fun SettingsPanel(
    userPreferences: UserPreferences,
    onChangeTeamSort: (sortBy: TeamSort) -> Unit,
) {
    SettingsDialogSectionTitle(text = stringResource(R.string.team_category))
    Column(Modifier.selectableGroup()) {
        SettingsDialogThemeChooserRow(
            text = stringResource(R.string.none),
            selected = userPreferences.teamSortBy == TeamSort.NONE,
            onClick = { onChangeTeamSort(TeamSort.NONE) },
        )
        SettingsDialogThemeChooserRow(
            text = stringResource(R.string.name),
            selected = userPreferences.teamSortBy == TeamSort.NAME,
            onClick = { onChangeTeamSort(TeamSort.NAME) },
        )
        SettingsDialogThemeChooserRow(
            text = stringResource(R.string.city),
            selected = userPreferences.teamSortBy == TeamSort.CITY,
            onClick = { onChangeTeamSort(TeamSort.CITY) },
        )
        SettingsDialogThemeChooserRow(
            text = stringResource(R.string.conference),
            selected = userPreferences.teamSortBy == TeamSort.CONFERENCE,
            onClick = { onChangeTeamSort(TeamSort.CONFERENCE) },
        )
    }
}
