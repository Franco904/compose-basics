package com.example.auth_compose.ui.composables

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.auth_compose.R
import com.example.auth_compose.ui.theme.woof.WoofTheme

@Composable
fun WoofDogsComposable(modifier: Modifier = Modifier) {
    Scaffold(
        topBar = { WoofTopAppBar() }
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(
                    start = 24.dp,
                    top = 24.dp + it.calculateTopPadding(),
                    end = 24.dp,
                    bottom = 24.dp,
                )
                .verticalScroll(rememberScrollState())
        ) {
            Column {
                val dogs = Dog.entries

                for (dog in dogs) {
                    if (dog != dogs.first()) {
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                    DogCard(dog)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WoofTopAppBar(
    modifier: Modifier = Modifier,
) {
    CenterAlignedTopAppBar(
        title = {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_woof_logo),
                    contentDescription = null,
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(id = R.string.woof_title),
                    style = MaterialTheme.typography.displayLarge,
                )
            }
        },
        modifier = modifier,
    )
}

@Composable
fun DogCard(
    dog: Dog,
    modifier: Modifier = Modifier,
) {
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
            .clickable { isExpanded = !isExpanded }
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioNoBouncy,
                    stiffness = Spring.StiffnessMedium,
                )
            )
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
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
                        .padding(vertical = 12.dp)
                        .weight(1f)
                ) {
                    Text(
                        text = stringResource(id = dog.dogName),
                        style = MaterialTheme.typography.displayMedium,
                    )
                    Text(
                        text = stringResource(id = R.string.years_old, dog.age),
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
                ExpandDogCardButton(
                    isExpanded = isExpanded,
                    onExpand = { isExpanded = !isExpanded }
                )
            }
            AnimatedVisibility(visible = isExpanded) {
                DogHobbie(hobbieId = dog.hobbie)
            }
        }
    }
}

@Composable
fun ExpandDogCardButton(
    isExpanded: Boolean,
    onExpand: () -> Unit,
    modifier: Modifier = Modifier,
) {
    IconButton(
        onClick = onExpand,
        modifier = modifier
            .padding(end = 8.dp)
    ) {
        Icon(
            imageVector = if (isExpanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
            contentDescription = stringResource(id = R.string.expand_button_content_description),
        )
    }
}

@Composable
fun DogHobbie(@StringRes hobbieId: Int) {
    Column(
        modifier = Modifier
            .padding(
                start = 16.dp,
                top = 8.dp,
                bottom = 16.dp
            )
    ) {
        Text(
            text = stringResource(id = R.string.hobbie),
            style = MaterialTheme.typography.labelSmall,
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = stringResource(id = hobbieId),
            style = MaterialTheme.typography.bodyLarge,
        )
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
    @StringRes val hobbie: Int,
) {
    KODA(
        photo = R.drawable.koda,
        dogName = R.string.dog_name_1,
        age = 2,
        hobbie = R.string.dog_description_1,
    ),
    LOLA(
        photo = R.drawable.lola,
        dogName = R.string.dog_name_2,
        age = 16,
        hobbie = R.string.dog_description_2,
    ),
    FRANKIE(
        photo = R.drawable.frankie,
        dogName = R.string.dog_name_3,
        age = 2,
        hobbie = R.string.dog_description_3,
    ),
    NOX(
        photo = R.drawable.nox,
        dogName = R.string.dog_name_4,
        age = 8,
        hobbie = R.string.dog_description_4,
    ),
    FAYE(
        photo = R.drawable.faye,
        dogName = R.string.dog_name_5,
        age = 8,
        hobbie = R.string.dog_description_5,
    ),
    BELLA(
        photo = R.drawable.bella,
        dogName = R.string.dog_name_6,
        age = 14,
        hobbie = R.string.dog_description_6,
    ),
    MOANA(
        photo = R.drawable.moana,
        dogName = R.string.dog_name_7,
        age = 2,
        hobbie = R.string.dog_description_7,
    ),
    TZEITEL(
        photo = R.drawable.tzeitel,
        dogName = R.string.dog_name_8,
        age = 7,
        hobbie = R.string.dog_description_8,
    ),
    LEROY(
        photo = R.drawable.leroy,
        dogName = R.string.dog_name_9,
        age = 4,
        hobbie = R.string.dog_description_9,
    );
}