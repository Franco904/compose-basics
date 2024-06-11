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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.auth_compose.R
import com.example.auth_compose.ui.theme.AuthcomposeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GuestListComposable() {
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
    var guestToAddName by remember { mutableStateOf("") }
    var guests by remember { mutableStateOf(listOf<Guest>()) }

    Row(
        verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 24.dp)
    ) {
        OutlinedTextField(
            value = guestToAddName,
            onValueChange = { newValue -> guestToAddName = newValue },
            label = { Text("Name") },
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Button(
            onClick = {
                if (guestToAddName.isNotBlank()) {
                    val lastGuestId = guests.lastOrNull()?.id ?: 0

                    guests += Guest(id = lastGuestId + 1, name = guestToAddName)
                    guestToAddName = ""

                    focusManager.clearFocus()
                }
            },
        ) {
            Text("Add")
        }
    }
    Spacer(modifier = Modifier.height(24.dp))
    LazyColumn {
        items(guests, key = { guest -> guest.id }) { guestItem ->
            GuestItem(
                focusManager,
                guestName = guestItem.name,
                isFirstGuest = guestItem.name == guests.firstOrNull()?.name,
                onRemoveGuest = {
                    guests = guests.filter { it.name != guestItem.name }
                },
            )
        }
    }
}

@Composable
fun GuestItem(
    focusManager: FocusManager,
    guestName: String,
    isFirstGuest: Boolean,
    onRemoveGuest: () -> Unit,
) {
    if (isFirstGuest) HorizontalDivider()
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(R.drawable.baseline_person_outline_24),
            contentDescription = null,
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            guestName,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .padding(16.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = {
                onRemoveGuest()
                focusManager.clearFocus()
            },
        ) {
            Text("Remove")
        }
    }
    HorizontalDivider()
}

data class Guest(
    val id: Long,
    val name: String,
)

@Preview(showBackground = true)
@Composable
fun GuestListPreview() {
    AuthcomposeTheme {
        GuestListComposable()
    }
}