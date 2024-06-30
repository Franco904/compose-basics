package com.example.auth_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.auth_compose.ui.unscramble_game.UnscrambleGameScreen
import com.example.auth_compose.ui.unscramble_game.theme.UnscrambleGameTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
//            ComposeBasicsTheme {
//                ArtworkShowcaseComposable()
//            }

            UnscrambleGameTheme {
                UnscrambleGameScreen()
            }
        }
    }
}
