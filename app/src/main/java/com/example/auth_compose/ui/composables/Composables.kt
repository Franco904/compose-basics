package com.example.auth_compose.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.auth_compose.R

@Composable
fun BoxTextsLayout(name: String) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(400.dp)
            .fillMaxSize()
    ) {
        Column {
            Text(
                text = "Hello $name!",
                color = Color.Black,
                fontSize = 32.sp,
                modifier = Modifier
                    .background(Color.LightGray)
                    .padding(8.dp)
            )
            Text(
                text = "under :))",
                color = Color.Black,
                fontSize = 32.sp,
            )
        }
        Row {
            Text(text = "column 1")
            Text(text = "column 2")
        }
    }
}

@Composable
fun ImageContentLayout() {
    Column {
        for (i in 1..100) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = null,
                modifier = Modifier.background(Color.Black)
            )
        }
    }
}

@Composable
fun ListItemsLayout() {
    Column {
        LazyRow {
            items(10) { i ->
                Icon(
                    imageVector = Icons.Default.List,
                    contentDescription = null,
                    modifier = Modifier
                        .size(100.dp)
                        .padding(bottom = 8.dp)
                )
            }
        }
        Divider()
        LazyColumn {
            items(10) { i ->
                Icon(
                    imageVector = Icons.Default.List,
                    contentDescription = null,
                    modifier = Modifier.size(100.dp)
                )
            }
        }
    }
}