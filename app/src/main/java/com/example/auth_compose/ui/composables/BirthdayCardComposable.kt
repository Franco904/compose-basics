package com.example.auth_compose.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.auth_compose.R
import com.example.auth_compose.ui.composables.theme.AuthcomposeTheme
import com.example.auth_compose.ui.composables.theme.Purple80

@Composable
fun BirthdayCardComposable(
    message: String,
    from: String,
    modifier: Modifier = Modifier,
) {
    Surface(
        color = Purple80,
        modifier = modifier.fillMaxSize()
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(R.drawable.birthday_card_background),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .alpha(0.5F)
                    .fillMaxSize()
            )
            GreetingText(
                message = message,
                from = from,
            )
        }
    }
}

@Composable
fun GreetingText(
    message: String,
    from: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(8.dp)
    ) {
        Text(
            message,
            fontSize = 100.sp,
            lineHeight = 116.sp,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            from,
            fontSize = 36.sp,
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.CenterHorizontally)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BirthdayCardPreview() {
    AuthcomposeTheme {
        BirthdayCardComposable(
            message = stringResource(R.string.happy_birthday_card_title),
            from = stringResource(R.string.happy_birthday_card_from_text),
        )
    }
}