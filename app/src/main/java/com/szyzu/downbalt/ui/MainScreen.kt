package com.szyzu.downbalt.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.datastore.dataStore
import androidx.lifecycle.viewmodel.compose.viewModel
import com.szyzu.downbalt.data.DataStoreManager

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    dataStore: DataStoreManager,
    viewModel: MainViewModel = viewModel(
        factory = MainViewModel.provideFactory(dataStore)
    )
) {
    val uiState by viewModel.uiState.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    var linkText by remember { mutableStateOf("") }

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        TextButton(
            onClick = { showDialog = true },
            modifier = Modifier.align(Alignment.Center)
        ) {
            Box(
                modifier = Modifier
                    .border(2.dp, Color.Black, RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {
                Text(
                    text = "Change Cobalt link",
                    color = Color.Black
                )
            }
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 18.dp),

            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            CobaltLinkInfo(
                currentLink = uiState.cobaltLink
            )
        }

        if(showDialog){
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = {
                    Text(text = "Change Link")
                },
                text = {
                    OutlinedTextField(
                        value = linkText,
                        onValueChange = { linkText = it },
                        label = { Text("Enter new link") },
                        singleLine = true
                    )
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            showDialog = false
                            viewModel.updateLink(linkText)
                        }
                    ) {
                        Text("Save")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = { showDialog = false }
                    ) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}

@Composable
fun CobaltLinkInfo(
    currentLink: String
){
    var link = currentLink
    if(currentLink.isEmpty())
        link = "None"

    Text(
        text = "Current Cobalt api link:",
    )

    Text(
        text = link,
    )
}