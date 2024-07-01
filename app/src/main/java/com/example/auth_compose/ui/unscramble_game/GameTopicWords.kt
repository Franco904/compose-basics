package com.example.auth_compose.ui.unscramble_game

import kotlinx.collections.immutable.ImmutableList

data class GameTopicWords(
    val topic: GameTopic,
    val words: ImmutableList<String>,
) {
    fun copyWith(
        topic: GameTopic? = null,
        words: ImmutableList<String>? = null,
    ) = GameTopicWords(
        topic = topic ?: this.topic,
        words = words ?: this.words,
    )
}