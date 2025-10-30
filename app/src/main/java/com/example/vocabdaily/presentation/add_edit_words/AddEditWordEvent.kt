package com.example.vocabdaily.presentation.add_edit_words

import androidx.compose.ui.focus.FocusState

sealed class AddEditWordEvent {
    data class EnteredWord(val value: String) : AddEditWordEvent()
    data class ChangeWordFocus(val focusState: FocusState) : AddEditWordEvent()
    data class EnteredDescription(val value: String) : AddEditWordEvent()
    data class ChangeDescriptionFocus(val focusState: FocusState): AddEditWordEvent()
    data class ChangeColor(val color: Int): AddEditWordEvent()
    object SaveWord : AddEditWordEvent()
}