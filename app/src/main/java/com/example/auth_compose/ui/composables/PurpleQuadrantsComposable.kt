package com.example.auth_compose.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.auth_compose.R
import com.example.auth_compose.ui.composables.theme.AuthcomposeTheme

@Composable
fun PurpleQuadrantsComposable() {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.weight(1F)
        ) {
            FirstPurpleQuadrant(Modifier.weight(1F))
            SecondPurpleQuadrant(Modifier.weight(1F))
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.weight(1F)
        ) {
            ThirdPurpleQuadrant(Modifier.weight(1F))
            FourthPurpleQuadrant(Modifier.weight(1F))
        }
    }
}

@Composable
fun FirstPurpleQuadrant(modifier: Modifier = Modifier) {
    Surface(
        color = colorResource(id = R.color.purple_1st_quadrant),
        modifier = modifier.fillMaxHeight()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = stringResource(R.string.first_quadrant_title),
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.first_quadrant_message),
                textAlign = TextAlign.Justify,
            )
        }
    }
}

@Composable
fun SecondPurpleQuadrant(modifier: Modifier = Modifier) {
    Surface(
        color = colorResource(id = R.color.purple_2nd_quadrant),
        modifier = modifier.fillMaxHeight()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = stringResource(R.string.second_quadrant_title),
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.second_quadrant_message),
                textAlign = TextAlign.Justify,
            )
        }
    }
}

@Composable
fun ThirdPurpleQuadrant(modifier: Modifier = Modifier) {
    Surface(
        color = colorResource(id = R.color.purple_3rd_quadrant),
        modifier = modifier.fillMaxHeight()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = stringResource(R.string.third_quadrant_title),
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.third_quadrant_message),
                textAlign = TextAlign.Justify,
            )
        }
    }
}

@Composable
fun FourthPurpleQuadrant(modifier: Modifier = Modifier) {
    Surface(
        color = colorResource(id = R.color.purple_4th_quadrant),
        modifier = modifier.fillMaxHeight()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = stringResource(R.string.fourth_quadrant_title),
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.fourth_quadrant_message),
                textAlign = TextAlign.Justify,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PurpleQuadrantsPreview() {
    AuthcomposeTheme {
        PurpleQuadrantsComposable()
    }
}
