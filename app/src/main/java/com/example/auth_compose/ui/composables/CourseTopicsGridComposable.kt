package com.example.auth_compose.ui.composables

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.auth_compose.R
import com.example.auth_compose.ui.theme.AuthcomposeTheme
import kotlinx.parcelize.Parcelize

@Composable
fun CourseTopicsGridComposable() {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(8.dp)
    ) {
        val courseTopics = getCourseTopics()

        items(courseTopics, key = { it.id }) { courseTopic ->
            CourseTopicCard(courseTopic)
        }
    }
}

@Composable
fun CourseTopicCard(courseTopic: CourseTopic) {
    Card(
        modifier = Modifier
            .fillMaxHeight()
            .aspectRatio(4 / 2f)
    ) {
        Row {
            Image(
                painter = painterResource(id = courseTopic.image),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .weight(3f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(4f)
                    .padding(end = 16.dp)
            ) {
                Text(
                    text = stringResource(id = courseTopic.name),
                    fontSize = 14.sp,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_grain),
                        contentDescription = null,
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${courseTopic.coursesAmount}",
                        fontSize = 14.sp,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.labelMedium,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CourseTopicsGridPreview() {
    AuthcomposeTheme {
        CourseTopicsGridComposable()
    }
}

fun getCourseTopics() = listOf(
    CourseTopic(1L, R.drawable.architecture, R.string.architecture, 58),
    CourseTopic(2L, R.drawable.crafts, R.string.crafts, 121),
    CourseTopic(3L, R.drawable.business, R.string.business, 78),
    CourseTopic(4L, R.drawable.culinary, R.string.culinary, 118),
    CourseTopic(5L, R.drawable.design, R.string.design, 423),
    CourseTopic(6L, R.drawable.fashion, R.string.fashion, 92),
    CourseTopic(7L, R.drawable.film, R.string.film, 165),
    CourseTopic(8L, R.drawable.gaming, R.string.gaming, 164),
    CourseTopic(9L, R.drawable.drawing, R.string.drawing, 326),
    CourseTopic(10L, R.drawable.lifestyle, R.string.lifestyle, 305),
    CourseTopic(11L, R.drawable.music, R.string.music, 212),
    CourseTopic(12L, R.drawable.painting, R.string.painting, 172),
    CourseTopic(13L, R.drawable.photography, R.string.photography, 321),
    CourseTopic(14L, R.drawable.tech, R.string.tech, 118)
)

@Parcelize
data class CourseTopic(
    val id: Long,
    @DrawableRes
    val image: Int,
    @StringRes
    val name: Int,
    val coursesAmount: Int,
) : Parcelable
