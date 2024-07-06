package com.example.auth_compose.ui.unscramble_game.overlays

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.auth_compose.R
import com.example.auth_compose.ui.unscramble_game.coreComposables.DropdownField
import kotlinx.collections.immutable.ImmutableList

@Composable
fun GameTopicSelectionDialog(
    topics: ImmutableList<Int>,
    onStart: (Int?) -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var selectedTopic by rememberSaveable { mutableStateOf<Int?>(null) }

    AlertDialog(
        title = {
            Text(text = stringResource(R.string.unscramble_game_select_game_topic))
        },
        text = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                DropdownField(
                    placeholderText = stringResource(R.string.unscramble_game_label_topic),
                    items = topics,
                    onItemSelected = { item -> selectedTopic = item }
                )
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = stringResource(R.string.unscramble_game_topic_selection_explanation),
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onCancel) {
                Text(
                    text = stringResource(id = R.string.unscramble_game_dialog_button_cancel),
                    style = MaterialTheme.typography.titleMedium,
                )
            }
        },
        confirmButton = {
            TextButton(
                enabled = selectedTopic != null,
                onClick = { onStart(selectedTopic) },
            ) {
                Text(
                    text = stringResource(id = R.string.unscramble_game_dialog_button_start),
                    style = MaterialTheme.typography.titleMedium,
                )
            }
        },
        onDismissRequest = {},
        shape = MaterialTheme.shapes.small,
        modifier = modifier.width(450.dp)
    )
}
