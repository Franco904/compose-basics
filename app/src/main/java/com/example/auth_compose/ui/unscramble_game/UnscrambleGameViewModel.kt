package com.example.auth_compose.ui.unscramble_game

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.auth_compose.R
import com.example.auth_compose.ui.unscramble_game.models.GameState
import com.example.auth_compose.ui.unscramble_game.models.GameTopic
import com.example.auth_compose.ui.unscramble_game.models.GameTopicWords
import com.example.auth_compose.ui.unscramble_game.models.UnscrambleGameUiState
import com.example.auth_compose.ui.unscramble_game.validators.GuessValidator
import com.example.auth_compose.util.faker
import com.example.auth_compose.util.scramble
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UnscrambleGameViewModel(
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    val uiState = savedStateHandle.getStoredStateFlow(UI_STATE_KEY, UnscrambleGameUiState())

    var guess by mutableStateOf("")
        private set

    var isGuessValid by mutableStateOf(true)
        private set

    private lateinit var topicToWords: Map<GameTopic, ImmutableList<String>>

    fun init() {
        viewModelScope.launch {
            topicToWords = mapOf(
                GameTopic.ADJECTIVES to GameTopic.ADJECTIVES.getWords(),
                GameTopic.ANIMAL_NAMES to GameTopic.ANIMAL_NAMES.getWords(),
                GameTopic.BASKETBALL_TEAMS to GameTopic.BASKETBALL_TEAMS.getWords(),
                GameTopic.COLOR_NAMES to GameTopic.COLOR_NAMES.getWords(),
                GameTopic.HARRY_POTTER_SPELLS to GameTopic.HARRY_POTTER_SPELLS.getWords(),
                GameTopic.SPORT_NAMES to GameTopic.SPORT_NAMES.getWords(),
                GameTopic.MINECRAFT_MOBS to GameTopic.MINECRAFT_MOBS.getWords(),
            )
        }
    }

    fun onGuessChanged(newGuess: String) {
        guess = newGuess
    }

    fun onGuessFieldFocusChanged(isFocused: Boolean) {
        isGuessValid = if (isFocused) true else GuessValidator.isValid(guess)
    }

    fun onPrimaryButtonClicked() {
        when (uiState.value.gameState) {
            GameState.NOT_STARTED -> initTopicSelection()
            GameState.TOPIC_SELECTION -> {}
            GameState.STARTED -> submitGuess()
            GameState.FINISHED -> restartGame()
        }
    }

    private fun initTopicSelection() {
        savedStateHandle.updateStateFlow(UI_STATE_KEY, uiState) { currentUiState ->
            currentUiState.copy(
                gameState = GameState.TOPIC_SELECTION,
                topicWords = null,
            )
        }
    }

    fun onCancelTopicSelection() {
        savedStateHandle.updateStateFlow(UI_STATE_KEY, uiState) { currentUiState ->
            currentUiState.copy(gameState = GameState.NOT_STARTED)
        }
    }

    fun onTopicSelected(selectedTopicDisplayName: Int?) {
        val topicWords = topicToWords.entries.find { it.key.displayName == selectedTopicDisplayName }

        if (topicWords == null) {
            onCancelTopicSelection()
            return
        }

        startGame(GameTopicWords(topicWords.key, topicWords.value))
    }

    private fun startGame(topicWords: GameTopicWords) {
        val roundWord = faker.random.randomValue(topicWords.words)

        savedStateHandle.updateStateFlow(UI_STATE_KEY, uiState) {
            UnscrambleGameUiState(
                gameState = GameState.STARTED,
                topicWords = topicWords,
                totalScore = 0,
                round = 1,
                roundWord = roundWord,
                scrambledRoundWord = roundWord.scramble(),
                hasScoredInRound = false,
                hasSkippedRound = false,
                primaryButtonText = R.string.unscramble_game_submit_primary_btn,
                secondaryButtonText = R.string.unscramble_game_skip_secondary_btn,
            )
        }

        resetGuessState()
    }

    private fun submitGuess() {
        isGuessValid = GuessValidator.isValid(guess)
        if (!isGuessValid) return

        val hasScoredInRound = guess.lowercase() == uiState.value.roundWord?.lowercase()
        val currentTotalScore = uiState.value.totalScore

        val (newRoundWord, newWords) = pickRandomWordForNextRound()

        if (uiState.value.round != LAST_ROUND_NUMBER) {
            savedStateHandle.updateStateFlow(UI_STATE_KEY, uiState) { currentUiState ->
                currentUiState.copy(
                    topicWords = uiState.value.topicWords?.copyWith(words = newWords),
                    totalScore = if (hasScoredInRound) currentTotalScore + 10 else currentTotalScore,
                    round = uiState.value.round + 1,
                    roundWord = newRoundWord,
                    scrambledRoundWord = newRoundWord.scramble(),
                    hasSkippedRound = false,
                )
            }
        } else {
            savedStateHandle.updateStateFlow(UI_STATE_KEY, uiState) { currentUiState ->
                currentUiState.copy(
                    gameState = GameState.FINISHED,
                    hasSkippedRound = false,
                )
            }
        }

        resetGuessState()
    }

    fun restartGame() = initTopicSelection()

    fun onSecondaryButtonClicked() {
        val gameState = uiState.value.gameState

        if (gameState == GameState.STARTED) skipWord()
    }

    private fun skipWord() {
        val (newRoundWord, newWords) = pickRandomWordForNextRound()

        if (uiState.value.round != LAST_ROUND_NUMBER) {
            savedStateHandle.updateStateFlow(UI_STATE_KEY, uiState) { currentUiState ->
                currentUiState.copy(
                    topicWords = uiState.value.topicWords?.copyWith(words = newWords),
                    round = uiState.value.round + 1,
                    roundWord = newRoundWord,
                    scrambledRoundWord = newRoundWord.scramble(),
                    hasScoredInRound = false,
                    hasSkippedRound = true,
                )
            }
        } else {
            savedStateHandle.updateStateFlow(UI_STATE_KEY, uiState) { currentUiState ->
                currentUiState.copy(
                    gameState = GameState.FINISHED,
                    hasScoredInRound = false,
                    hasSkippedRound = true,
                )
            }
        }

        resetGuessState()
    }

    fun quitGame() {
        savedStateHandle.updateStateFlow(UI_STATE_KEY, uiState) { currentUiState ->
            currentUiState.copy(
                gameState = GameState.NOT_STARTED,
                primaryButtonText = R.string.unscramble_game_start_game_primary_btn,
                secondaryButtonText = R.string.unscramble_game_no_secondary_btn,
            )
        }
    }

    private fun pickRandomTopicAndWords() = faker.random.randomValue(
        topicToWords.map { GameTopicWords(it.key, it.value) }.toPersistentList()
    )

    private fun pickRandomWordForNextRound(): Pair<String, ImmutableList<String>> {
        val currentWords = uiState.value.topicWords?.words ?: persistentListOf()
        val currentRoundWord = uiState.value.roundWord

        val newRoundWord = faker.random.randomValue(currentWords)
        val newWords = currentWords.filter { word -> word != currentRoundWord }.toPersistentList()

        return Pair(newRoundWord, newWords)
    }

    private fun resetGuessState() {
        guess = ""
        isGuessValid = true
    }

    companion object {
        private const val UI_STATE_KEY = "uiState"
        private const val LAST_ROUND_NUMBER = 10
    }
}

fun <T> SavedStateHandle.getStoredStateFlow(key: String, defaultValue: T) = getStateFlow(key, defaultValue)

fun <T> SavedStateHandle.updateStateFlow(key: String, stateFlow: StateFlow<T>, function: (T) -> T) {
    synchronized(stateFlow) {
        val prevValue = stateFlow.value
        val nextValue = function(prevValue)
        this[key] = nextValue
    }
}
