package com.example.auth_compose

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performImeAction
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.auth_compose.ui.composables.TipCalculatorComposable
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TipUITest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun mustCalculate20PercentTipWithoutRound() {
        with(composeTestRule) {
            setContent {
                TipCalculatorComposable()
            }

            onNodeWithText("Bill Amount")
                .performTextInput("123")

            onNodeWithText("Tip Percentage").run {
                performTextInput("26.3")
                performImeAction()
            }

            val expectedTip = "Tip Amount: $ 32.349"
            val notFoundMessage = "Composable with text $expectedTip not found."

            onNodeWithText(expectedTip).assertExists(notFoundMessage)
        }
    }
}
