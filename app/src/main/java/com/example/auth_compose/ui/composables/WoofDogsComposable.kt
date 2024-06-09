package com.example.auth_compose.ui.composables

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.auth_compose.R
import com.example.auth_compose.ui.theme.woof.WoofTheme

@Composable
fun WoofDogsComposable(modifier: Modifier = Modifier) {
    Scaffold {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(32.dp + it.calculateTopPadding())
                .verticalScroll(rememberScrollState())
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_woof_logo),
                    contentDescription = null,
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = stringResource(id = R.string.woof_title))
            }
            Spacer(modifier = Modifier.height(16.dp))
            Column {
                val dogs = Dog.entries
                for (dog in dogs) {
                    if (dog != dogs.first()) {
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                    Card(
                        shape = MaterialTheme.shapes.medium,
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Image(
                                painter = painterResource(id = dog.photo),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(64.dp)
                                    .padding(12.dp)
                                    .clip(CircleShape)
                            )
                            Column(
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier
                                    .padding(vertical = 8.dp)
                            ) {
                                Text(text = stringResource(id = dog.dogName))
                                Text(
                                    text = "${dog.age} years old",
                                    fontSize = 14.sp,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun WoofDogsPreview() {
    WoofTheme(darkTheme = false) {
        WoofDogsComposable()
    }
}

enum class Dog(
    @DrawableRes val photo: Int,
    @StringRes val dogName: Int,
    val age: Int,
) {
    KODA(
        photo = R.drawable.koda,
        dogName = R.string.dog_name_1,
        age = 2,
    ),
    LOLA(
        photo = R.drawable.lola,
        dogName = R.string.dog_name_2,
        age = 16,
    ),
    FRANKIE(
        photo = R.drawable.frankie,
        dogName = R.string.dog_name_3,
        age = 2,
    ),
    NOX(
        photo = R.drawable.nox,
        dogName = R.string.dog_name_4,
        age = 8,
    ),
    FAYE(
        photo = R.drawable.faye,
        dogName = R.string.dog_name_5,
        age = 8,
    ),
    BELLA(
        photo = R.drawable.bella,
        dogName = R.string.dog_name_6,
        age = 14,
    ),
    MOANA(
        photo = R.drawable.moana,
        dogName = R.string.dog_name_7,
        age = 2,
    ),
    TZEITEL(
        photo = R.drawable.tzeitel,
        dogName = R.string.dog_name_8,
        age = 7,
    ),
    LEROY(
        photo = R.drawable.leroy,
        dogName = R.string.dog_name_9,
        age = 4,
    );
}