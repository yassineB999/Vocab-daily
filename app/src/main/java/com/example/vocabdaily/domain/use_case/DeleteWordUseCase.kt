package com.example.vocabdaily.domain.use_case

import com.example.vocabdaily.domain.model.Word
import com.example.vocabdaily.domain.repository.WordRepository

class DeleteWordUseCase(private val repository: WordRepository) {

    suspend operator fun invoke(word: Word) {
        repository.deleteWord(word)
    }

}