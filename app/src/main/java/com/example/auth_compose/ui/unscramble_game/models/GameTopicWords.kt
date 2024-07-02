package com.example.auth_compose.ui.unscramble_game.models

import android.os.Parcelable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class GameTopicWords(
    val topic: GameTopic,
    val words: @RawValue ImmutableList<String>,
): Parcelable {
    fun copyWith(
        topic: GameTopic? = null,
        words: ImmutableList<String>? = null,
    ) = GameTopicWords(
        topic = topic ?: this.topic,
        words = words ?: this.words,
    )
}