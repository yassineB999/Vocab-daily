package com.example.vocabdaily.presentation.add_edit_words

data class WordsTextFieldState(
    val text: String = "",
    val hint: String = "",
    val isHintVisible: Boolean = true
)