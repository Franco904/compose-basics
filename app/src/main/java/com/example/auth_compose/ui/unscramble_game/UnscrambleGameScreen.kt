package com.example.auth_compose.ui.unscramble_game

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Badge
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.auth_compose.R
import com.example.auth_compose.ui.unscramble_game.models.GameState
import com.example.auth_compose.ui.unscramble_game.theme.UnscrambleGameTheme
import com.example.auth_compose.ui.unscramble_game.theme.spanTypography
import com.example.auth_compose.util.style
import com.example.auth_compose.util.textSpan

@Composable
fun UnscrambleGameScreen(
    modifier: Modifier = Modifier,
    viewModel: UnscrambleGameViewModel = viewModel(),
) {
    val gameUiState by viewModel.uiState.collectAsState()

    val focusManager = LocalFocusManager.current

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .pointerInput(Unit) {
                detectTapGestures(onTap = { focusManager.clearFocus() })
            }
            .padding(horizontal = 24.dp, vertical = 32.dp)
            .verticalScroll(rememberScrollState())
    ) {
        if (gameUiState.gameState == GameState.STARTED) {
            Row {
                GameTotalScore(totalScore = gameUiState.totalScore.toString())
                Spacer(modifier = Modifier.weight(1f))
                GameTopic(
                    topic = stringResource(
                        id = gameUiState.topicWords?.topic?.displayName
                            ?: R.string.unscramble_game_topic_no_topic_set
                    )
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        GameMainPanel {
            when (gameUiState.gameState) {
                GameState.NOT_STARTED -> GameNotStartedPanel()
                GameState.STARTED -> GameStartedPanel(
                    round = gameUiState.round.toString(),
                    scrambledRoundWord = gameUiState.scrambledRoundWord,
                    guess = viewModel.guess,
                    isGuessValid = viewModel.isGuessValid,
                    onGuessChanged = viewModel::onGuessChanged,
                    onGuessInputDone = { focusManager.clearFocus() },
                    onFocusChanged = viewModel::onGuessFieldFocusChanged,
                )

                GameState.FINISHED -> GameFinishedPanel(
                    totalScore = gameUiState.totalScore.toString(),
                )
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
        GamePrimaryButton(
            buttonText = stringResource(
                id = gameUiState.primaryButtonText
                    ?: R.string.unscramble_game_start_game_primary_btn
            ),
            onClick = viewModel::onPrimaryButtonClicked,
        )
        AnimatedVisibility(gameUiState.gameState != GameState.NOT_STARTED) {
            GameSecondaryButton(
                buttonText = stringResource(
                    id = gameUiState.secondaryButtonText
                        ?: R.string.unscramble_game_no_secondary_btn
                ),
                onClick = viewModel::onSecondaryButtonClicked,
            )
        }
    }
}

@Composable
private fun GameTotalScore(
    totalScore: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = buildAnnotatedString {
            textSpan(stringResource(R.string.unscramble_game_score))
            textSpan(
                totalScore.style(
                    SpanStyle(
                        fontWeight = FontWeight.Medium,
                        letterSpacing = 0.5.sp,
                    )
                )
            )
        },
        modifier = modifier
    )
}

@Composable
private fun GameTopic(
    topic: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = buildAnnotatedString {
            textSpan(stringResource(R.string.unscramble_game_topic))
            textSpan(
                topic.style(
                    SpanStyle(
                        fontWeight = FontWeight.Medium,
                        letterSpacing = 0.5.sp,
                    )
                )
            )
        },
        modifier = modifier
    )
}

@Composable
private fun GameMainPanel(
    modifier: Modifier = Modifier,
    mainPanel: @Composable () -> Unit,
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
        ),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp,
        ),
        modifier = modifier
            .fillMaxWidth()
    ) {
        mainPanel()
    }
}

@Composable
private fun GameNotStartedPanel(
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .padding(32.dp)
    ) {
        Text(
            text = buildAnnotatedString {
                textSpan(
                    stringResource(R.string.app_owner_text),
                    style = MaterialTheme.spanTypography.bodyLarge,
                )
                textSpan(
                    stringResource(R.string.unscramble_game_name),
                    style = MaterialTheme.spanTypography.headlineMedium
                )
            },
        )
    }
}

@Composable
private fun GameStartedPanel(
    round: String,
    scrambledRoundWord: String?,
    guess: String?,
    isGuessValid: Boolean,
    onGuessChanged: (String) -> Unit,
    onGuessInputDone: KeyboardActionScope.() -> Unit,
    onFocusChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    var isStartedStateInitialCompositionCompleted by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isStartedStateInitialCompositionCompleted = true
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Badge(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .clip(MaterialTheme.shapes.small)
                .align(Alignment.End)
        ) {
            Text(
                text = stringResource(R.string.unscramble_game_current_round, round),
                modifier = Modifier.padding(2.dp),
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = scrambledRoundWord ?: "",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.unscramble_guess_the_scrambled_word),
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
        )
        Spacer(modifier = Modifier.height(16.dp))
        GameGuessTextField(
            guess = guess,
            isGuessValid = isGuessValid,
            onGuessChanged = onGuessChanged,
            onGuessInputDone = onGuessInputDone,
            onFocusChanged = onFocusChanged,
            isStartedStateInitialCompositionCompleted = isStartedStateInitialCompositionCompleted,
        )
    }
}

@Composable
private fun GameGuessTextField(
    guess: String?,
    isGuessValid: Boolean,
    onGuessChanged: (String) -> Unit,
    onGuessInputDone: KeyboardActionScope.() -> Unit,
    onFocusChanged: (Boolean) -> Unit,
    isStartedStateInitialCompositionCompleted: Boolean,
    modifier: Modifier = Modifier,
) {
    OutlinedTextField(
        value = guess ?: "",
        onValueChange = onGuessChanged,
        keyboardActions = KeyboardActions(
            onDone = onGuessInputDone,
        ),
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done,
        ),
        isError = !isGuessValid,
        placeholder = { Text(text = stringResource(R.string.unscramble_your_guess)) },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.background,
            unfocusedContainerColor = MaterialTheme.colorScheme.background,
            errorContainerColor = MaterialTheme.colorScheme.background,
            errorCursorColor = MaterialTheme.colorScheme.error,
        ),
        supportingText = {
            AnimatedVisibility(!isGuessValid) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = stringResource(R.string.unscramble_input_your_guess_before_submit),
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        },
        shape = MaterialTheme.shapes.medium,
        singleLine = true,
        modifier = modifier
            .onFocusChanged {
                if (isStartedStateInitialCompositionCompleted) {
                    onFocusChanged(it.isFocused)
                }
            }
    )
}

@Composable
private fun GameFinishedPanel(
    totalScore: String,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.unscramble_your_final_score),
            style = MaterialTheme.typography.bodyLarge,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = totalScore,
            style = MaterialTheme.typography.headlineMedium,
        )
    }
}

@Composable
private fun GamePrimaryButton(
    buttonText: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ElevatedButton(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
        ),
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(text = buttonText)
    }
}

@Composable
private fun GameSecondaryButton(
    buttonText: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedButton(
        onClick = onClick,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(text = buttonText)
    }
}

@Preview
@Composable
fun UnscrambleGameUiPreview() {
    UnscrambleGameTheme {
        UnscrambleGameScreen()
    }
}