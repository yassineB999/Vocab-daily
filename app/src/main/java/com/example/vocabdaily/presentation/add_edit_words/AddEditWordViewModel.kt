package com.example.vocabdaily.presentation.add_edit_words

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vocabdaily.domain.model.Word
import com.example.vocabdaily.domain.use_case.WordUseCases
import com.example.vocabdaily.exeprions.InvalidWordException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AddEditWordViewModel @Inject constructor(
    private val wordUseCases: WordUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _wordText = mutableStateOf(WordsTextFieldState(
        hint = "Enter word..."
    ))
    val wordText: State<WordsTextFieldState> = _wordText

    private val _descriptionText = mutableStateOf(WordsTextFieldState(
        hint = "Enter meaning/description..."
    ))
    val descriptionText: State<WordsTextFieldState> = _descriptionText

    private val _wordColor = mutableStateOf(Word.wordColors.random().toArgb())
    val wordColor: State<Int> = _wordColor

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentWordId: Int? = null

    init{
        savedStateHandle.get<Int>("wordId")?.let { wordId ->
            if(wordId != -1){
                viewModelScope.launch {
                    wordUseCases.getWord(wordId)?.also { word ->
                        currentWordId = word.id
                        _wordText.value = wordText.value.copy(
                            text = word.word,
                            isHintVisible = false
                        )
                        _descriptionText.value = descriptionText.value.copy(
                            text = word.description,
                            isHintVisible = false
                        )
                        _wordColor.value = word.color
                    }
                }
            }
        }
    }

    fun onEvent(event: AddEditWordEvent) {
        when (event) {
            is AddEditWordEvent.EnteredWord -> {
                _wordText.value = wordText.value.copy(
                    text = event.value
                )
            }
            is AddEditWordEvent.ChangeWordFocus -> {
                _wordText.value = wordText.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            wordText.value.text.isBlank()
                )
            }
            is AddEditWordEvent.EnteredDescription -> {
                _descriptionText.value = descriptionText.value.copy(
                    text = event.value
                )
            }
            is AddEditWordEvent.ChangeDescriptionFocus -> {
                _descriptionText.value = descriptionText.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            descriptionText.value.text.isBlank()
                )
            }
            is AddEditWordEvent.ChangeColor -> {
                _wordColor.value = event.color
            }
            is AddEditWordEvent.SaveWord -> {
                // Use viewModelScope to launch a coroutine
                viewModelScope.launch {
                    try {
                        // Create a new word and call the use case
                        wordUseCases.addWord(
                            Word(
                                id = currentWordId,
                                word = wordText.value.text,
                                description = descriptionText.value.text,
                                timestamp = System.currentTimeMillis(),
                                color = wordColor.value
                            )
                        )
                        // Emit an event to navigate back or confirm save
                        _eventFlow.emit(UiEvent.SaveWord)
                    } catch (e: InvalidWordException) {
                        // If validation fails, show a snackbar
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = e.message ?: "Couldn't save word. Please fill out all fields."
                            )
                        )
                    }
                }
            }
        }
    }

    // This sealed class defines one-time events for the UI.
    // It should be defined within the ViewModel class, not inside a function.
    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
        object SaveWord : UiEvent()
    }
}