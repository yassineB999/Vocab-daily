package com.example.vocabdaily.presentation.words

import com.example.vocabdaily.domain.model.Word
import com.example.vocabdaily.domain.utils.OrderType
import com.example.vocabdaily.domain.utils.WordOrder

data class WordsState(
    val words: List<Word> = emptyList(),
    val wordOrder: WordOrder = WordOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false
)
