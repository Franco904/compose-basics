package com.example.auth_compose.ui.unscramble_game.models

import androidx.annotation.StringRes
import com.example.auth_compose.R
import com.example.auth_compose.util.faker
import com.example.auth_compose.util.toTitleCase
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

enum class GameTopic(
    @StringRes val displayName: Int,
    private val wordBuilder: () -> String,
) {
    ADJECTIVES(R.string.unscramble_game_topic_adjectives, faker.adjective::positive),
    ANIMAL_NAMES(R.string.unscramble_game_topic_animal_names, faker.animal::name),
    BASKETBALL_TEAMS(R.string.unscramble_game_topic_basketball_teams, faker.basketball::teams),
    COLOR_NAMES(R.string.unscramble_game_topic_color_names, faker.color::name),
    HARRY_POTTER_SPELLS(
        R.string.unscramble_game_topic_harry_potter_spells,
        faker.harryPotter::spells
    ),
    SPORT_NAMES(R.string.unscramble_game_topic_sport_names, faker.sport::summerOlympics),
    MINECRAFT_MOBS(R.string.unscramble_game_topic_minecraft_mobs, faker.minecraft::mobs);

    suspend fun getWords() = withContext(Dispatchers.Default) {
        List(10) { wordBuilder() }
            .toSet()
            .toMutableList()
            .apply {
                while (size != 10) {
                    wordBuilder().let { if (it !in this) add(it) }
                }
            }
            .map {
                it.toTitleCase()
            }
            .toPersistentList()
    }
}