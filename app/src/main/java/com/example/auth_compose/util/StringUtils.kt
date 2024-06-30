package com.example.auth_compose.util

import java.util.Locale

fun String.toTitleCase() = split(" ").joinToString(separator = " ") { word ->
    word.lowercase().replaceFirstChar { it.titlecase(Locale.getDefault()) }
}

fun String.scramble() = split(" ").joinToString(separator = " ") { word ->
    val scrambledWord = word.toCharArray().apply { shuffle() }
    scrambledWord.joinToString(separator = "").toTitleCase()
}