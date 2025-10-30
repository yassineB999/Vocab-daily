package com.example.vocabdaily.presentation.words.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.vocabdaily.presentation.words.WordViewModel
import com.example.vocabdaily.presentation.words.WordsEvent
import kotlinx.coroutines.launch

@Composable
fun WordsScreen(
    navController: NavController,
    viewModel: WordViewModel = hiltViewModel()
) {
    // Observe state from the ViewModel
    val state by viewModel.state
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    // Navigate to your add/edit screen route. Replace with your route constant.
                    navController.navigate("add_edit_word")
                },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Text(text = "+", color = MaterialTheme.colorScheme.onPrimary)
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            // Order controls
            WordSection(
                wordOrder = state.wordOrder,
                onOrderChange = { order -> viewModel.onEvent(WordsEvent.Order(order)) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // List of words
            LazyColumn {
                items(state.words) { word ->
                    WordItem(
                        word = word,
                        modifier = Modifier.clickable { },
                        onDeleteClick = {
                            viewModel.onEvent(WordsEvent.DeleteWord(word))
                            scope.launch {
                                val result = snackbarHostState.showSnackbar(
                                    message = "Word deleted",
                                    actionLabel = "Undo"
                                )
                                if (result == SnackbarResult.ActionPerformed) {
                                    viewModel.onEvent(WordsEvent.RestoreWord)
                                }
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}
