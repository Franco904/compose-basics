package com.example.auth_compose.ui.composables

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.auth_compose.R
import com.example.auth_compose.ui.theme.AuthcomposeTheme
import com.example.auth_compose.ui.util.calculateTip

@Composable
fun TipCalculatorComposable(modifier: Modifier = Modifier) {
    var billAmount by rememberSaveable { mutableStateOf("") }
    var tipPercentage by rememberSaveable { mutableStateOf("") }
    var tipAmount by rememberSaveable { mutableStateOf("") }

    var mustRoundTip by rememberSaveable { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()

    Surface(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures { focusManager.clearFocus() }
            }
            .verticalScroll(scrollState)
            .padding(36.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
        ) {
            Text(text = stringResource(id = R.string.calculate_tip))
            Spacer(modifier = Modifier.height(16.dp))
            StandardTextField(
                value = billAmount,
                onValueChange = { value ->
                    billAmount = value
                    tipAmount = calculateTip(billAmount, tipPercentage, mustRoundTip)
                },
                leadingIcon = R.drawable.baseline_money_24,
                label = stringResource(R.string.bill_amount),
                focusManager = focusManager,
            )
            Spacer(modifier = Modifier.height(28.dp))
            StandardTextField(
                value = tipPercentage,
                onValueChange = { value ->
                    tipPercentage = value
                    tipAmount = calculateTip(billAmount, tipPercentage, mustRoundTip)
                },
                leadingIcon = R.drawable.baseline_percent_24,
                label = stringResource(R.string.tip_percentage),
                focusManager = focusManager,
                imeAction = ImeAction.Done,
            )
            Spacer(modifier = Modifier.height(28.dp))
            RoundTheTipRow(
                mustRoundTip = mustRoundTip,
                onCheckedChange = { isChecked ->
                    mustRoundTip = isChecked
                    tipAmount = calculateTip(billAmount, tipPercentage, mustRoundTip)
                },
            )
            Spacer(modifier = Modifier.height(28.dp))
            Text(
                text = stringResource(id = R.string.tip_amount, tipAmount),
                fontSize = 24.sp,
            )
        }
    }
}

@Composable
fun RoundTheTipRow(
    mustRoundTip: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Text(
            text = stringResource(id = R.string.round_up_tip),
            modifier = Modifier.weight(1f)
        )
        Switch(
            checked = mustRoundTip,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                uncheckedThumbColor = colorResource(id = R.color.brown_field),
                checkedThumbColor = colorResource(id = R.color.white),
                uncheckedBorderColor = colorResource(id = R.color.brown_field),
                uncheckedTrackColor = colorResource(id = R.color.red_light_field),
                checkedTrackColor = colorResource(id = R.color.red_dark_field),
            ),
        )
    }
}

@Composable
fun StandardTextField(
    value: String,
    onValueChange: (String) -> Unit,
    @DrawableRes leadingIcon: Int,
    label: String,
    focusManager: FocusManager,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Number,
    imeAction: ImeAction = ImeAction.Next,
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        leadingIcon = {
            Icon(
                painter = painterResource(id = leadingIcon),
                contentDescription = null,
            )
        },
        label = { Text(text = label) },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = keyboardType,
            imeAction = imeAction,
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus()
            }
        ),
        singleLine = true,
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = colorResource(id = R.color.red_light_field),
            focusedContainerColor = colorResource(id = R.color.red_light_field),
            unfocusedLeadingIconColor = colorResource(id = R.color.brown_field),
            focusedLeadingIconColor = colorResource(id = R.color.red_dark_field),
            unfocusedLabelColor = colorResource(id = R.color.brown_field),
            focusedLabelColor = colorResource(id = R.color.red_dark_field),
            cursorColor = colorResource(id = R.color.red_dark_field),
            unfocusedIndicatorColor = colorResource(id = R.color.brown_field),
            focusedIndicatorColor = colorResource(id = R.color.red_dark_field),
        ),
        shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp),

        modifier = modifier.fillMaxWidth()
    )
}

@Preview(showBackground = true)
@Composable
fun TipCalculatorPreview() {
    AuthcomposeTheme {
        TipCalculatorComposable()
    }
}