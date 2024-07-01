package com.example.auth_compose.ui.unscramble_game.models

import androidx.annotation.StringRes

data class UnscrambleGameUiState(
    val gameState: GameState = GameState.NOT_STARTED,
    val topicWords: GameTopicWords? = null,
    val totalScore: Int = 0,
    val round: Int = 1,
    val roundWord: String? = null,
    val scrambledRoundWord: String? = null,
    val hasScoredInRound: Boolean = false,
    val hasSkippedRound: Boolean = false,
    @StringRes
    val primaryButtonText: Int? = null,
    @StringRes
    val secondaryButtonText: Int? = null,
) {
    fun copyWith(
        gameState: GameState? = null,
        topicWords: GameTopicWords? = null,
        totalScore: Int? = null,
        round: Int? = null,
        roundWord: String? = null,
        scrambledRoundWord: String? = null,
        hasScoredInRound: Boolean? = null,
        hasSkippedRound: Boolean? = null,
        primaryButtonText: Int? = null,
        secondaryButtonText: Int? = null,
    ) = UnscrambleGameUiState(
        gameState = gameState ?: this.gameState,
        topicWords = topicWords ?: this.topicWords,
        totalScore = totalScore ?: this.totalScore,
        round = round ?: this.round,
        roundWord = roundWord ?: this.roundWord,
        scrambledRoundWord = scrambledRoundWord ?: this.scrambledRoundWord,
        hasScoredInRound = hasScoredInRound ?: this.hasScoredInRound,
        hasSkippedRound = hasSkippedRound ?: this.hasSkippedRound,
        primaryButtonText = primaryButtonText ?: this.primaryButtonText,
        secondaryButtonText = secondaryButtonText ?: this.secondaryButtonText,
    )
}