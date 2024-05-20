package com.example.auth_compose.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.auth_compose.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GuestListContent() {
    val focusManager = LocalFocusManager.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Guest list", overflow = TextOverflow.Ellipsis)
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                ),
                scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primary)
                    .shadow(elevation = 8.dp)
            )
        },
        modifier = Modifier.pointerInput(Unit) {
            detectTapGestures(onTap = { focusManager.clearFocus() })
        },
    ) { padding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = 24.dp,
                    top = padding.calculateTopPadding() + 16.dp,
                    end = 24.dp,
                    bottom = 24.dp,
                )
        ) {
            GuestList(focusManager)
        }
    }
}

@Composable
fun GuestList(focusManager: FocusManager) {
    var name by remember { mutableStateOf("") }
    val names = remember { mutableStateOf(listOf<String>()) }

    Row(
        verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 24.dp)
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = { newValue -> name = newValue },
            label = { Text("Name") },
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Button(
            onClick = {
                if (name.isNotBlank()) {
                    names.value += name
                    name = ""
                    focusManager.clearFocus()
                }
            },
        ) {
            Text("Add")
        }
    }
    Spacer(modifier = Modifier.height(24.dp))
    LazyColumn {
        items(names.value) { name ->
            GuestItem(focusManager, name, names)
        }
    }
}

@Composable
fun GuestItem(
    focusManager: FocusManager,
    name: String,
    names: MutableState<List<String>>,
) {
    if (name == names.value.first()) Divider()
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(R.drawable.baseline_person_outline_24),
            contentDescription = null,
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            name,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .padding(16.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = {
                names.value = names.value.filter { it != name }
                focusManager.clearFocus()
            },
        ) {
            Text("Remove")
        }
    }
    Divider()
}