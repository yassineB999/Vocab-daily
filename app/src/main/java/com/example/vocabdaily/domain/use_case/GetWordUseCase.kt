package com.example.vocabdaily.domain.use_case

import com.example.vocabdaily.domain.model.Word
import com.example.vocabdaily.domain.repository.WordRepository

class GetWordUseCase(private val repository: WordRepository) {
    suspend operator fun invoke(id: Int): Word? {
        return repository.getWordById(id)
    }
}