package com.example.auth_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import com.example.auth_compose.ui.composables.CourseTopicsGridComposable
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
    CourseTopicsGridComposable()
}
