package com.example.auth_compose.ui.composables

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.auth_compose.R
import com.example.auth_compose.ui.theme.AuthcomposeTheme
import com.example.auth_compose.ui.util.style
import com.example.auth_compose.ui.util.textSpan
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.parcelize.Parcelize

@Composable
fun ArtworkShowcaseComposable() {
    val scrollState = rememberScrollState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        val artworks = getArtworks()
        var currentArtwork by rememberSaveable {
            mutableStateOf(artworks.first())
        }

        ArtworkPictureRow(picture = currentArtwork.picture)
        Spacer(modifier = Modifier.height(8.dp))
        AboutArtworkRow(
            name = currentArtwork.name,
            author = currentArtwork.author,
            publishYear = currentArtwork.publishYear,
        )
        Spacer(modifier = Modifier.height(36.dp))
        TogglePictureRow(
            artworks = artworks,
            currentArtwork = currentArtwork,
            onPreviousPicturePressed = { previousArtworkIndex ->
                currentArtwork = artworks[previousArtworkIndex]
            },
            onNextPicturePressed = { nextArtworkIndex ->
                currentArtwork = artworks[nextArtworkIndex]
            }
        )
    }
}

@Composable
fun ArtworkPictureRow(@DrawableRes picture: Int) {
    Surface(
        modifier = Modifier
            .padding(32.dp)
            .aspectRatio(3 / 4f)
            .shadow(elevation = 6.dp)
    ) {
        Image(
            painter = painterResource(id = picture),
            contentDescription = null,
            modifier = Modifier.padding(32.dp)
        )
    }
}

@Composable
fun AboutArtworkRow(
    name: String,
    author: String,
    publishYear: Int,
) {
    Surface(
        shape = RoundedCornerShape(6.dp),
        modifier = Modifier.padding(start = 36.dp, end = 36.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .background(colorResource(id = R.color.grey_light_surface))
                .padding(
                    start = 16.dp,
                    top = 8.dp,
                    end = 16.dp,
                    bottom = 8.dp,
                )
                .aspectRatio(3 / 0.65f)
        ) {
            Text(
                text = name,
                fontSize = 22.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = buildAnnotatedString {
                    textSpan(
                        author.style(SpanStyle(fontWeight = FontWeight.Bold))
                    )
                    textSpan(" ($publishYear)")
                },
                fontSize = 14.sp,
                modifier = Modifier.align(Alignment.Start)
            )
        }
    }
}

@Composable
fun TogglePictureRow(
    artworks: ImmutableList<Artwork>,
    currentArtwork: Artwork,
    onPreviousPicturePressed: (Int) -> Unit,
    onNextPicturePressed: (Int) -> Unit,
) {
    val isNotFirstPicture = artworks.first() != currentArtwork
    val isNotLastPicture = artworks.last() != currentArtwork

    Row {
        Button(
            onClick = {
                val previousArtworkIndex = artworks.indexOf(currentArtwork) - 1
                onPreviousPicturePressed(previousArtworkIndex)
            },
            enabled = isNotFirstPicture,
        ) {
            Text(text = stringResource(R.string.previous_button_text))
        }
        Spacer(modifier = Modifier.width(16.dp))
        Button(
            onClick = {
                val nextArtworkIndex = artworks.indexOf(currentArtwork) + 1
                onNextPicturePressed(nextArtworkIndex)
            },
            enabled = isNotLastPicture,
        ) {
            Text(text = stringResource(R.string.next_button_text))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ArtworkShowcasePreview() {
    AuthcomposeTheme {
        ArtworkShowcaseComposable()
    }
}

// Source: https://www.nationalgallery.org.uk/paintings/learn-about-art/guide-to-impressionism
fun getArtworks() = persistentListOf(
    Artwork(
        picture = R.drawable.renoir_at_the_theatre,
        name = "At the Theatre (La Première Sortie)",
        author = "Pierre-Auguste Renoir",
        publishYear = 1876,
    ),
    Artwork(
        picture = R.drawable.monet_the_beach_at_touville,
        name = "The Beach at Trouville",
        author = "Claude Monet",
        publishYear = 1870,
    ),
    Artwork(
        picture = R.drawable.monet_water_lily_pond,
        name = "The Water-Lilly Pond",
        author = "Claude Monet",
        publishYear = 1899,
    ),
    Artwork(
        picture = R.drawable.renoir_the_skiff_la_yole,
        name = "The Skiff (La Yole)",
        author = "Pierre-Auguste Renoir",
        publishYear = 1875,
    ),
    Artwork(
        picture = R.drawable.monet_lavacourt_under_snow,
        name = "'Lavacourt Under Snow",
        author = "Claude Monet",
        publishYear = 1878,
    ),
)

@Parcelize
data class Artwork(
    @DrawableRes
    val picture: Int,
    val name: String,
    val author: String,
    val publishYear: Int,
) : Parcelable
