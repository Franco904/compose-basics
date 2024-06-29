package com.example.auth_compose.util

fun calculateTip(
    billAmount: String,
    tipPercentage: String,
    mustRoundTip: Boolean,
): String {
    val billAmountNumber = billAmount.toFloatOrNull() ?: return ""
    val tipPercentageNumber = tipPercentage.toFloatOrNull() ?: return ""

    val tipAmount = billAmountNumber * (tipPercentageNumber / 100)

    return if (mustRoundTip) String.format("%.2f", tipAmount) else tipAmount.toString()
}