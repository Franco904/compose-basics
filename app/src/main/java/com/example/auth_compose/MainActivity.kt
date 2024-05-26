package com.example.auth_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.auth_compose.ui.composables.BirthdayCardComposable
import com.example.auth_compose.ui.theme.AuthcomposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AuthcomposeTheme {
                AppContent()
            }
        }
    }
}

@Composable
fun AppContent() {
    BirthdayCardComposable(
        message = stringResource(R.string.happy_birthday_card_title),
        from = stringResource(R.string.happy_birthday_card_from_text),
    )
}

