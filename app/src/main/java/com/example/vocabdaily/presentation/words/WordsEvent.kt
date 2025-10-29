package com.example.vocabdaily.presentation.words

import com.example.vocabdaily.domain.model.Word
import com.example.vocabdaily.domain.utils.WordOrder

sealed class WordsEvent {
    data class Order(val wordOrder: WordOrder): WordsEvent()
    data class DeleteWord(val word: Word): WordsEvent()
    object RestoreWord: WordsEvent()
    object ToggleOrderSection: WordsEvent()
    //object AddWord: WordsEvent()
    //object EditWord: WordsEvent()
    //object DeleteAllWords: WordsEvent()
}

