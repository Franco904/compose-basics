package com.example.auth_compose.ui.unscramble_game

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.auth_compose.ui.unscramble_game.theme.UnscrambleGameTheme
import com.example.auth_compose.util.faker
import com.example.auth_compose.util.style
import com.example.auth_compose.util.textSpan
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList

@Composable
fun UnscrambleGameScreen(modifier: Modifier = Modifier) {
    var gameState by remember { mutableStateOf(GameState.NOT_STARTED) }

    var totalScore by remember { mutableIntStateOf(0) }
    var round by remember { mutableIntStateOf(1) }

    val topicToWords by remember { mutableStateOf(mapOf(
        "App names" to List(10) { faker.app.name() }.toPersistentList(),
        "Adjectives" to List(10) { faker.adjective.positive() }.toPersistentList(),
        "Animal names" to List(10) { faker.animal.name() }.toPersistentList(),
        "Beer brands" to List(10) { faker.beer.brand() }.toPersistentList(),
        "Bird adjectives" to List(10) { faker.bird.emotionalAdjectives() }.toPersistentList(),
        "Color names" to List(10) { faker.color.name() }.toPersistentList(),
        "Harry Potter spells" to List(10) { faker.harryPotter.spells() }.toPersistentList(),
        "Sport names" to List(10) { faker.sport.summerOlympics() }.toPersistentList(),
        "Minecraft mobs" to List(10) { faker.minecraft.mobs() }.toPersistentList(),
        "Vehicle fuels" to List(10) { faker.vehicle.fuelTypes() }.toPersistentList(),
        )) }
    var topic by remember { mutableStateOf(faker.random.randomValue(topicToWords.keys.toList())) }
    var words by remember { mutableStateOf(topicToWords[topic] ?: persistentListOf()) }

    var roundWord by remember { mutableStateOf(faker.random.randomValue(words)) }

    var primaryButtonText by rememberSaveable { mutableStateOf("Start game") }
    var secondaryButtonText by rememberSaveable { mutableStateOf("") }

    var guess by rememberSaveable { mutableStateOf("") }
    var isGuessInvalid by remember { mutableStateOf(false) }

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
        AnimatedVisibility(gameState == GameState.STARTED) {
            GameTotalScore(totalScore.toString())
        }
        Spacer(modifier = Modifier.height(8.dp))
        GameMainPanel(
            mainPanel = {
                when (gameState) {
                    GameState.NOT_STARTED -> GameNotStartedPanel()
                    GameState.STARTED -> GameStartedPanel(
                        round = round.toString(),
                        roundWord = roundWord,
                        guess = guess,
                        isGuessInvalid = isGuessInvalid,
                        onGuessChanged = { guessValue -> guess = guessValue },
                        onGuessInputDone = { focusManager.clearFocus() },
                    )

                    GameState.FINISHED -> GameFinishedPanel(
                        totalScore = totalScore.toString(),
                    )
                }
            },
        )
        Spacer(modifier = Modifier.height(32.dp))
        GamePrimaryButton(
            buttonText = primaryButtonText,
            onClick = {
                when (gameState) {
                    GameState.NOT_STARTED -> {
                        roundWord = faker.random.randomValue(words)

                        gameState = GameState.STARTED
                    }

                    GameState.STARTED -> {
                        if (guess.isBlank()) {
                            isGuessInvalid = true
                        } else {
                            isGuessInvalid = false

                            if (round == 10) {
                                gameState = GameState.FINISHED
                            } else {
                                words = words.filter { word -> word != roundWord }.toPersistentList()
                                roundWord = faker.random.randomValue(words)

                                round++
                            }
                            guess = ""
                        }
                    }

                    GameState.FINISHED -> {
                        topic = faker.random.randomValue(topicToWords.keys.toList())
                        words = topicToWords[topic] ?: persistentListOf()
                        roundWord = faker.random.randomValue(words)

                        round = 1
                        totalScore = 0

                        gameState = GameState.STARTED
                    }
                }
            }
        )
        AnimatedVisibility(gameState == GameState.STARTED) {
            GameSecondaryButton(
                buttonText = secondaryButtonText,
                onClick = {
                    if (guess.isBlank()) {
                        isGuessInvalid = true
                    } else {
                        isGuessInvalid = false
                        guess = ""
                    }
                }
            )
        }
    }
}

@Composable
fun GameTotalScore(
    currentRoundScore: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = buildAnnotatedString {
            textSpan("Score: ")
            textSpan(
                currentRoundScore.style(
                    SpanStyle(
                        fontWeight = FontWeight.Medium,
                        letterSpacing = 0.5.sp,
                    )
                )
            )
        },
        modifier = modifier,
    )
}

@Composable
fun GameMainPanel(
    mainPanel: @Composable () -> Unit,
    modifier: Modifier = Modifier,
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
fun GameNotStartedPanel(
    modifier: Modifier = Modifier,
) {
    Text(
        text = "Start a new game from the button below",
        style = MaterialTheme.typography.bodyLarge,
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
}

@Composable
fun GameStartedPanel(
    round: String,
    roundWord: String,
    guess: String,
    isGuessInvalid: Boolean,
    onGuessChanged: (String) -> Unit,
    onGuessInputDone: KeyboardActionScope.() -> Unit,
    modifier: Modifier = Modifier,
) {
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
                text = "$round/10",
                modifier = Modifier.padding(2.dp),
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = roundWord,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Guess the scrambled word!",
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = guess,
            onValueChange = onGuessChanged,
            keyboardActions = KeyboardActions(
                onDone = onGuessInputDone,
            ),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done,
            ),
            isError = isGuessInvalid,
            placeholder = { Text(text = "Your guess") },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.background,
                unfocusedContainerColor = MaterialTheme.colorScheme.background,
                errorContainerColor = MaterialTheme.colorScheme.background,
                errorCursorColor = MaterialTheme.colorScheme.error,
            ),
            supportingText = {
                AnimatedVisibility(isGuessInvalid) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Input your guess before submit",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                    )
                }
            },
            shape = MaterialTheme.shapes.medium,
            singleLine = true,
        )
    }
}

@Composable
fun GameFinishedPanel(
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
            text = "Your final score:",
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
fun GamePrimaryButton(
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
fun GameSecondaryButton(
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

enum class GameState {
    NOT_STARTED,
    STARTED,
    FINISHED;
}

@Preview
@Composable
fun UnscrambleGameUiPreview() {
    UnscrambleGameTheme {
        UnscrambleGameScreen()
    }
}