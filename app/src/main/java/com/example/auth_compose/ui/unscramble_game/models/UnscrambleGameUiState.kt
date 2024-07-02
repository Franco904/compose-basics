package com.example.auth_compose.ui.unscramble_game.models

import android.os.Parcelable
import androidx.annotation.StringRes
import kotlinx.parcelize.Parcelize

@Parcelize
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
) : Parcelable
