package com.example.vocabdaily.presentation.add_edit_words.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.vocabdaily.domain.model.Word
import com.example.vocabdaily.presentation.add_edit_words.AddEditWordEvent
import com.example.vocabdaily.presentation.add_edit_words.AddEditWordViewModel
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditWordScreen(
    navController: NavController,
    wordColor: Int,
    addEditWordViewModel: AddEditWordViewModel = hiltViewModel()
){
    var showPalette by rememberSaveable { mutableStateOf(false) }

    val wordState by addEditWordViewModel.wordText
    val descriptionState by addEditWordViewModel.descriptionText
    val selectedColor by addEditWordViewModel.wordColor

    LaunchedEffect(wordColor) {
        if (wordColor != 0) {
            addEditWordViewModel.onEvent(AddEditWordEvent.ChangeColor(wordColor))
        }
    }

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        addEditWordViewModel.eventFlow.collectLatest { event ->
            when (event) {
                is AddEditWordViewModel.UiEvent.ShowSnackbar -> snackbarHostState.showSnackbar(event.message)
                AddEditWordViewModel.UiEvent.SaveWord -> navController.popBackStack()
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add / Edit Word", fontWeight = FontWeight.SemiBold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { addEditWordViewModel.onEvent(AddEditWordEvent.SaveWord) },
                icon = { Icon(Icons.Filled.Check, contentDescription = "Save") },
                text = { Text("Save") }
            )
        }
    ) { innerPadding ->
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            val isWide = maxWidth >= 600.dp

            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .animateContentSize(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                if (isWide) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            WordInputFields(
                                wordText = wordState.text,
                                wordHint = wordState.hint,
                                wordHintVisible = wordState.isHintVisible,
                                descriptionText = descriptionState.text,
                                descriptionHint = descriptionState.hint,
                                descriptionHintVisible = descriptionState.isHintVisible,
                                onWordChange = { addEditWordViewModel.onEvent(AddEditWordEvent.EnteredWord(it)) },
                                onWordFocusChange = { fs -> addEditWordViewModel.onEvent(AddEditWordEvent.ChangeWordFocus(fs)) },
                                onDescriptionChange = { addEditWordViewModel.onEvent(AddEditWordEvent.EnteredDescription(it)) },
                                onDescriptionFocusChange = { fs -> addEditWordViewModel.onEvent(AddEditWordEvent.ChangeDescriptionFocus(fs)) },
                                selectedColor = selectedColor
                            )
                        }

                        Column(modifier = Modifier.weight(0.6f)) {
                            ColorPaletteHeader(
                                showPalette = showPalette,
                                onToggle = { showPalette = !showPalette },
                                selectedColor = selectedColor
                            )
                            AnimatedVisibility(visible = showPalette) {
                                ColorPalette(
                                    colors = Word.wordColors,
                                    selectedColor = selectedColor,
                                    onColorSelected = { color -> addEditWordViewModel.onEvent(AddEditWordEvent.ChangeColor(color)) }
                                )
                            }
                        }
                    }
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        WordInputFields(
                            wordText = wordState.text,
                            wordHint = wordState.hint,
                            wordHintVisible = wordState.isHintVisible,
                            descriptionText = descriptionState.text,
                            descriptionHint = descriptionState.hint,
                            descriptionHintVisible = descriptionState.isHintVisible,
                            onWordChange = { addEditWordViewModel.onEvent(AddEditWordEvent.EnteredWord(it)) },
                            onWordFocusChange = { fs -> addEditWordViewModel.onEvent(AddEditWordEvent.ChangeWordFocus(fs)) },
                            onDescriptionChange = { addEditWordViewModel.onEvent(AddEditWordEvent.EnteredDescription(it)) },
                            onDescriptionFocusChange = { fs -> addEditWordViewModel.onEvent(AddEditWordEvent.ChangeDescriptionFocus(fs)) },
                            selectedColor = selectedColor
                        )

                        ColorPaletteHeader(
                            showPalette = showPalette,
                            onToggle = { showPalette = !showPalette },
                            selectedColor = selectedColor
                        )

                        AnimatedVisibility(visible = showPalette) {
                            ColorPalette(
                                colors = Word.wordColors,
                                selectedColor = selectedColor,
                                onColorSelected = { color -> addEditWordViewModel.onEvent(AddEditWordEvent.ChangeColor(color)) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun WordInputFields(
    wordText: String,
    wordHint: String,
    wordHintVisible: Boolean,
    descriptionText: String,
    descriptionHint: String,
    descriptionHintVisible: Boolean,
    onWordChange: (String) -> Unit,
    onWordFocusChange: (androidx.compose.ui.focus.FocusState) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onDescriptionFocusChange: (androidx.compose.ui.focus.FocusState) -> Unit,
    selectedColor: Int
) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        // Word field using our DesignedTextField for consistent styling.
        DesignedTextField(
            value = wordText,
            onValueChange = onWordChange,
            hint = wordHint,
            isHintVisible = wordHintVisible,
            singleLine = true,
            backgroundColor = Color(selectedColor),
            onFocusChange = onWordFocusChange
        )

        // Description field: same component with multi-line behavior.
        DesignedTextField(
            value = descriptionText,
            onValueChange = onDescriptionChange,
            hint = descriptionHint,
            isHintVisible = descriptionHintVisible,
            singleLine = false,
            onFocusChange = onDescriptionFocusChange
        )
    }
}

@Composable
private fun ColorPaletteHeader(
    showPalette: Boolean,
    onToggle: () -> Unit,
    selectedColor: Int
) {
    val rotation by animateFloatAsState(if (showPalette) 180f else 0f, label = "paletteRotation")
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .clickable { onToggle() }
            .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text("Pick Color", style = MaterialTheme.typography.bodyMedium)
        Row(verticalAlignment = Alignment.CenterVertically) {
            androidx.compose.foundation.layout.Box(
                modifier = Modifier
                    .size(18.dp)
                    .clip(CircleShape)
                    .background(Color(selectedColor))
                    .border(1.dp, MaterialTheme.colorScheme.outline, CircleShape)
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(if (showPalette) "▼" else "►", modifier = Modifier.padding(start = 4.dp))
        }
    }
}

@Composable
private fun ColorPalette(
    colors: List<Color>,
    selectedColor: Int,
    onColorSelected: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        colors.forEach { c ->
            val isSelected = c.value.toInt() == selectedColor
            androidx.compose.foundation.layout.Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(c)
                    .border(
                        width = if (isSelected) 3.dp else 1.dp,
                        color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
                        shape = CircleShape
                    )
                    .clickable { onColorSelected(c.value.toInt()) },
                contentAlignment = Alignment.Center
            ) {}
        }
    }
}