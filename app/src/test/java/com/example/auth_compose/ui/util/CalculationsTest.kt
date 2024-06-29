package com.example.auth_compose.ui.util

import com.example.auth_compose.testUtils.faker
import com.example.auth_compose.util.calculateTip
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class CalculationsTest {
    @Nested
    @DisplayName("calculateTip")
    inner class CalculateTipTests {
        @Test
        fun `Deve retornar o valor da gorjeta sem arrendondamento com base no valor da conta e percentual da gorjeta`() {
            val tipAmount = calculateTip(
                billAmount = "123",
                tipPercentage = "26.3",
                mustRoundTip = false,
            )

            tipAmount.shouldBeEqualTo("32.349")
        }

        @Test
        fun `Deve retornar o valor da gorjeta com arrendondamento com base no valor da conta e percentual da gorjeta`() {
            val tipAmount = calculateTip(
                billAmount = "123",
                tipPercentage = "26.3",
                mustRoundTip = true,
            )

            tipAmount.shouldBeEqualTo("32,35")
        }

        @Test
        fun `Deve retornar string vazia se o valor da conta nao for numerico`() {
            val tipAmount = calculateTip(
                billAmount = "wegqegadea",
                tipPercentage = "26.3",
                mustRoundTip = faker.random.nextBoolean(),
            )

            tipAmount.shouldBeEqualTo("")
        }

        @Test
        fun `Deve retornar string vazia se o valor percentual da gorjeta nao for numerico`() {
            val tipAmount = calculateTip(
                billAmount = "123",
                tipPercentage = "wrgwargawRg",
                mustRoundTip = faker.random.nextBoolean(),
            )

            tipAmount.shouldBeEqualTo("")
        }
    }
}