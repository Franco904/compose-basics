package com.example.auth_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.auth_compose.ui.composables.ArtworkShowcaseComposable
import com.example.auth_compose.ui.composables.WoofDogsComposable
import com.example.auth_compose.ui.theme.AuthcomposeTheme
import com.example.auth_compose.ui.theme.woof.WoofTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AuthcomposeTheme {
                ArtworkShowcaseComposable()
            }
//            WoofTheme {
//                WoofDogsComposable()
//            }
        }
    }
}
