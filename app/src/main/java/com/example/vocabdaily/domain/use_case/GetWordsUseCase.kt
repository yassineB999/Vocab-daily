package com.example.vocabdaily.domain.use_case

import com.example.vocabdaily.domain.model.Word
import com.example.vocabdaily.domain.repository.WordRepository
import com.example.vocabdaily.domain.utils.OrderType
import com.example.vocabdaily.domain.utils.WordOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetWordsUseCase(private  val repository: WordRepository) {

    // La fonction invoke permet d'appeler l'objet comme une fonction : GetWordsUseCase()
    operator fun invoke(wordOrder: WordOrder = WordOrder.Date(OrderType.Descending)
    ): Flow<List<Word>> {
        return repository.getWords().map { words ->
            when (wordOrder.orderType) {
                is OrderType.Ascending -> {
                    when (wordOrder) {
                        is WordOrder.Word -> words.sortedBy { it.word.lowercase() }
                        is WordOrder.Date -> words.sortedBy { it.timestamp }
                        is WordOrder.Color -> words.sortedBy { it.color }
                        else -> words
                    }
                }
                is OrderType.Descending -> {
                    when (wordOrder) {
                        is WordOrder.Word -> words.sortedByDescending { it.word.lowercase() }
                        is WordOrder.Date -> words.sortedByDescending { it.timestamp }
                        is WordOrder.Color -> words.sortedByDescending { it.color }
                        else -> words
                    }
                }
            }
        }
    }
}