package com.example.auth_compose.ui.unscramble_game.validators

object GuessValidator {
    fun isValid(guess: String): Boolean {
        return guess.isNotBlank()
    }
}